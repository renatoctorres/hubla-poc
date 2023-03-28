package com.hubla.challenge.core.model.extensions

import com.hubla.challenge.core.model.ProductDTO
import com.hubla.challenge.core.model.SellerAccountDTO
import com.hubla.challenge.core.model.SellerDTO
import com.hubla.challenge.core.model.TransactionDTO
import com.hubla.challenge.core.model.objectmother.ProductObjectMother
import com.hubla.challenge.core.model.objectmother.SellerAccountObjectMother
import com.hubla.challenge.core.model.objectmother.SellerObjectMother
import com.hubla.challenge.core.model.objectmother.TransactionImportedObjectMother
import com.hubla.challenge.infra.persistence.entity.Product
import com.hubla.challenge.infra.persistence.entity.Seller
import com.hubla.challenge.infra.persistence.entity.SellerAccount
import com.hubla.challenge.infra.persistence.entity.TransactionImported
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ObjectExtensionTest {
    private val product: Product = ProductObjectMother.build()
    private val seller: Seller = SellerObjectMother.build()
    private val sellerAccount: SellerAccount = SellerAccountObjectMother.build()
    private val transaction: TransactionImported = TransactionImportedObjectMother.build()
    private val transactions: List<TransactionImported> = listOf(transaction)

    @Nested
    inner class WhenConvertProductToModel {
        @Test
        fun `it should convert ProductDTO to Model`() {
            val expected: Product = product
            val actual: ProductDTO = product.toModel()
            assertEquals(expected.id, actual.id)
            assertEquals(expected.description, actual.description)
        }
    }

    @Nested
    inner class WhenConvertSellerToModel {
        @Test
        fun `it should convert SellerDTO to Model`() {
            val expected: Seller = seller
            val actual: SellerDTO = seller.toModel()
            assertEquals(expected.id, actual.id)
            assertEquals(expected.name, actual.name)
        }
    }

    @Nested
    inner class WhenConvertSellerAccountToModel {
        @Test
        fun `it should convert SellerAccountDTO to Model`() {
            val expected: SellerAccount = sellerAccount
            val actual: SellerAccountDTO = sellerAccount.toModel()
            assertEquals(expected.id, actual.id)
            assertEquals(expected.seller.name, actual.seller.name)
            assertEquals(expected.seller.id, actual.seller.id)
            assertEquals(expected.amount, actual.amount)
        }
    }

    @Nested
    inner class WhenConvertTransactionImportedListToModelList {
        @Test
        fun `Should convert Transaction List to Model`() {
            val expected: List<TransactionImported> = transactions
            val actual: List<TransactionDTO> = transactions.toListModel()
            assertEquals(expected.size, actual.size)
            assertEquals(expected[0].id, actual[0].id)
            assertEquals(expected[0].importation.id, actual[0].idImportation)
            assertEquals(expected[0].seller.id, actual[0].seller.id)
            assertEquals(expected[0].seller.name, actual[0].seller.name)
            assertEquals(expected[0].product.id, actual[0].product.id)
            assertEquals(expected[0].product.description, actual[0].product.description)
            assertEquals(expected[0].type.code, actual[0].type)
        }
    }

    @Nested
    inner class WhenConvertProductDTOToEntity {
        @Test
        fun `it should convert ProductDTO to Entity`() {
            val expected: ProductDTO = product.toModel()
            val actual: Product = expected.toEntity()
            assertEquals(expected.id, actual.id)
            assertEquals(expected.description, actual.description)
        }
    }

    @Nested
    inner class WhenConvertSellerDTOToEntity {
        @Test
        fun `it should convert SellerDTO to Entity`() {
            val expected: SellerDTO = seller.toModel()
            val actual: Seller = expected.toEntity()
            assertEquals(expected.id, actual.id)
            assertEquals(expected.name, actual.name)
        }
    }

    @Nested
    inner class WhenConvertSellerAccountDTOToEntity {
        @Test
        fun `it should convert SellerAccountDTO to Entity`() {
            val expected: SellerAccountDTO = sellerAccount.toModel()
            val actual: SellerAccount = expected.toEntity()
            assertEquals(expected.id, actual.id)
            assertEquals(expected.amount, actual.amount)
            assertEquals(expected.seller.id, actual.seller.id)
            assertEquals(expected.seller.name, actual.seller.name)
        }
    }

    @Nested
    inner class WhenConvertTransactionDTOToEntity {
        @Test
        fun `it should convert TransactionDTO to Entity`() {
            val expected: TransactionDTO = transaction.toModel()
            expected.date = "2022-01-15T19:20:30-03:00"
            val actual: TransactionImported = expected.toEntity()
            assertEquals(expected.id, actual.id)
            assertEquals(expected.seller.toEntity(), actual.seller)
            assertEquals(expected.product.toEntity(), actual.product)
            assertEquals(expected.idImportation, actual.importation.id)
            assertEquals(expected.valueTransaction.toDouble(), actual.valueTransaction)
        }
    }
}
