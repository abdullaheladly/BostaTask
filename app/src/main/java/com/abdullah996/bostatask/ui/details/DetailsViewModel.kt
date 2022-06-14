package com.abdullah996.bostatask.ui.details

import android.app.Application
import com.abdullah996.bostatask.base.BaseViewModel
import com.abdullah996.bostatask.repository.photos.PhotosRepo
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject


@HiltViewModel
class DetailsViewModel@Inject constructor(
    private val  photosRepo: PhotosRepo,
    application: Application
): BaseViewModel(application) {

    fun getAllPhotos(albumId:Int)=handleFlowResponse {
        photosRepo.getAlbumPhotos(albumId)
    }
}