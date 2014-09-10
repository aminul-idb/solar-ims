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
        description nullable: true, blank: true
        productPrice nullable: true, blank: true
        amount nullable: true, blank: true
        fromBranch nullable: false, blank: false
        toBranch nullable: false, blank: false
    }
}
