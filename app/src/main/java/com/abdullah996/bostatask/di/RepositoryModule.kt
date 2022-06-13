package com.abdullah996.bostatask.di

import com.abdullah996.bostatask.repository.albums.AlbumsRepo
import com.abdullah996.bostatask.repository.albums.PhotosRepoImpl
import com.abdullah996.bostatask.repository.photos.AlbumsRepoImpl
import com.abdullah996.bostatask.repository.photos.PhotosRepo
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

    @Binds
    abstract fun provideAlbumsRepository(impl: AlbumsRepoImpl) : AlbumsRepo

    @Binds
    abstract fun providePhotosRepository(impl: PhotosRepoImpl) : PhotosRepo
}