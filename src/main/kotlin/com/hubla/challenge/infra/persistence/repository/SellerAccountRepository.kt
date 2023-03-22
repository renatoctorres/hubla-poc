package com.hubla.challenge.infra.persistence.repository

import com.hubla.challenge.infra.persistence.entity.SellerAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SellerAccountRepository : JpaRepository<SellerAccount, UUID>