package com.hubla.challenge.core.service

import com.hubla.challenge.infra.persistence.repository.TransactionImportedRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionService (private val repository : TransactionImportedRepository) {

    fun findAll() = repository.findAll()
    fun findAllByImportID(importID : UUID) = repository.findAllByImportationID(importID)
    fun findAllBySellerID(sellerID : UUID) = repository.findAllBySellerID(sellerID)


}