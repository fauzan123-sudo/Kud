package com.example.kud.ui.fragment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.*
import com.example.kud.R
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.kud.databinding.FragmentScanBinding
import com.example.kud.ui.base.BaseFragment

class ScanFragment : BaseFragment<FragmentScanBinding>(FragmentScanBinding::inflate) {
    private lateinit var codeScanner: CodeScanner

    private val cameraPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permitted ->
            if (permitted) {
                Log.d("checkPermission", "permitted")
//                displayCameraFragment()
            } else {
                Log.d("checkPermission", " not permitted")
                alertDialog()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setUpPermissionCamera()
        }

//        displayCameraFragment()
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, binding.scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
            }
        }
        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun setUpPermissionCamera() {
        cameraPermissionResult.launch(Manifest.permission.CAMERA)
    }

    private fun alertDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Peringatan")
            .setMessage("Anda Harus memberika izin untuk scan")
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton("Ya") { _, _ ->
                cameraPermissionResult.launch(Manifest.permission.CAMERA)
            }
            .setNegativeButton("Kembali") { _, _ ->
                findNavController().navigate(R.id.action_scanFragment_to_addFragment)
            }
            .create()
            .show()
    }

    private fun displayCameraFragment() {
        codeScanner = CodeScanner(requireActivity(), binding.scannerView)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false
        }
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
            }
        }
        codeScanner.errorCallback = ErrorCallback {
            requireActivity().runOnUiThread {
                Log.e("error", "camera initialize error ${it.message}: ")
            }
        }
        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}