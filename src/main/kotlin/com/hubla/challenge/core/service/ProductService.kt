package com.hubla.challenge.core.service

import com.hubla.challenge.infra.persistence.entity.Product
import com.hubla.challenge.infra.persistence.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(private val repository: ProductRepository) {

    fun findAll() : List<Product> = repository.findAll()
    fun findById(id : Long) : Optional<Product> = repository.findById(id)
    fun findByDescription(description: String) : Optional<Product> = repository.findAllByDescription(description)
    fun save(product : Product) : Product = repository.save(product)
    fun deleteById(id: Long) = repository.deleteById(id)

}