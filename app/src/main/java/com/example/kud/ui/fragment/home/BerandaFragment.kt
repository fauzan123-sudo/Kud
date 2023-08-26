package com.example.kud.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kud.R
import com.example.kud.data.adapter.UserAdapter
import com.example.kud.databinding.FragmentBerandaBinding
import com.example.kud.ui.activity.LoginActivity
import com.example.kud.ui.base.BaseFragment
import com.example.kud.ui.viewModel.AlbumViewModel
import com.example.kud.utils.Constants.TAG
import com.example.kud.utils.TokenManager
import com.example.kud.utils.UserPreferences
import com.example.kud.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BerandaFragment :
    BaseFragment<FragmentBerandaBinding>(FragmentBerandaBinding::inflate) {

    @Inject
    lateinit var tokenManager: TokenManager
    private val viewModel: AlbumViewModel by viewModels()
    lateinit var adapter: UserAdapter

    private var mList = mutableListOf<String>()

    @Inject
    lateinit var userPreferences: UserPreferences

    private lateinit var recyclerview: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter()
        recyclerview = binding.recv
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.setHasFixedSize(true)

        viewModel.readData.observe(viewLifecycleOwner) {
            adapter.differ.submitList(it)
            mList.add(it.toString())
        }

        binding.logOut.setOnClickListener {
            tokenManager.deleteToken()
            requireActivity().startNewActivity(LoginActivity::class.java)
//            lifecycleScope.launch {
//                userPreferences.clear()
//            }

        }

        userPreferences.accessToken.asLiveData().observe(requireActivity()) {
            Log.d(TAG + "logins", it.toString())
        }

        lifecycleScope.launch {
            val datas = userPreferences.read()
            Log.d(TAG, " read token user  $datas")
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_addFragment)
        }

        setHasOptionsMenu(true)
//        swipeDeleteItem()
        swipeDelete()

    }

    private fun swipeDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val budget = adapter.differ.currentList[pos]

                viewModel.deleteUser(budget)

                Toast.makeText(requireContext(), "item deleted", Toast.LENGTH_SHORT).show()

//                Snackbar.make(recyclerview(),"Item Deleted",Snackbar.LENGTH_LONG).apply {
//                    setAction("UNDO"){
//                        budgetViewModel.insertBudget(budget)
//                    }
//                    show()
//                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recv)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.item_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            deleteAllUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteAllUser()
            Toast.makeText(requireContext(), "Successfully for delete", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete")
        builder.setMessage("Are you sure for delete")
        builder.create().show()

    }

    private fun swipeDeleteItem() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.differ.currentList[position]
                Log.d(TAG, mList.toString())
                mList.removeAt(position)
//                viewModel.deleteUser(item)
                binding.recv.adapter?.notifyItemRemoved(position)
                Toast.makeText(
                    requireContext(),
                    "Successfully Delete Item $item",
                    Toast.LENGTH_LONG
                )
                    .show()
            }

        }).attachToRecyclerView(recyclerview)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}