package com.example.kud.ui.fragment.transaction

import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.kud.databinding.FragmentPaymentProofBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.TransactionViewModel
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
            isImageLoaded = true
            checkButtonState()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAmount()
        binding.imgProofPhoto.setOnClickListener {
            resultContract.launch("image/*")
        }

        binding.btnUpload.setOnClickListener {
            if (isImageLoaded) {
                val transactionCode = totalShoppingArgs.transactionCode
                val bitmap: Bitmap = (binding.imgProofPhoto.drawable as BitmapDrawable).bitmap
                val photo = uriToMultipartBody(bitmap)
//                viewModel.requestImageUpload(transactionCode, photo)
            } else {

            }
        }
        binding.btnUpload.isEnabled = false
        checkButtonState()

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

    override fun onResume() {
        super.onResume()
        checkButtonState()
    }
}