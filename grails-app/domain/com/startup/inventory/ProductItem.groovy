package com.startup.inventory

class ProductItem {

    String name
    String description
    Status status
    static belongsTo = [categoryType: CategoryType]

    static constraints = {
        name nullable: false, unique: true
        description nullable: true, blank: true
    }
}
