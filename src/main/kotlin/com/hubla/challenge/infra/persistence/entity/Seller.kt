package com.hubla.challenge.infra.persistence.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id

@Entity
data class Seller(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Long,
    @Column(unique=true)
    val name: String
)
