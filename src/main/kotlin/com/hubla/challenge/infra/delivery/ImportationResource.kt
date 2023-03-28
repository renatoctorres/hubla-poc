package com.hubla.challenge.infra.delivery

import com.hubla.challenge.core.service.ImportationService
import com.hubla.challenge.infra.persistence.entity.Importation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/importations", produces = [APPLICATION_JSON_VALUE])
class ImportationResource(private val service: ImportationService) {
    @Operation(summary = "Find AllTransactions", description = "Returns 200")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation")
        ]
    )
    @GetMapping
    @RequestMapping(produces = [APPLICATION_JSON_VALUE])
    fun findAll(): ResponseEntity<List<Importation>> =
        ResponseEntity.ok(service.findAll())

    @Operation(summary = "Find All Transactions By Id", description = "Returns 200")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "404", description = "No Transactions with this Importation ID")
        ]
    )
    @GetMapping
    @RequestMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun getByID(@PathVariable id: Long): ResponseEntity<Importation> {
        val importation = service.findById(id)
        return if (importation.isEmpty) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(importation.get())
        }
    }

    @Operation(summary = "Find All Transactions By Id", description = "Returns 200")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation")
        ]
    )
    @DeleteMapping(produces = [APPLICATION_JSON_VALUE])
    fun deleteByID(@RequestParam id: Long): ResponseEntity<Any> {
        this.service.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
