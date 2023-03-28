package com.hubla.challenge.core.model

enum class ImportationTemplate(
    val field: String,
    val startPosition: Int,
    val endPosition: Int,
    val size: Int
) {

    TYPE_FIELD(field = "Type", startPosition = 0, endPosition = 1, size = 1),
    DATE_FIELD(field = "Date", startPosition = 1, endPosition = 26, size = 25),
    PRODUCT_FIELD(field = "Product", startPosition = 26, endPosition = 56, size = 20),
    VALUE_FIELD(field = "Value", startPosition = 56, endPosition = 66, size = 10),
    SELLER_FIELD(field = "Seller", startPosition = 66, endPosition = 86, size = 20);

    companion object {
        private val map = ImportationTemplate.values().associateBy { it.field }
        operator fun get(field: String) = map[field]
    }
}
