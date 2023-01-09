package com.example.kud.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kud.R
import com.example.kud.data.model.User
import com.example.kud.databinding.FragmentAddBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.AlbumViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment : BaseFragment<FragmentAddBinding>(FragmentAddBinding::inflate) {

    private val viewModel: AlbumViewModel by viewModels()
    private lateinit var permissionLancer: ActivityResultLauncher<Array<String>>

    private val listPermission = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            insertData()
        }

        binding.imgPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT < 23) {
                camera()
            }
            activity?.let {
                if (hasPermissions(activity as Context, listPermission)) {
                    camera()
                    Toast.makeText(requireContext(), " camera 2", Toast.LENGTH_SHORT).show()
                    Log.d("kamera 2", "onCreateView: ")
                } else {
                    permissionLancer.launch(
                        listPermission
                    )
                }
            }

        }

        permissionLancer =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                permissions.forEach {
                    if (!it.value) {
                        Toast.makeText(requireContext(), it.value.toString(), Toast.LENGTH_SHORT).show()
                    }
                    camera()
                    Toast.makeText(requireContext(), "${it.value}", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        putPhoto.launch(intent)
    }

    private val putPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val bitmap = it?.data?.extras?.get("data") as Bitmap
                imgPhoto.setImageBitmap(bitmap)            /* imgPhoto.load(bitmap) */
            } else if (it == null) {
                imgPhoto.setImageResource(R.drawable.ic_launcher_background)
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }

        }

    private fun insertData() {
        lifecycleScope.launch {
            val firstName  : String   = edt1.text.toString()
            val middleName : String   = edt2.text.toString()
            val lastName   : String   = edt3.text.toString()
            val image      :Bitmap    =  imgPhoto.drawable.toBitmap()

            if (checkInput(firstName, middleName, lastName)) {
                val data = User(0, firstName, middleName, lastName, image)
                viewModel.insertData(data)
                Toast.makeText(requireContext(), "Successfuly Add Data!!", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_addFragment_to_berandaFragment)
            } else {
                Toast.makeText(requireContext(), "Failed Add data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInput(firstName: String, middleName: String, lastname: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(middleName) && TextUtils.isEmpty(
            lastname
        ))

    }

//    override fun getViewBinding() = FragmentAddBinding.inflate(layoutInflater)


//    private suspend fun getBitmap(): Bitmap {
//        val loading = ImageLoader(requireContext())
//        val request = ImageRequest.Builder(requireContext())
//            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
//            .build()
//
//        val result = (loading.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}
