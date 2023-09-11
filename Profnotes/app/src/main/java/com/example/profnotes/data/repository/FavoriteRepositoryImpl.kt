package com.example.profnotes.data.repository

import com.example.profnotes.data.model.home.HomeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val notesRepository: NotesRepository,
    private val coursesRepository: CoursesRepository
) : FavoriteRepository {

    override suspend fun getDataFlow(): Flow<HomeData> {
        return combine(
            coursesRepository.getFavouriteCoursesFlow(),
            notesRepository.getFavouriteLocalNotesFlow(),
            notesRepository.getFavouriteCommunityNotesFlow()
        ) { courses, localNotes, communityNotes ->
            HomeData(courses, localNotes, communityNotes)
        }
    }

    override suspend fun search(text: String): Flow<HomeData> {
        return combine(
            coursesRepository.searchFavouritesCourses(text),
            notesRepository.searchFavouritesLocalNotes(text),
            notesRepository.searchFavouritesCommunityNotes(text)
        ) { courses, localNotes, communityNotes ->
            HomeData(courses, localNotes, communityNotes)
        }
    }
}