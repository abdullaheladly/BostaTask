package com.abdullah996.bostatask.repository.albums

import com.abdullah996.bostatask.model.albums.AlbumsResponse
import retrofit2.Response


interface AlbumsRepo {

    suspend fun getUserAlbums(
        userId:Int,
    ): Response<AlbumsResponse>
}