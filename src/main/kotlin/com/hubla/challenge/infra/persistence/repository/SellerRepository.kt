package com.hubla.challenge.infra.persistence.repository

import com.hubla.challenge.infra.persistence.entity.Seller
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SellerRepository : JpaRepository<Seller, Long>{
    @Query("FROM Seller WHERE name = :name")
    fun findByName(@Param("name") name : String) : Optional<Seller>
}

