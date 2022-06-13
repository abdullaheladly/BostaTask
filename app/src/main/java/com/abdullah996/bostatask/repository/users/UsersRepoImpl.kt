package com.abdullah996.bostatask.repository.users

import com.abdullah996.bostatask.model.users.UsersResponse
import com.abdullah996.bostatask.network.Apis
import retrofit2.Response
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(private val apis: Apis):UsersRepo {
    override suspend fun getAllUsers(): Response<UsersResponse> {
        return apis.getAllUsers()
    }
}