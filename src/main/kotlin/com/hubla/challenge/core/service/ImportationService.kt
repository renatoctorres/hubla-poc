package com.hubla.challenge.core.service

import com.hubla.challenge.core.model.ImportationDTO
import com.hubla.challenge.core.model.TransactionDTO
import com.hubla.challenge.infra.persistence.entity.*
import com.hubla.challenge.infra.persistence.repository.ImportationRepository
import com.hubla.challenge.infra.persistence.repository.ProductRepository
import com.hubla.challenge.infra.persistence.repository.SellerRepository
import com.hubla.challenge.infra.persistence.repository.TransactionImportedRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class ImportationService(private val repository: ImportationRepository,
                         private val transactionRepository: TransactionImportedRepository,
                         private val productRepository : ProductRepository,
                         private val sellerRepository: SellerRepository) {

    private val products = arrayListOf<Product>()
    private val sellers = arrayListOf<Seller>()


    fun saveAllTransactions(transactions : List<TransactionDTO>, importation : Importation) : List<TransactionImported>{
        return transactionRepository.saveAll(
            transactions.toListEntity(importation)
        )
    }

    fun create(request: ImportationDTO) : Importation = repository.save(request.toEntity())

    fun findAll() : List<Importation> = repository.findAll()

    fun findByID(id: UUID): Optional<Importation> = repository.findById(id)

    private fun findOrSaveProduct(input : String) : Product {
        val productCached = products.any { it.description == input }
        return if(!productCached || productRepository.findByDescription(input).isEmpty){
            saveProduct(input)
        }else{
            products.first { it.description == input }
        }
    }

    private fun saveProduct(input : String) : Product {
        val product = Product(
            description = input
        )
        products.add(product)
        return productRepository.save(product)
    }

    private fun findOrSaveSeller(input : String) : Seller {
        val sellerCached = sellers.any { it.name == input }
        return if(!sellerCached || sellerRepository.findByName(input).isEmpty){
            saveSeller(input)
        }else{
            sellers.first { it.name == input }
        }
    }

    private fun saveSeller(input : String) : Seller {
        val seller = Seller(
            name = input
        )
        sellers.add(seller)
        return sellerRepository.save(seller)
    }

    private fun List<TransactionDTO>.toListEntity(request: Importation) : List<TransactionImported> =
        this.map {
            TransactionImported(
                id = it.id,
                createdAt = it.createdAt,
                type = TransactionType[it.type],
                product = findOrSaveProduct(it.product),
                valueTransaction = it.valueTransaction,
                seller = findOrSaveSeller(it.seller),
                importation = request,
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
            createdAt = it.createdAt,
            type = it.type!!.code,
            product = it.product.description,
            valueTransaction = it.valueTransaction,
            seller = it.seller.name
        )
    }



