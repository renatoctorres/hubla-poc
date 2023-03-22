package com.hubla.challenge.infra.persistence.entity.extension

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

val locale = Locale("pt", "BR")

fun Double.convertToBRL() = NumberFormat
                .getCurrencyInstance(locale)
                .format(this.toDouble());

fun Int.convertCentsInBRL() = (this.toDouble()/100)