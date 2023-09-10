package com.example.kud.ui.fragment.transaction

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kud.R
import com.example.kud.databinding.FragmentPaymentProofBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.TransactionViewModel
import com.example.kud.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class PaymentProofFragment :
    BaseFragment<FragmentPaymentProofBinding>(FragmentPaymentProofBinding::inflate) {

    private val viewModel: TransactionViewModel by viewModels()

    private val totalShoppingArgs by navArgs<PaymentProofFragmentArgs>()
    private var isImageLoaded = false
    private val resultContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.imgProofPhoto.setImageURI(result)
            binding.btnUpload.isEnabled = true
            isImageLoaded = true
            checkButtonState()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAmount()
        binding.imgProofPhoto.setOnClickListener {
            takePhoto()
        }

        binding.btnUpload.setOnClickListener {
            val transactionCode = totalShoppingArgs.transactionCode
            val transactionCodeBody =
                transactionCode!!.toRequestBody("text/plain".toMediaTypeOrNull())
            Log.d("transactionCodeBody", "$transactionCodeBody")
            val bitmap: Bitmap = (binding.imgProofPhoto.drawable as BitmapDrawable).bitmap
            val photo = uriToMultipartBody(bitmap)
            viewModel.requestImageUpload(transactionCodeBody, photo)
            viewModel.uploadImage.observe(viewLifecycleOwner) {
                dismissLoadingDialog()
                when (it) {
                    is NetworkResult.Success -> {
                        showSuccessDialog()
                        findNavController().navigate(R.id.action_paymentProofFragment_to_thanksFragment)
                    }

                    is NetworkResult.Loading -> {
                        showLoadingDialog()
                    }

                    is NetworkResult.Error -> {
                        showErrorDialog(it.message ?: "")
                    }
                }
            }

        }
        binding.btnUpload.isEnabled = false
        checkButtonState()

    }


    private fun takePhoto() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("Select Action")
        val pictureDialogItem = arrayOf(
            "Select photo from Gallery",
            "Capture photo from Camera"
        )
        pictureDialog.setItems(pictureDialogItem) { _, which ->
            when (which) {
                0 -> gallery()
                1 -> camera()
            }
        }

        pictureDialog.show()

    }

    private fun gallery() {
        resultContract.launch("image/*")
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        putPhoto.launch(intent)
    }

    private val putPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val bitmap = it?.data?.extras?.get("data") as Bitmap
                binding.imgProofPhoto.setImageBitmap(bitmap)
                binding.btnUpload.isEnabled = true
            } else if (it == null) {
                binding.imgProofPhoto.setImageResource(R.drawable.uploa_bukti)
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun uriToMultipartBody(bitmap: Bitmap): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val requestFile = byteArray.toRequestBody("image/png".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("bukti_transfer", "image.png", requestFile)
    }

    private fun checkButtonState() {
        binding.btnUpload.isEnabled = isImageLoaded
    }

    private fun setAmount() {
        val totalPurchase = totalShoppingArgs.totalShopping ?: ""
        val text =
            "Silahkan Transfer Sesuai Nominal $totalPurchase untuk Pembelian Obat ke Rekening yang tertera di atas"
        val spannableText = SpannableStringBuilder(text)

        val startIndex = text.indexOf(totalPurchase)
        val endIndex = startIndex + totalPurchase.length

        spannableText.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            endIndex,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )

        binding.tvTransfer.text = spannableText
    }

}