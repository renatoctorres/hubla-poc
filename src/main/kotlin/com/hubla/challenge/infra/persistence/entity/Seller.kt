package com.hubla.challenge.infra.persistence.entity

import javax.persistence.*

@Entity
data class Seller(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(unique=true)
    val name: String
)
