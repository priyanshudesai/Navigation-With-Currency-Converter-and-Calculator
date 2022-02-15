package com.pd.currencyconverter.utils

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

object FirebaseAnalyticsHelper {

    var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    fun logScreenEvent(screenName: String, screenClass: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
        }
    }

    fun logClickEvent(itemId: String, itemName: String, type: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, itemId)
            param(FirebaseAnalytics.Param.ITEM_NAME, itemName)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, type)
        }
    }
}