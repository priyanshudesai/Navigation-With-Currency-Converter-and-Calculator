package com.pd.currencyconverter.dataclass

import java.io.Serializable

data class Card(
    val back: Back,
    val front: Front
): Serializable