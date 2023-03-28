package com.hubla.challenge.core.service

import com.hubla.challenge.infra.persistence.entity.TransactionImported
import com.hubla.challenge.infra.persistence.repository.TransactionImportedRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionService(
    private val repository: TransactionImportedRepository,
    private val accountService: SellerAccountService
) {
    fun findAll(): List<TransactionImported> = repository.findAll()
    fun findById(id: Long): Optional<TransactionImported> = repository.findById(id)
    fun findAllByImportID(importID: Long): List<TransactionImported> {
        println("ENTROU NO importation SERVICE 5")
        return repository.findAllByImportationID(importID)
    }
    fun findAllBySellerID(sellerID: Long): List<TransactionImported> = repository.findAllBySellerID(sellerID)
    fun findAllByProductID(productID: Long): List<TransactionImported> = repository.findAllByProductID(productID)
    fun deleteById(id: Long) = repository.deleteById(id)
    fun save(transaction: TransactionImported): TransactionImported {
        val saved = repository.saveAndFlush(transaction)
        accountService.updateAmount(
            transaction.seller.id,
            transaction.valueTransaction
        )
        return saved
    }
}
