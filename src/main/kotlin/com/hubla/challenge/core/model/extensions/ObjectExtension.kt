package com.hubla.challenge.core.model.extensions

import com.hubla.challenge.core.model.ProductDTO
import com.hubla.challenge.core.model.SellerAccountDTO
import com.hubla.challenge.core.model.SellerDTO
import com.hubla.challenge.core.model.TransactionDTO
import com.hubla.challenge.infra.persistence.entity.Importation
import com.hubla.challenge.infra.persistence.entity.Product
import com.hubla.challenge.infra.persistence.entity.Seller
import com.hubla.challenge.infra.persistence.entity.SellerAccount
import com.hubla.challenge.infra.persistence.entity.TransactionImported
import com.hubla.challenge.infra.persistence.entity.TransactionType
import java.text.SimpleDateFormat

const val DIVISOR = 100
const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
fun ProductDTO.toEntity(): Product = Product(description = this.description, id = this.id)
fun SellerDTO.toEntity(): Seller = Seller(name = this.name, id = this.id)
fun SellerAccountDTO.toEntity(): SellerAccount = SellerAccount(
    seller = this.seller.toEntity(),
    amount = this.amount,
    id = this.id
)
fun TransactionDTO.toEntity(): TransactionImported =
    TransactionImported(
        type = TransactionType[this.type]!!,
        seller = this.seller.toEntity(),
        valueTransaction = this.valueTransaction.convertCentsInBRL(),
        product = this.product.toEntity(),
        importation = Importation(id = this.idImportation),
        id = this.id,
        date = SimpleDateFormat(DATE_PATTERN).parse(this.date)
    )

fun List<TransactionImported>.toListModel(): List<TransactionDTO> {
    return this.map {
        TransactionDTO(
            id = it.id,
            type = it.type.code,
            product = it.product.toModel(),
            valueTransaction = it.valueTransaction.toInt(),
            seller = it.seller.toModel(),
            date = it.date.toString(),
            idImportation = it.importation.id
        )
    }
}

fun Product.toModel(): ProductDTO = ProductDTO(description = this.description, id = this.id)
fun Seller.toModel(): SellerDTO = SellerDTO(name = this.name, id = this.id)
fun SellerAccount.toModel(): SellerAccountDTO = SellerAccountDTO(
    id = this.id,
    seller = this.seller.toModel(),
    amount = this.amount
)

fun TransactionImported.toModel(): TransactionDTO = TransactionDTO(
    id = this.id,
    date = DATE_PATTERN.format(this.date),
    type = type.code,
    seller = this.seller.toModel(),
    product = this.product.toModel(),
    valueTransaction = this.valueTransaction.toInt(),
    idImportation = this.importation.id
)
fun Int.convertCentsInBRL() = (this.toDouble() / DIVISOR)
