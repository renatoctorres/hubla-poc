package com.hubla.challenge.infra.persistence.repository

import com.hubla.challenge.infra.persistence.entity.Importation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
@Repository
interface ImportationRepository : JpaRepository<Importation, Long>
