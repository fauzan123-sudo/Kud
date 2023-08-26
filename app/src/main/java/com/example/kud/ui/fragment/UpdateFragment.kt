package com.example.kud.ui.fragment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.kud.R
import com.example.kud.data.model.User
import com.example.kud.databinding.FragmentUpdateBinding
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.AlbumViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : BaseFragment<FragmentUpdateBinding>(FragmentUpdateBinding::inflate) {

    private val args by navArgs<UpdateFragmentArgs>()

    //    private lateinit var viewModel:AlbumViewModel
    private val viewModel: AlbumViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argUpd = args.update
        binding.edt1.setText(argUpd.firstName)
        binding.edt2.setText(argUpd.middleName)
        binding.edt3.setText(argUpd.lastName)

        binding.btnUpdt.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)
    }

    private fun updateItem() {
        val updateFirstName = binding.edt1.text.toString()
        val updatedName = binding.edt2.text.toString()
        val updateLastName = binding.edt3.text.toString()

        if (checkInput(updateFirstName, updatedName, updateLastName)) {
            val update = User(args.update.id, updateFirstName, updatedName, updateLastName, null)
//            viewModel.updateData(update)
            Toast.makeText(requireContext(), "Successfully Update Data!!", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_updateFragment_to_berandaFragment)
        } else {
            Toast.makeText(requireContext(), "Please Fill out the Field", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun checkInput(firstName: String, middleName: String, lastname: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(middleName) && TextUtils.isEmpty(
            lastname
        ))

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.item_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
//            viewModel.deleteUser(args.update)
            Toast.makeText(
                requireContext(),
                "Successfully for delete : ${args.update.firstName}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_berandaFragment)
        }

        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.update.firstName}?")
        builder.setMessage("Are you sure for delete ${args.update.firstName}")
        builder.create().show()

    }

}