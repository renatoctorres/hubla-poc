package com.hubla.challenge.core.model.objectmother

import com.hubla.challenge.infra.persistence.entity.Importation
import kotlin.random.Random

object ImportationObjectMother {
    fun build(id: Long = Random.nextLong()) = Importation(
        id = id,
    )
}
