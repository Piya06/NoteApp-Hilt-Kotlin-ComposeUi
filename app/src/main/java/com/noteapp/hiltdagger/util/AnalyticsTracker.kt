package com.noteapp.hiltdagger.util

import javax.inject.Inject

interface AnalyticsTracker {
    fun trackScreenView(screenName: String)
}


class AnalyticsTrackerImpl @Inject constructor() : AnalyticsTracker{
    override fun trackScreenView(screenName: String) {
        println("Analytics screen view: $screenName")
    }


}