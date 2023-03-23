package com.hubla.challenge.infra.delivery

import com.hubla.challenge.core.model.ImportationDTO
import com.hubla.challenge.core.service.ImportationService
import com.hubla.challenge.infra.persistence.entity.Importation
import com.hubla.challenge.infra.persistence.entity.TransactionImported
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/importations")
class ImportationResource (private val service: ImportationService) {

    @PostMapping
    fun create(@RequestBody request: ImportationDTO) = ResponseEntity.ok(service.save(request))

    @GetMapping
    @RequestMapping("/{id}")
    fun getByID(@PathVariable id : Long) : ResponseEntity<Importation> {
        val importation = service.findById(id);
        return if(importation.isEmpty){
            ResponseEntity.notFound().build()
        }else{
            ResponseEntity.ok(importation.get())
        }
    }
    @Operation(summary = "Upload Transactions Document", description = "Returns 202 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Successful Operation")
        ]
    )
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @RequestMapping("/upload")
    fun upload(@RequestPart file: MultipartFile) : ResponseEntity<List<TransactionImported>> =  ResponseEntity.ok(service.upload(file))

}