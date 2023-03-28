package com.hubla.challenge.infra.persistence.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
@Entity
data class TransactionImported(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Long,

    val date: Date,

    @Enumerated(STRING)
    val type: TransactionType,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,

    val valueTransaction: Double,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "seller_id")
    val seller: Seller,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "importation_id")
    val importation: Importation
)
