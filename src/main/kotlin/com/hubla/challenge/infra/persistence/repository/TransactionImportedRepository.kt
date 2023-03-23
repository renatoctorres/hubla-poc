package com.hubla.challenge.infra.persistence.repository

import com.hubla.challenge.infra.persistence.entity.TransactionImported
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionImportedRepository : JpaRepository<TransactionImported, Long> {
    @Query("FROM TransactionImported t WHERE t.importation.id = :importationID")
    fun findAllByImportationID(@Param("importationID") importationID : Long) : List<TransactionImported>
    @Query("FROM TransactionImported t WHERE t.seller.id = :sellerID")
    fun findAllBySellerID(@Param("sellerID") sellerID : Long) : List<TransactionImported>
    @Query("FROM TransactionImported t WHERE t.product.id = :productID")
    fun findAllByProductID(@Param("productID") productID : Long) : List<TransactionImported>

}