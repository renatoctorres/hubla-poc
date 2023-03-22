package com.hubla.challenge.infra.persistence.repository

import com.hubla.challenge.infra.persistence.entity.Importation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ImportationRepository : JpaRepository<Importation, UUID>