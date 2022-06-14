package com.abdullah996.bostatask.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullah996.bostatask.databinding.PhotosItemRowBinding
import com.abdullah996.bostatask.model.photos.PhotosResponseItem
import com.abdullah996.bostatask.util.DiffUtilCallBack
import com.squareup.picasso.Picasso

class PhotosAdapter(private val onPhotoClickListeners: OnPhotoClickListeners) : RecyclerView.Adapter<PhotosAdapter.MyViewHolder>(){
    private var currenciesList= emptyList<PhotosResponseItem>()




   inner class MyViewHolder(val binding: PhotosItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding=PhotosItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(currenciesList[position]){
                Picasso.get().load(thumbnailUrl).into(binding.albumPhoto)
                binding.root.setOnClickListener {
                    thumbnailUrl?.let { it1 -> onPhotoClickListeners.onPhotoItemClick(it1) }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return currenciesList.size
    }

    fun saveData(newActionsList:List<PhotosResponseItem>){
        if (newActionsList.isNullOrEmpty()){
            onPhotoClickListeners.onEmptyListPassed()
        }
        val leadsListDiffUtil= DiffUtilCallBack(currenciesList,newActionsList)
        val diffUtilResult= DiffUtil.calculateDiff(leadsListDiffUtil)
        currenciesList=newActionsList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}