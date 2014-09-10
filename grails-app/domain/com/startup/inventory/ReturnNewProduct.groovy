package com.startup.inventory

class ReturnNewProduct {

    ProductItem productItem
    String productAmount
    Date returnNewDate
    Date entryDate = new Date()
    String description

    Status status = Status.ACTIVE

    static constraints = {
        productItem nullable: false, blank: false
        description nullable: true, blank: true
        entryDate nullable: true, blank: true
        productAmount nullable: true, blank: true
    }
}
