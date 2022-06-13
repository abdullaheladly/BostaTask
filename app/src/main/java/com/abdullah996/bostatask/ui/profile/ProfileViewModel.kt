package com.abdullah996.bostatask.ui.profile

import android.app.Application
import com.abdullah996.bostatask.base.BaseViewModel
import com.abdullah996.bostatask.repository.albums.AlbumsRepo
import com.abdullah996.bostatask.repository.users.UsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val usersRepo: UsersRepo,
    private val albumsRepo: AlbumsRepo,
    application: Application
): BaseViewModel(application) {

    fun getAllUser()=handleFlowResponse {
        usersRepo.getAllUsers()
    }


    fun getUserAlbums(userId:Int)=handleFlowResponse {
        albumsRepo.getUserAlbums(userId)
    }
}