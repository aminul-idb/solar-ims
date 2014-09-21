package com.startup.inventory

class YearlyReport {
    CategoryType categoryType
    ProductItem productItem
    String amount

    static constraints = {
        categoryType nullable: true, blank: true
        productItem nullable: true, blank: true
        amount nullable: true, blank: true
    }

}
