package com.startup.inventory

class CategoryType {

    String name
    Integer priority
    String description
    Status status

    static constraints = {
        priority nullable: false, unique: true
        name nullable: false, unique: true
        description nullable: true
    }
}
