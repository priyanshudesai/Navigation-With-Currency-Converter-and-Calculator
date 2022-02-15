package com.pd.currencyconverter.utils

import android.graphics.Bitmap
import com.pd.currencyconverter.R

object ConstantUtils {

    lateinit var bitmap: Bitmap

    const val ALARM_NOTIFICATION_CHANNEL_ID = "ALARM_CHANNEL_ID"
    const val ALARM_NOTIFICATION_CHANNEL_NAME = "Alarm Manager"
    const val PUSH_NOTIFICATION_CHANNEL_ID = "PUSH_CHANNEL_ID"
    const val PUSH_NOTIFICATION_CHANNEL_NAME = "Push Notification"

    const val KEY_THEME = "mynewtheme"
    const val KEY_LANGUAGE = "mynewlanguage"
    const val KEY_TOKEN = "mynewtoken"
    const val NORMAL = R.style.Theme_CurrencyConverter_NoActionBar
    const val DARK = R.style.Theme_CurrencyConverter_NoActionBar_Dark
    const val CHRISTMAS = R.style.Theme_CurrencyConverter_NoActionBar_Christmas

    const val API_BASE_URL = "https://private-c5d570-test23976.apiary-mock.com/"

    const val DATABASE_NAME = "CardDB"
    const val TABLE_NAME_CARD_INFORMATION = "cardInformation"
    const val TABLE_NAME_ALARM_INFORMATION = "alarmInformation"
    const val SELECT_QUERY_FOR_ALL_CARDS = "SELECT * FROM ".plus(TABLE_NAME_CARD_INFORMATION)
    const val SELECT_QUERY_FOR_ALL_ALARM = "SELECT * FROM ".plus(TABLE_NAME_ALARM_INFORMATION)
    const val DELETE_FROM_ALARM_INFORMATION =
        "DELETE FROM ".plus(TABLE_NAME_ALARM_INFORMATION).plus(" WHERE id = :id")

    const val INTENT_PARSE_DATA = "data"
    const val API_LIST_EMPLOYEES = "list"
    const val CARD_DETAILS_TITLE = "Test Card"
    const val CURRENT_DATE_FORMAT = "yyyy-MM-dd"
    const val CHANGED_DATE_FORMAT = "dd MMM yyyy"

    const val MSG_SOME_ERROR_OCCURRED = "Some error occurred. Please try again!"
    const val MSG_NO_DATA_FOUND = "No Data Found"


}