package com.example.profnotes.di.module

import com.example.profnotes.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository

    @Binds
    abstract fun provideRefreshTokenRepository(refreshTokenRepositoryImpl: RefreshTokenRepositoryImpl): RefreshTokenRepository

    @Binds
    abstract fun provideCoursesRepository(coursesRepositoryImpl: CoursesRepositoryImpl): CoursesRepository

    @Binds
    abstract fun provideNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository

    @Binds
    abstract fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun provideFavouriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): FavoriteRepository
}

