package com.hubla.challenge.core.service

import com.hubla.challenge.core.model.ImportationTemplate.DATE_FIELD
import com.hubla.challenge.core.model.ImportationTemplate.PRODUCT_FIELD
import com.hubla.challenge.core.model.ImportationTemplate.SELLER_FIELD
import com.hubla.challenge.core.model.ImportationTemplate.TYPE_FIELD
import com.hubla.challenge.core.model.ImportationTemplate.VALUE_FIELD
import com.hubla.challenge.infra.persistence.entity.Importation
import com.hubla.challenge.infra.persistence.entity.OperationNature
import com.hubla.challenge.infra.persistence.entity.Product
import com.hubla.challenge.infra.persistence.entity.Seller
import com.hubla.challenge.infra.persistence.entity.TransactionImported
import com.hubla.challenge.infra.persistence.entity.TransactionType
import com.hubla.challenge.infra.persistence.repository.ImportationRepository
import org.apache.commons.io.FileUtils
import org.apache.commons.io.LineIterator
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat

@Service
class ImportationFileService(
    private val repository: ImportationRepository,
    private val transactionService: TransactionService,
    private val sellerService: SellerService,
    private val productService: ProductService
) {

    private val products = arrayListOf<Product>()
    private val sellers = arrayListOf<Seller>()

    fun upload(multipartFile: MultipartFile): List<TransactionImported> {
        val lineIterator: LineIterator = FileUtils.lineIterator(multipartFile.convertToFile(), "UTF-8")
        val importation = repository.saveAndFlush(Importation(id = 0))
        while (lineIterator.hasNext()) {
            val line = lineIterator.next()
            if (line != null) {
                this.readLine(line.toString(), importation)
            }
        }
        return transactionService.findAllByImportID(importation.id)
    }

    private fun readLine(line: String, importation: Importation) {
        val typeInput = line.substring(TYPE_FIELD.startPosition, TYPE_FIELD.endPosition)
        val typeTransaction = TransactionType[Integer.valueOf(typeInput)]
        val dateInput = line.substring(DATE_FIELD.startPosition, DATE_FIELD.endPosition)
        val productInput = line.substring(PRODUCT_FIELD.startPosition, PRODUCT_FIELD.endPosition).trim()
        val valueInput = line.substring(VALUE_FIELD.startPosition, VALUE_FIELD.endPosition)
        val sellerInput = line.substring(SELLER_FIELD.startPosition)
        val saved = this.transactionService.save(
            TransactionImported(
                id = 0,
                product = findOrSaveProduct(productInput),
                date = SimpleDateFormat(DATE_PATTERN).parse(dateInput),
                valueTransaction = valueInput
                    .convertCentsToBRL()
                    .applySignal(typeTransaction!!.nature),
                seller = findOrSaveSeller(sellerInput),
                type = typeTransaction,
                importation = importation
            )
        )
        println("Transaction: $saved")
    }

    private fun findOrSaveSeller(input: String): Seller {
        val sellerCached = sellers.any { it.name == input }
        return if (!sellerCached || sellerService.findByName(input).isEmpty) {
            val saved = createSeller(input)
            sellers.add(saved)
            saved
        } else {
            sellers.first { it.name == input }
        }
    }

    private fun findOrSaveProduct(input: String): Product {
        val productCached = products.any { it.description == input }
        return if (!productCached || productService.findByDescription(input).isEmpty) {
            val saved = saveProduct(input)
            products.add(saved)
            saved
        } else {
            products.first { it.description == input }
        }
    }

    private fun saveProduct(input: String): Product {
        return productService.save(Product(id = 0, description = input))
    }

    private fun createSeller(input: String): Seller {
        val seller = Seller(
            id = 0,
            name = input
        )
        sellers.add(seller)
        return sellerService.create(seller)
    }

    fun Double.applySignal(nature: OperationNature): Double {
        println("APPLY SIGNAL $nature")
        return when (nature) {
            OperationNature.DEBIT -> this * -1
            else -> { this }
        }
    }

    fun MultipartFile.convertToFile(): File {
        val convFile = File(this.originalFilename.toString())
        convFile.createNewFile()
        val fos = FileOutputStream(convFile)
        fos.write(this.bytes)
        fos.close()
        return convFile
    }
    fun String.convertCentsToBRL(): Double {
        val result = this.toInt() / DIVISOR.toDouble()
        print("Resultado da operação de converter em Reais, centavos: $this , reais: $result")
        return result
    }

    companion object {
        const val DIVISOR = 100
        const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    }
}
