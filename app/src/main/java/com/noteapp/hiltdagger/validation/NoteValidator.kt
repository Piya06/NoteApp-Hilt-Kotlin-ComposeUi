package com.noteapp.hiltdagger.validation

interface NoteValidator {
    fun validate(input: String): Boolean
}

class NotEmptyValidator: NoteValidator {
    override fun validate(input: String): Boolean = input.isNotBlank()
}

class MaxLengthValidator(private val maxLength: Int= 100): NoteValidator{
    override fun validate(input: String): Boolean = input.length <= maxLength
}