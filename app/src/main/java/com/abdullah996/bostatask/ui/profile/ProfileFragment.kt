package com.abdullah996.bostatask.ui.profile

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullah996.bostatask.R
import com.abdullah996.bostatask.databinding.FragmentProfileBinding
import com.abdullah996.bostatask.model.users.UsersResponseItem
import com.abdullah996.bostatask.util.ApiStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random



@AndroidEntryPoint
class ProfileFragment : Fragment(),OnAlbumsClickListeners {

    private var _binding:FragmentProfileBinding?=null
    private val binding get() = _binding!!

    private val profileViewModel:ProfileViewModel by viewModels()
    private var usersList= emptyList<UsersResponseItem>()
    private val albumsAdapter by lazy { AlbumsAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentProfileBinding.inflate(layoutInflater,container,false)
       handleNoInternetConnection()
        setupAdapter()
        setupSwipe()
        return binding.root
    }

    private fun setupSwipe() {
        binding.sToRefresh.setOnRefreshListener {
            binding.sToRefresh.isRefreshing =true
           handleNoInternetConnection()

        }
    }


    private fun handleNoInternetConnection(){
        if (hasInternetConnection()){
            getRandomUser()
            binding.rvAlbums.visibility=View.VISIBLE
            binding.txtError.visibility=View.GONE
            binding.txtName.visibility=View.VISIBLE
            binding.txtAddress.visibility=View.VISIBLE


        }
        else{
            binding.rvAlbums.visibility=View.GONE
            binding.txtError.visibility=View.VISIBLE
            binding.txtName.visibility=View.GONE
            binding.txtAddress.visibility=View.GONE
            Toast.makeText(requireContext(), "No Internet connection", Toast.LENGTH_SHORT).show()
            binding.sToRefresh.isRefreshing=false
        }
    }

    private fun setupAdapter() {
        binding.rvAlbums.adapter=albumsAdapter
        binding.rvAlbums.layoutManager=LinearLayoutManager(requireContext())
        /*binding.rvAlbums.itemAnimator=SlideInLeftAnimator().apply {
            addDuration=500
        }*/
    }

    private fun getRandomUser() {
        profileViewModel.getAllUser().observe(viewLifecycleOwner) {
            when (it.status) {
                ApiStatus.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()){
                        usersList=it.data
                        getUserAlbum()
                    }
                    binding.sToRefresh.isRefreshing=false

                }
                ApiStatus.ERROR -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                    binding.sToRefresh.isRefreshing=false

                }
                ApiStatus.LOADING -> {

                }
            }
        }
    }

    private fun getUserAlbum() {
       val user= usersList.random()
        binding.txtName.text=user.name

        val address="${user.address?.city} ${user.address?.street} ${user.address?.zipcode} "
        binding.txtAddress.text=address
        user.id?.let { userId->
            profileViewModel.getUserAlbums(userId).observe(viewLifecycleOwner) {
                when (it.status) {
                    ApiStatus.SUCCESS -> {

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
    private fun hasInternetConnection():Boolean{
        val connectivityManager=requireActivity().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork=connectivityManager.activeNetwork?:return false
        val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return  when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onAlbumItemClick(albumId: Int) {
        val action=ProfileFragmentDirections.actionProfileFragmentToDetailsFragment(albumId)
        findNavController().navigate(action)
    }


}