package com.startup.inventory

class ProductDistribution {

    String fromBranch
    String toCustomer
    String address
    String description
    Date distributionDate
    Status status
    ProductItem productItem
    String amount
    String productPrice
    CategoryType categoryType


    static constraints = {
        address nullable: true, blank: true
        description nullable: true, blank: true
        productPrice nullable: true, blank: true
    }
}
