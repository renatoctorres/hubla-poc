package com.hubla.challenge.infra.persistence.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
data class Importation(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = 0L,
    val createdAt: LocalDate

)
