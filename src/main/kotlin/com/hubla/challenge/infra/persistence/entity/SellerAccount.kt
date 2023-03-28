package com.hubla.challenge.infra.persistence.entity

import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class SellerAccount(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Long = 0L,

    @OneToOne(fetch = LAZY, optional = true)
    @JoinColumn(name = "seller_id")
    val seller: Seller,

    val amount: Double = 0.00

)
