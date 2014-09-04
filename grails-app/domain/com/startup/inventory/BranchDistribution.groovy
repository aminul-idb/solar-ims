package com.startup.inventory

class BranchDistribution {

    String fromBranch
    String toBranch
    ProductItem productItem
    Date distributionDate
    String description
    String amount
    String branchUniqueId
    String productPrice
    Status status

    static constraints = {
        status nullable: true, blank: true
        branchUniqueId nullable: false, blank: false
        description nullable: false, blank: false
        productPrice nullable: true, blank: true
        amount nullable: true, blank: true
    }
}
