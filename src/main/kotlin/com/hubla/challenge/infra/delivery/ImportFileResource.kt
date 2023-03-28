package com.hubla.challenge.infra.delivery

import com.hubla.challenge.core.service.ImportationFileService
import com.hubla.challenge.infra.persistence.entity.TransactionImported
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/files")
class ImportFileResource(private val service: ImportationFileService) {
    @Operation(summary = "Upload Transactions Document", description = "Returns 204 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Successful Operation")
        ]
    )
    @PostMapping
    @RequestMapping("/upload", consumes = [MULTIPART_FORM_DATA_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun upload(@RequestPart file: MultipartFile): ResponseEntity<List<TransactionImported>> {
        return ResponseEntity.ok(service.upload(file))
    }
}
