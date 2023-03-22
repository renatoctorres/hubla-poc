package com.hubla.challenge.infra.persistence.entity

import javax.persistence.*

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    val id: Long = 0L,
    @Column(unique=true)
    val description: String
)