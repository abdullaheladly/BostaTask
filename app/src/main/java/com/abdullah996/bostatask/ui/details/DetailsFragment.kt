package com.abdullah996.bostatask.ui.details

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.abdullah996.bostatask.databinding.FragmentDetailsBinding
import com.abdullah996.bostatask.model.photos.PhotosResponseItem
import com.abdullah996.bostatask.util.ApiStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class DetailsFragment : Fragment(),OnPhotoClickListeners {

    private var _binding: FragmentDetailsBinding?=null
    private val binding get() = _binding!!

    private var job: Job?=null


    private val detailsViewModel:DetailsViewModel by viewModels()


    private val photosAdapter by lazy { PhotosAdapter(this) }

    private val args by navArgs<DetailsFragmentArgs>()
    private var photosList= emptyList<PhotosResponseItem>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentDetailsBinding.inflate(layoutInflater,container,false)

        setupRecycleView()
        getAlbumPhotos()
        setupSwipe()
        setListeners()


        return binding.root
    }

    private fun setupSwipe() {
        binding.sToRefresh.setOnRefreshListener {
            binding.searchView.setQuery("",false)
            binding.searchView.clearFocus()
            if (hasInternetConnection()) {
                getAlbumPhotos()
                binding.rvAlbums.visibility = View.VISIBLE
            } else {
                binding.rvAlbums.visibility = View.GONE
                Toast.makeText(requireContext(), "No Internet connection", Toast.LENGTH_SHORT)
                    .show()
                binding.sToRefresh.isRefreshing = false
            }
        }
    }

    private fun setListeners() {

        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {


                val newList= photosList.filter {
                    it.title?.contains(p0.toString()) == true
                }
                photosAdapter.saveData(newList)
                return true

            }

            override fun onQueryTextChange(p0: String?): Boolean {
                job?.cancel()
               job= lifecycleScope.launch {
                    delay(1000)
                    val newList = photosList.filter {
                        it.title?.contains(p0.toString()) == true
                    }
                    photosAdapter.saveData(newList)
                }
                return true
            }

        })
    }

    private fun getAlbumPhotos() {
        detailsViewModel.getAllPhotos(args.albumsId).observe(viewLifecycleOwner){
            when (it.status) {
                ApiStatus.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()){

                        photosList=it.data
                       photosAdapter.saveData(it.data)
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

    private fun setupRecycleView() {
        binding.rvAlbums.adapter=photosAdapter
        binding.rvAlbums.layoutManager= GridLayoutManager(requireContext(),3)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onPhotoItemClick(imageUrl: String) {
        ImageViewerDialog(imageUrl).show(childFragmentManager, "pp")
    }

    override fun onEmptyListPassed() {
        Toast.makeText(requireContext(), "No data matches your search", Toast.LENGTH_SHORT).show()
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


}