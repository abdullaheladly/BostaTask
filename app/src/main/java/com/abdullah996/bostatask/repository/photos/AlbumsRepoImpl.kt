package com.abdullah996.bostatask.repository.photos

import com.abdullah996.bostatask.model.albums.AlbumsResponse
import com.abdullah996.bostatask.network.Apis
import com.abdullah996.bostatask.repository.albums.AlbumsRepo
import retrofit2.Response
import javax.inject.Inject

class AlbumsRepoImpl @Inject constructor(private val apis: Apis) :AlbumsRepo {
    override suspend fun getUserAlbums(userId: Int): Response<AlbumsResponse> {
        return apis.getUserAlbums(userId)
    }
}