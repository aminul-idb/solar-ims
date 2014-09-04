package com.startup.inventory

class DamageProduct {

    ProductItem productItem
    String productAmount
    Date damageDate
    Date entryDate = new Date()
    String description

    Status status = Status.ACTIVE

    static constraints = {
        description nullable: true, blank: true
        entryDate nullable: true, blank: true
        status nullable: true, blank: true
        productAmount nullable: true, blank: true
    }

}
