package com.hubla.challenge.infra.delivery

import com.hubla.challenge.core.service.TransactionService
import com.hubla.challenge.infra.persistence.entity.TransactionImported
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/transaction")
class TransactionResource(private val service : TransactionService) {

    @GetMapping
    @RequestMapping("/importation/{importID}")
    fun findAllByImportID(@PathVariable importID : UUID) : List<TransactionImported> = service.findAllByImportID(importID)

    @GetMapping
    @RequestMapping("/seller/{sellerID}")
    fun findAllBySellerID(@PathVariable sellerID : UUID) : List<TransactionImported> = service.findAllBySellerID(sellerID)

    @GetMapping
    fun findAll(@PathVariable sellerID : UUID) : List<TransactionImported> = service.findAll()

}