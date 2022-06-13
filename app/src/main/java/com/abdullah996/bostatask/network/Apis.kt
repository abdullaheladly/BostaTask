package com.abdullah996.bostatask.network

import com.abdullah996.bostatask.model.albums.AlbumsResponse
import com.abdullah996.bostatask.model.photos.PhotosResponse
import com.abdullah996.bostatask.model.users.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Apis {
    @GET("users")
    suspend fun getAllUsers(
    ): Response<UsersResponse>


    @GET("albums")
    suspend fun getUserAlbums(
        @Query("userId") userId:Int,
    ): Response<AlbumsResponse>

    @GET("photos")
    suspend fun getAlbumPhotos(
        @Query("albumId") albumId:Int,
    ): Response<PhotosResponse>


}