package com.hubla.challenge.core.model.objectmother

import com.hubla.challenge.infra.persistence.entity.Product
import kotlin.random.Random

object ProductObjectMother {
    fun build(id: Long = Random.nextLong(), description: String = "description") = Product(
        id = id,
        description = description,
    )
}
