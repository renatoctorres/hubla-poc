package com.hubla.challenge.infra.persistence.entity

import java.util.*
import javax.persistence.*

@Entity
data class Importation(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val createdAt: Date = Date()

)
