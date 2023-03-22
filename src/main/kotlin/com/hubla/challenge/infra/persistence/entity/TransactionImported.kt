package com.hubla.challenge.infra.persistence.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
data class TransactionImported(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = 0L,

    val createdAt: LocalDate,

    @Enumerated(EnumType.STRING)
    val type: TransactionType?,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "product_id")
    val product: Product,

    val valueTransaction: Int,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "seller_id")
    val seller: Seller,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "importation_id")
    val importation: Importation
)