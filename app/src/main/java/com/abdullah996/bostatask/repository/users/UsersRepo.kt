package com.abdullah996.bostatask.repository.users

import com.abdullah996.bostatask.model.users.UsersResponse
import retrofit2.Response

interface UsersRepo {
    suspend fun getAllUsers(
    ): Response<UsersResponse>
}