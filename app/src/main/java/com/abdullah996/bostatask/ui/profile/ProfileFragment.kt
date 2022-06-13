package com.abdullah996.bostatask.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah996.bostatask.R
import com.abdullah996.bostatask.databinding.FragmentProfileBinding
import com.abdullah996.bostatask.model.users.UsersResponseItem
import com.abdullah996.bostatask.util.ApiStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random



@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding:FragmentProfileBinding?=null
    private val binding get() = _binding!!

    private val profileViewModel:ProfileViewModel by viewModels()
    private var usersList= emptyList<UsersResponseItem>()
    private val albumsAdapter by lazy { AlbumsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentProfileBinding.inflate(layoutInflater,container,false)
        getRandomUser()
        setupAdapter()
        return binding.root
    }

    private fun setupAdapter() {
        binding.rvAlbums.adapter=albumsAdapter
        binding.rvAlbums.layoutManager=LinearLayoutManager(requireContext())

    }

    private fun getRandomUser() {
        profileViewModel.getAllUser().observe(viewLifecycleOwner) {
            when (it.status) {
                ApiStatus.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()){
                        usersList=it.data
                        getUserAlbum()
                    }

                }
                ApiStatus.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        }
    }

    private fun getUserAlbum() {
       val user= usersList.random()
        user.id?.let { userId->
            profileViewModel.getUserAlbums(userId).observe(viewLifecycleOwner) {
                when (it.status) {
                    ApiStatus.SUCCESS -> {
                        Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                            .show()
                        it.data?.let { it1 -> albumsAdapter.saveData(it1) }


                    }
                    ApiStatus.ERROR -> {
                        Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                            .show()

                    }
                    ApiStatus.LOADING -> {

                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}