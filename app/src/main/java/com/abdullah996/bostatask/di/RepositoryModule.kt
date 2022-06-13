package com.abdullah996.bostatask.di

import com.abdullah996.bostatask.repository.users.UsersRepo
import com.abdullah996.bostatask.repository.users.UsersRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideUsersRepository(impl: UsersRepoImpl) : UsersRepo
}