package com.hubla.challenge.core.service

import com.hubla.challenge.core.model.ImportationDTO
import com.hubla.challenge.core.model.TransactionDTO
import com.hubla.challenge.infra.persistence.entity.*
import com.hubla.challenge.infra.persistence.repository.ImportationRepository
import com.hubla.challenge.infra.persistence.repository.ProductRepository
import com.hubla.challenge.infra.persistence.repository.SellerRepository
import com.hubla.challenge.infra.persistence.repository.TransactionImportedRepository
import org.apache.commons.io.FileUtils
import org.apache.commons.io.LineIterator
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

import com.hubla.challenge.core.model.ImportationTemplate.TYPE_FIELD
import com.hubla.challenge.core.model.ImportationTemplate.DATE_FIELD
import com.hubla.challenge.core.model.ImportationTemplate.PRODUCT_FIELD
import com.hubla.challenge.core.model.ImportationTemplate.VALUE_FIELD
import com.hubla.challenge.core.model.ImportationTemplate.SELLER_FIELD
import com.hubla.challenge.core.model.ProductDTO
import com.hubla.challenge.core.model.SellerDTO
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat

@Service
class ImportationService(private val repository: ImportationRepository,
                         private val transactionRepository: TransactionImportedRepository,
                         private val productRepository : ProductRepository,
                         private val sellerRepository: SellerRepository) {

    private val products = arrayListOf<Product>()
    private val sellers = arrayListOf<Seller>()


    fun save(request: ImportationDTO) : Importation = repository.save(request.toEntity())
    fun findAll() : List<Importation> = repository.findAll()
    fun findById(id: Long): Optional<Importation> = repository.findById(id)
    fun deleteById(id: Long) = repository.deleteById(id)

    fun saveAllTransactions(transactions : List<TransactionDTO>, importation : Importation) : List<TransactionImported>{
        return transactionRepository.saveAll(
            transactions.toListEntity(importation)
        )
    }

    fun saveTransaction(transaction: TransactionDTO, importation: Importation) : TransactionImported {
        return transactionRepository.save(
            transaction.toEntity(importation)
        )
    }


    private fun findOrSaveProduct(input : String) : Product {
        val productCached = products.any { it.description == input }
        return if(!productCached || productRepository.findAllByDescription(input).isEmpty()){
            saveProduct(input)
        }else{
            products.first { it.description == input }
        }
    }

    private fun saveProduct(input : String) : Product {
        val product = Product(
            id = 0,
            description = input
        )
        products.add(product)
        return productRepository.save(product)
    }

    private fun findOrSaveSeller(input : String) : Seller {
        val sellerCached = sellers.any { it.name == input }
        return if(!sellerCached || sellerRepository.findByName(input).isEmpty()){
            saveSeller(input)
        }else{
            sellers.first { it.name == input }
        }
    }

    private fun saveSeller(input : String) : Seller {
        val seller = Seller(
            id = 0,
            name = input
        )
        sellers.add(seller)
        return sellerRepository.save(seller)
    }



    private fun List<TransactionDTO>.toListEntity(request: Importation) : List<TransactionImported> =
        this.map {
            TransactionImported(
                id = it.id,
                date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(it.date),
                type = TransactionType[it.type],
                product = findOrSaveProduct(it.product.description),
                valueTransaction = it.valueTransaction,
                seller = findOrSaveSeller(it.seller.name),
                importation = request,
            )
        }

    private fun TransactionDTO.toEntity(importation: Importation) : TransactionImported =
        TransactionImported(
            id = id,
            date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date),
            type = TransactionType[type],
            product = findOrSaveProduct(product.description),
            valueTransaction = valueTransaction,
            seller = findOrSaveSeller(seller.name),
            importation = importation ,
    )

    fun upload(file : MultipartFile) : List<TransactionImported>  {
        val convFile = file.originalFilename?.let { File(it) }
        val lineIterator: LineIterator = FileUtils.lineIterator(convFile, "UTF-8")
        val importation = repository.save(Importation(id = 0))

        while(lineIterator.hasNext()) {
            val line = lineIterator.next()
            if (line != null) {
                this.readLine(line.toString(),importation)
            }
        }

        return transactionRepository.findAllByImportationID(importation.id)

    }

    private fun readLine(line: String, importation : Importation ) {
        val typeTransaction = line.substring(TYPE_FIELD.startPosition,TYPE_FIELD.endPosition)
        val dateTransaction = line.substring(DATE_FIELD.startPosition,DATE_FIELD.endPosition)
        val productTransaction = line.substring(PRODUCT_FIELD.startPosition,PRODUCT_FIELD.endPosition)
        val valueTransaction = line.substring(VALUE_FIELD.startPosition,VALUE_FIELD.endPosition)
        val sellerTransaction = line.substring(SELLER_FIELD.startPosition,SELLER_FIELD.endPosition)
        this.transactionRepository.save(
            TransactionImported(
                id = 0,
                product = findOrSaveProduct(productTransaction),
                date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateTransaction),
                valueTransaction = parseInt(valueTransaction),
                seller = findOrSaveSeller(sellerTransaction),
                type = TransactionType.get(Integer.valueOf(typeTransaction)),
                importation = importation
            )
        )
    }

}

private fun ImportationDTO.toEntity() = Importation(
    id = this.id,
    createdAt = this.createdAt
)

private fun Importation.toModel(transactionsImportedSaved : List<TransactionImported>) = ImportationDTO(
    id = this.id,
    createdAt = this.createdAt,
    transactions = transactionsImportedSaved.toListModel()

)

private fun List<TransactionImported>.toListModel() : List<TransactionDTO> =
    this.map {
        TransactionDTO(
            id = it.id,
            date = it.date.toString(),
            type = it.type!!.code,
            product = ProductDTO(description = it.product.description, id = it.id),
            valueTransaction = it.valueTransaction,
            seller = SellerDTO(name = it.seller.name, id = it.id),
            idImportation = it.importation.id
        )
    }





