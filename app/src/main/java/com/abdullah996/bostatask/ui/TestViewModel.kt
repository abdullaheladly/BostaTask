package com.abdullah996.bostatask.ui

import android.app.Application
import com.abdullah996.bostatask.base.BaseViewModel
import com.abdullah996.bostatask.repository.users.UsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class TestViewModel @Inject constructor(
    private val usersRepo: UsersRepo,
    application: Application
):BaseViewModel(application) {

    fun getAllUser()=handleFlowResponse {
        usersRepo.getAllUsers()
    }
}