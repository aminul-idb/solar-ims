package com.startup.inventory

class YearlyReport {
    CategoryType categoryType
    ProductItem productItem
    String amount
    String month

    static constraints = {
        categoryType nullable: true, blank: true
        productItem nullable: true, blank: true
        amount nullable: true, blank: true
        month nullable: true, blank: true
    }

}
