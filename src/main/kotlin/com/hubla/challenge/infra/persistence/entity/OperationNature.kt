package com.hubla.challenge.infra.persistence.entity

enum class OperationNature(val signal: String) {
    DEBIT(signal = "-"),
    CREDIT(signal = "+")
}
