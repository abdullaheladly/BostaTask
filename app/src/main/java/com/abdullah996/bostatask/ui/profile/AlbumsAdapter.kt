package com.abdullah996.bostatask.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullah996.bostatask.databinding.AlbumsItemRowBinding
import com.abdullah996.bostatask.model.albums.AlbumsResponseItem
import com.abdullah996.bostatask.util.DiffUtilCallBack

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.MyViewHolder>(){
    private var currenciesList= emptyList<AlbumsResponseItem>()




   inner class MyViewHolder(val binding: AlbumsItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding=AlbumsItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(currenciesList[position]){
                binding.albumName.text=this.title.toString()
            }
        }

    }

    override fun getItemCount(): Int {
        return currenciesList.size
    }

    fun saveData(newActionsList:List<AlbumsResponseItem>){
        val leadsListDiffUtil= DiffUtilCallBack(currenciesList,newActionsList)
        val diffUtilResult= DiffUtil.calculateDiff(leadsListDiffUtil)
        currenciesList=newActionsList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}