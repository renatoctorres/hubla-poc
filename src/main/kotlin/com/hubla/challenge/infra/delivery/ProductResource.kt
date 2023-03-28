package com.hubla.challenge.infra.delivery

import com.hubla.challenge.core.model.ProductDTO
import com.hubla.challenge.core.model.extensions.toEntity
import com.hubla.challenge.core.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products", produces = [APPLICATION_JSON_VALUE])
class ProductResource(private val service: ProductService) {

    @Operation(summary = "Create Product", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Product added successfully!")
        ]
    )
    @PostMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun create(@RequestBody request: ProductDTO) = ResponseEntity.ok(service.save(request.toEntity()))

    @Operation(summary = "Find All Products", description = "Returns 200")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Returns all Products included")
        ]
    )
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun findAll() = ResponseEntity.ok(service.findAll())

    @Operation(summary = "Find Product by ID", description = "Returns 200 if found")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Product found successfully!"),
            ApiResponse(responseCode = "404", description = "No Product found!")
        ]
    )
    @GetMapping("/product/{id}", produces = [APPLICATION_JSON_VALUE])
    fun findById(@PathVariable id: Long): ResponseEntity<Any> {
        val product = service.findById(id)
        return if (product.isPresent) {
            ResponseEntity.ok(product)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Find Product by Name", description = "Returns the Product if found")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Product found successfully!"),
            ApiResponse(responseCode = "404", description = "No Product found!")
        ]
    )
    @GetMapping("/product", produces = [APPLICATION_JSON_VALUE])
    fun findByDescription(@RequestParam description: String): ResponseEntity<Any> {
        val product = service.findByDescription(description)
        return if (product.isPresent) {
            ResponseEntity.ok(product)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
