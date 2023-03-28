package com.hubla.challenge.infra.delivery

import com.hubla.challenge.core.model.TransactionDTO
import com.hubla.challenge.core.model.extensions.toEntity
import com.hubla.challenge.core.service.TransactionService
import com.hubla.challenge.infra.persistence.entity.TransactionImported
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions", produces = [APPLICATION_JSON_VALUE])
class TransactionResource(private val service: TransactionService) {

    @Operation(summary = "Create Transaction", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Transaction added successfully!")
        ]
    )
    @PostMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun create(@RequestBody request: TransactionDTO) = ResponseEntity.ok(service.save(request.toEntity()))

    @Operation(summary = "Find All Transactions", description = "Returns 200")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Returns all Transactions included")
        ]
    )
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun findAll() = ResponseEntity.ok(service.findAll())

    @Operation(summary = "Find Transaction by ID", description = "Returns 200 if found")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Transaction found successfully!"),
            ApiResponse(responseCode = "404", description = "No Transaction found!")
        ]
    )
    @GetMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun findById(@PathVariable id: Long): ResponseEntity<Any> {
        val transaction = service.findById(id)
        return if (transaction.isPresent) {
            ResponseEntity.ok(transaction)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Find All Transactions by Importation ID", description = "Returns 200")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Return all Transactions by Importation ID, if found"),
        ]
    )
    @GetMapping("/importation/{importID}", produces = [APPLICATION_JSON_VALUE])
    fun findAllByImportID(@PathVariable importID: Long): ResponseEntity<List<TransactionImported>> =
        ResponseEntity.ok(service.findAllByImportID(importID))

    @Operation(summary = "Find All Transactions by Seller ID", description = "Returns 200")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Return all Transactions by Seller ID, if found"),
        ]
    )
    @GetMapping("/seller/{sellerID}", produces = [APPLICATION_JSON_VALUE])
    fun findAllBySellerID(@PathVariable sellerID: Long): ResponseEntity<List<TransactionImported>> =
        ResponseEntity.ok(service.findAllBySellerID(sellerID))

    @Operation(summary = "Find All Transactions by Product ID", description = "Returns 200")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Return all Transactions by Product ID, if found"),
        ]
    )
    @GetMapping("/product/{productID}", produces = [APPLICATION_JSON_VALUE])
    fun findAllByProductID(@PathVariable productID: Long): ResponseEntity<List<TransactionImported>> =
        ResponseEntity.ok(service.findAllByProductID(productID))

    @Operation(summary = "Update Transaction", description = "Update Transaction details")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Transaction updated successfully!"),
        ]
    )
    @PutMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun update(@RequestBody request: TransactionDTO) = ResponseEntity.ok(service.save(request.toEntity()))

    @Operation(summary = "Delete Transaction", description = "Delete Transaction by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Transaction deleted successfully!"),
        ]
    )
    @DeleteMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Any> {
        service.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
