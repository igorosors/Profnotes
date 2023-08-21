package com.example.profnotes.data.mapper

import com.example.profnotes.data.db.entity.NoteEntity
import com.example.profnotes.data.model.Author
import com.example.profnotes.data.model.Comment
import com.example.profnotes.data.model.Note
import com.example.profnotes.data.model.RichText
import com.example.profnotes.data.remote.model.ApiAuthor
import com.example.profnotes.data.remote.model.ApiComment
import com.example.profnotes.data.remote.model.ApiNote
import com.example.profnotes.data.remote.model.ApiRichText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class NoteMapper @Inject constructor(
    private val gson: Gson
) {

    fun fromApiToModel(note: ApiNote): Note {
        return Note(
            id = note.id.orEmpty(),
            title = note.title.orEmpty(),
            content = note.content.orEmpty().map { fromApiToModel(it) },
            author = fromApiToModel(note.author),
            date = note.date?.toLongOrNull() ?: 0,
            comments = note.comments.orEmpty().map { fromApiToModel(it) }
        )
    }

    private fun fromApiToModel(richText: ApiRichText): RichText {
        return RichText(
            text = richText.text.orEmpty(),
            image = richText.image.orEmpty()
        )
    }

    private fun fromApiToModel(author: ApiAuthor?): Author {
        return Author(
            id = author?.id.orEmpty(),
            name = author?.name.orEmpty(),
            surname = author?.surname.orEmpty(),
            role = author?.role.orEmpty()
        )
    }

    private fun fromApiToModel(comment: ApiComment): Comment {
        return Comment(
            id = comment.id.orEmpty(),
            author = fromApiToModel(comment.author),
            text = comment.text.orEmpty(),
            attachments = comment.attachments.orEmpty(),
            status = comment.status.orEmpty()
        )
    }

    fun fromModelToEntity(note: Note): NoteEntity {
        return NoteEntity(
            id = note.id,
            isLocal = if (note.isLocal) 1 else 0,
            isFavorite = if (note.isFavorite) 1 else 0,
            title = note.title,
            content = gson.toJson(note.content),
            author = gson.toJson(note.author),
            date = note.date,
            comments = gson.toJson(note.comments)
        )
    }

    fun fromEntityToModel(note: NoteEntity): Note {
        val contentTypeToken = object : TypeToken<List<RichText>>() {}.type
        val commentsTypeToken = object : TypeToken<List<Comment>>() {}.type
        return Note(
            id = note.id,
            isLocal = note.isLocal == 1,
            isFavorite = note.isFavorite == 1,
            title = note.title,
            content = gson.fromJson(note.content, contentTypeToken),
            author = gson.fromJson(note.author, Author::class.java),
            date = note.date,
            comments = gson.fromJson(note.comments, commentsTypeToken)
        )
    }

    fun fromModelToApi(richText: RichText) : ApiRichText {
        return ApiRichText(
            text = richText.text,
            image = richText.image
        )
    }
}