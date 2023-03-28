package com.hubla.challenge.core.service

import com.hubla.challenge.infra.persistence.entity.Seller
import com.hubla.challenge.infra.persistence.entity.SellerAccount
import com.hubla.challenge.infra.persistence.repository.SellerRepository
import org.springframework.stereotype.Service
import java.util.*
@Service
class SellerService(
    private val repository: SellerRepository,
    private val sellerAccountService: SellerAccountService
) {
    fun findAll(): List<Seller> = repository.findAll()
    fun findById(id: Long): Optional<Seller> = repository.findById(id)
    fun findByName(name: String): Optional<Seller> = repository.findByName(name)
    fun create(seller: Seller): Seller {
        val saved = this.repository.saveAndFlush(seller)
        sellerAccountService.save(
            SellerAccount(
                seller = saved
            )
        )
        return saved
    }
    fun save(seller: Seller): Seller = repository.saveAndFlush(seller)
    fun deleteById(id: Long) = repository.deleteById(id)
}
