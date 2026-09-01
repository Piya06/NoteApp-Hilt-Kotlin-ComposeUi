package com.noteapp.hiltdagger.di

import com.noteapp.hiltdagger.validation.MaxLengthValidator
import com.noteapp.hiltdagger.validation.NotEmptyValidator
import com.noteapp.hiltdagger.validation.NoteValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
object ValidatorModule {

    @Provides
    @IntoSet
    fun provideNotEmptyValidator(): NoteValidator = NotEmptyValidator()

    @Provides
    @IntoSet
    fun provideMaxLengthValidator(): NoteValidator = MaxLengthValidator(100)

}