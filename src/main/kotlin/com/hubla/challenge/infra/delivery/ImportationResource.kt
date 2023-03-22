package com.hubla.challenge.infra.delivery

import com.hubla.challenge.core.model.ImportationDTO
import com.hubla.challenge.core.service.ImportationService
import com.hubla.challenge.infra.persistence.entity.Importation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/importation")
class ImportationResource (private val service: ImportationService) {

    @PostMapping
    fun create(@RequestBody request: ImportationDTO) = ResponseEntity.ok(service.create(request))

    @GetMapping
    fun findAll(): ResponseEntity<List<Importation>> = ResponseEntity.ok(service.findAll())

    @GetMapping
    @RequestMapping("/{id}")
    fun getByID(@PathVariable id : UUID) : ResponseEntity<Importation> {
        val importation = service.findByID(id);
        return if(importation.isEmpty){
            ResponseEntity.notFound().build()
        }else{
            ResponseEntity.ok(importation.get())
        }
    }

}