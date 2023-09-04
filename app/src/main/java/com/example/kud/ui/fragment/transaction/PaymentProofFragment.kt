package com.example.kud.ui.fragment.transaction

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs
import com.example.kud.databinding.FragmentPaymentProofBinding
import com.example.kud.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentProofFragment :
    BaseFragment<FragmentPaymentProofBinding>(FragmentPaymentProofBinding::inflate) {

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

        binding.btnLogin.setOnClickListener {
            if (isImageLoaded) {
                Log.d("imageview", "gambar sudah berubah sip...: ")
                Toast.makeText(requireContext(), "gambar sudah berubah sip...", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("imageview", "gambar tidak berubah")
                Toast.makeText(requireContext(), "gambar tidak berubah", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.btnLogin.isEnabled = false
        checkButtonState()

    }

    private fun checkButtonState() {
        binding.btnLogin.isEnabled = isImageLoaded
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