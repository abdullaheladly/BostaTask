package com.abdullah996.bostatask.repository.photos

import com.abdullah996.bostatask.model.photos.PhotosResponse
import retrofit2.Response


interface PhotosRepo {

    suspend fun getAlbumPhotos(
         albumId:Int,
    ): Response<PhotosResponse>
}