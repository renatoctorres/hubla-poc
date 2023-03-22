package com.hubla.challenge.infra.persistence.entity

import javax.persistence.*

@Entity
data class SellerAccount (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0L,

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "seller_id")
    val seller: Seller,

    val amount: Double = 0.00

)