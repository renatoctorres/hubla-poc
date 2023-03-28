package com.hubla.challenge.infra.delivery

import com.hubla.challenge.core.model.SellerAccountDTO
import com.hubla.challenge.core.model.SellerAccountExtractDTO
import com.hubla.challenge.core.model.extensions.toEntity
import com.hubla.challenge.core.model.extensions.toListModel
import com.hubla.challenge.core.model.extensions.toModel
import com.hubla.challenge.core.service.SellerAccountService
import com.hubla.challenge.core.service.TransactionService
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
@RequestMapping("/accounts")
class SellerAccountResource(
    private val service: SellerAccountService,
    private val transactionService: TransactionService
) {

    @Operation(summary = "Create Seller Account", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Seller´s Account added successfully!")
        ]
    )
    @PostMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun create(@RequestBody request: SellerAccountDTO) = ResponseEntity.ok(service.save(request.toEntity()))

    @Operation(summary = "Find All Seller's Accounts", description = "Returns 200")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Returns all Seller´s Accounts included")
        ]
    )
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun findAll() = ResponseEntity.ok(service.findAll())

    @Operation(summary = "Find Seller´s Account by Seller ID", description = "Returns 200 if found")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Seller´s Account found successfully!"),
            ApiResponse(responseCode = "404", description = "Seller´s Account found!")
        ]
    )
    @GetMapping("/seller/{sellerID}", produces = [APPLICATION_JSON_VALUE])
    fun findBySellerId(@PathVariable sellerID: Long): ResponseEntity<Any> {
        val account = service.findBySellerId(sellerID)
        return if (account.isPresent) {
            ResponseEntity.ok(account)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Get Extract Account by Seller ID", description = "Returns 200 if found")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Seller´s Account found successfully!"),
            ApiResponse(responseCode = "404", description = "Seller´s Account found!")
        ]
    )
    @GetMapping("/seller/{sellerID}/extract", produces = [APPLICATION_JSON_VALUE])
    fun getExtractSellerAccount(@PathVariable sellerID: Long): ResponseEntity<Any> {
        val account = service.findBySellerId(sellerID)
        val transactions = transactionService.findAllBySellerID(sellerID)
        return if (account.isPresent) {
            ResponseEntity.ok(
                SellerAccountExtractDTO(
                    account = account.get().toModel(),
                    transactions = transactions.toListModel()
                )
            )
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Find Seller´s Account by ID", description = "Returns 200 if found")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Seller found successfully!"),
            ApiResponse(responseCode = "404", description = "No Seller found!")
        ]
    )
    @GetMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun findById(@PathVariable id: Long): ResponseEntity<Any> {
        val seller = service.findById(id)
        return if (seller.isPresent) {
            ResponseEntity.ok(seller)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Update Seller´s Account", description = "Update Seller´s Account details")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Seller´s Account updated successfully!"),
        ]
    )
    @PutMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun update(@RequestBody request: SellerAccountDTO) = ResponseEntity.ok(service.save(request.toEntity()))

    @Operation(summary = "Delete Seller´s Account", description = "Delete Seller´s Account by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Seller deleted successfully!"),
        ]
    )
    @DeleteMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun deleteById(@PathVariable id: Long): ResponseEntity<Any> {
        service.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
