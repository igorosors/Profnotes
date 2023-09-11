package com.example.profnotes.data.repository

import com.example.profnotes.data.model.home.HomeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val notesRepository: NotesRepository,
    private val coursesRepository: CoursesRepository
) : HomeRepository {

    override suspend fun loadData(): HomeData {
        return HomeData(
            coursesRepository.getCourses(),
            listOf(),
            notesRepository.getNotes()
        )
    }

    override suspend fun saveData(homeData: HomeData) {
        coursesRepository.saveCourses(homeData.courses)
        notesRepository.saveNote(*homeData.communityNotes.toTypedArray())
    }

    override suspend fun getDataFlow(): Flow<HomeData> {
        return combine(
            coursesRepository.getCoursesFlow(),
            notesRepository.getLocalNotesFlow(),
            notesRepository.getCommunityNotesFlow()
        ) { courses, localNotes, communityNotes ->
            HomeData(courses, localNotes, communityNotes)
        }
    }

    override suspend fun search(text: String): Flow<HomeData> {
        return combine(
            coursesRepository.searchCourses(text),
            notesRepository.searchLocalNotes(text),
            notesRepository.searchCommunityNotes(text)
        ) { courses, localNotes, communityNotes ->
            HomeData(courses, localNotes, communityNotes)
        }
    }
}