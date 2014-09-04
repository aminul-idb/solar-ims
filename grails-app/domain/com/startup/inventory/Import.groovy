package com.startup.inventory

class Import {

    ProductItem productItem
    String amount
    Status status
    LcSettings lcSettings
    Date entryDate = new Date()
    Date importDate
    String productPrice
    CategoryType categoryType

    static constraints = {
        entryDate nullable: true
        productPrice nullable: true, blank: true
        amount nullable: true, blank: true
    }
}
