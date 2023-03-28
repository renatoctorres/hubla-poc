package com.hubla.challenge.infra.persistence.repository

import com.hubla.challenge.infra.persistence.entity.SellerAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
@Repository
interface SellerAccountRepository : JpaRepository<SellerAccount, Long> {
    @Query("FROM SellerAccount s WHERE s.seller.id = :sellerID")
    fun findBySellerId(sellerID: Long): Optional<SellerAccount>

    @Modifying
    @Query("UPDATE SellerAccount sa SET sa.amount = sa.amount + :increment WHERE sa.seller.id = :sellerID")
    fun updateAmount(sellerID: Long, increment: Double)
}
