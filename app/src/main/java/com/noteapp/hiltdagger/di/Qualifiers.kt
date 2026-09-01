package com.noteapp.hiltdagger.di

import javax.inject.Qualifier

/*
class Qualifiers {
}*/

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NotEmptyValidation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MaxLengthValidation

