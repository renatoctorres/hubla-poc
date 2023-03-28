package com.hubla.challenge.core.model.objectmother

import com.hubla.challenge.infra.persistence.entity.Seller
import kotlin.random.Random

object SellerObjectMother {
    fun build(id: Long = Random.nextLong(), name: String = "name") = Seller(
        id = id,
        name = name,
    )
}
