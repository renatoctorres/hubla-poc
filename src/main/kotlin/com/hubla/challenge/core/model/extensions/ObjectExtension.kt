package com.hubla.challenge.core.model.extensions

import com.hubla.challenge.core.model.*
import com.hubla.challenge.infra.persistence.entity.*
import java.text.SimpleDateFormat

fun ImportationDTO.toEntity() : Importation = Importation(id = this.id)
fun ProductDTO.toEntity() : Product = Product(description = this.description,id = this.id)
fun SellerDTO.toEntity() : Seller = Seller(name = this.name,id = this.id)
fun SellerAccountDTO.toEntity() : SellerAccount = SellerAccount(seller = this.seller.toEntity(), amount = this.amount)
fun TransactionDTO.toEntity() : TransactionImported =
    TransactionImported(
        type = TransactionType.get(this.type),
        seller = this.seller.toEntity(),
        valueTransaction = this.valueTransaction,
        product = this.product.toEntity(),
        importation = Importation(id= this.idImportation),
        id = this.id,
        date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this.date)
    )