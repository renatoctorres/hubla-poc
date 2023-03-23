package com.hubla.challenge.infra.delivery

import com.hubla.challenge.core.model.SellerDTO
import com.hubla.challenge.core.model.extensions.toEntity
import com.hubla.challenge.core.service.SellerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sellers")
class SellerResource(private val service: SellerService) {

    @Operation(summary = "Create Seller", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Seller added successfully!")
        ]
    )
    @PostMapping(produces = [APPLICATION_JSON_VALUE])
    fun create(@RequestBody request: SellerDTO) = ResponseEntity.ok(service.save(request.toEntity()))

    @Operation(summary = "Find All Sellers", description = "Returns 200")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Returns all Sellers included")
        ]
    )
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun findAll() = ResponseEntity.ok(service.findAll())

    @Operation(summary = "Find Seller by ID", description = "Returns 200 if found")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Seller found successfully!"),
            ApiResponse(responseCode = "404", description = "No Seller found!")
        ]
    )
    @GetMapping("/seller/{id}", produces = [APPLICATION_JSON_VALUE])
    fun findById(@PathVariable id: Long) : ResponseEntity<Any>{
        val seller = service.findById(id)
        return if(seller.isPresent){
            ResponseEntity.ok(seller)
        }else{
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Find Seller by Name", description = "Returns the Seller if found")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Seller found successfully!"),
            ApiResponse(responseCode = "404", description = "No Seller found!")
        ]
    )
    @GetMapping("/seller", produces = [APPLICATION_JSON_VALUE])
    fun findByName(@RequestParam name: String) : ResponseEntity<Any>{
        val seller = service.findByName(name)
        return if(seller.isPresent){
            ResponseEntity.ok(seller)
        }else{
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Update Seller", description = "Update Seller details")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Seller updated successfully!"),
        ]
    )
    @PutMapping(produces = [APPLICATION_JSON_VALUE])
    fun update(@RequestBody request: SellerDTO) = ResponseEntity.ok(service.save(request.toEntity()))

    @Operation(summary = "Delete Seller", description = "Delete Seller by ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Seller deleted successfully!"),
        ]
    )
    @DeleteMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun deleteById(@PathVariable id: Long) : ResponseEntity<Any> {
        service.deleteById(id)
        return ResponseEntity.noContent().build();
    }


}