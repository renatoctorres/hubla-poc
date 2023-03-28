package com.hubla.challenge.core.model.objectmother

import com.hubla.challenge.infra.persistence.entity.Seller
import com.hubla.challenge.infra.persistence.entity.SellerAccount

object SellerAccountObjectMother {
    private val stub = SellerObjectMother.build()
    fun build(id: Long = stub.id, seller: Seller = stub) = SellerAccount(
        id = id,
        seller = seller,
    )
}
