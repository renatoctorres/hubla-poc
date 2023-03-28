package com.hubla.challenge.core.model.objectmother

import com.hubla.challenge.infra.persistence.entity.TransactionImported
import com.hubla.challenge.infra.persistence.entity.TransactionType
import java.util.*
import kotlin.random.Random

object TransactionImportedObjectMother {
    fun build(): TransactionImported {
        return TransactionImported(
            product = ProductObjectMother.build(),
            seller = SellerObjectMother.build(),
            importation = ImportationObjectMother.build(),
            valueTransaction = Random.nextDouble(),
            date = Date(),
            type = TransactionType.AFFILIATE_SALE,
            id = Random.nextLong(),
        )
    }
}
