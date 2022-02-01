package com.pd.currencyconverter.utils

import com.pd.currencyconverter.R

object ConstantUtils {

    const val KEY_THEME = "mynewtheme"
    const val KEY_LANGUAGE = "mynewlanguage"
    const val NORMAL = R.style.Theme_CurrencyConverter_NoActionBar
    const val DARK = R.style.Theme_CurrencyConverter_NoActionBar_Dark
    const val CHRISTMAS = R.style.Theme_CurrencyConverter_NoActionBar_Christmas

    const val API_BASE_URL = "https://private-c5d570-test23976.apiary-mock.com/"

    const val DATABASE_NAME = "CardDB"
    const val TABLE_NAME_CARD_INFORMATION = "cardInformation"
    const val SELECT_QUERY_FOR_ALL_CARDS = "SELECT * FROM ".plus(TABLE_NAME_CARD_INFORMATION)

    const val INTENT_PARSE_DATA = "data"
    const val API_LIST_EMPLOYEES = "list"
    const val CARD_DETAILS_TITLE = "Test Card"
    const val CURRENT_DATE_FORMAT = "yyyy-MM-dd"
    const val CHANGED_DATE_FORMAT = "dd MMM yyyy"
    
    const val MSG_SOME_ERROR_OCCURRED = "Some error occurred. Please try again!"
    const val MSG_NO_DATA_FOUND = "No Data Found"



}