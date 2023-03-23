package com.hubla.challenge.core.service

import com.hubla.challenge.infra.persistence.entity.SellerAccount
import com.hubla.challenge.infra.persistence.repository.SellerAccountRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SellerAccountService(private val repository: SellerAccountRepository) {
    fun findAll() : List<SellerAccount> = repository.findAll()
    fun findById(id : Long) : Optional<SellerAccount> = repository.findById(id)
    fun findBySellerId(id: Long) : Optional<SellerAccount> = repository.findBySellerId(id)
    fun save(sellerAccount : SellerAccount) : SellerAccount = repository.save(sellerAccount)
    fun deleteById(id: Long) = repository.deleteById(id)
}