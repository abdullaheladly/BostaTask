package com.abdullah996.bostatask.repository.albums

import com.abdullah996.bostatask.model.photos.PhotosResponse
import com.abdullah996.bostatask.network.Apis
import com.abdullah996.bostatask.repository.photos.PhotosRepo
import retrofit2.Response
import javax.inject.Inject

class PhotosRepoImpl @Inject constructor(private val apis: Apis) :PhotosRepo {
    override suspend fun getAlbumPhotos(albumId: Int): Response<PhotosResponse> {
        return apis.getAlbumPhotos(albumId)
    }
}