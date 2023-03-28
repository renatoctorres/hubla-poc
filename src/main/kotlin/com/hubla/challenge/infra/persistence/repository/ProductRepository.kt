package com.hubla.challenge.infra.persistence.repository

import com.hubla.challenge.infra.persistence.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    @Query("FROM Product WHERE description = :description")
    fun findAllByDescription(@Param("description") description: String): Optional<Product>
}
