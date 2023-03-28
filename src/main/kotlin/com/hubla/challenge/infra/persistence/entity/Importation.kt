package com.hubla.challenge.infra.persistence.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id

@Entity
data class Importation(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Long,
    val createdAt: Date = Date()

)
