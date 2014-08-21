package com.startup.inventory

class ReturnNewProduct {

    ProductItem productItem
    String productAmount
    Date returnNewDate
    Date entryDate = new Date()
    String description

    Status status = Status.ACTIVE

    static constraints = {
        description nullable: true, blank: true
        entryDate nullable: true, blank: true
    }
}
