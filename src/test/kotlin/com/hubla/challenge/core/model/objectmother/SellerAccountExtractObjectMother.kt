package com.hubla.challenge.core.model.objectmother

import com.hubla.challenge.infra.persistence.entity.Seller
import com.hubla.challenge.infra.persistence.entity.SellerAccount
import kotlin.random.Random

object SellerAccountExtractObjectMother {
    fun build(id: Long = Random.nextLong(), seller: Seller = SellerObjectMother.build()) = SellerAccount(
        id = id,
        seller = seller,
    )
}
