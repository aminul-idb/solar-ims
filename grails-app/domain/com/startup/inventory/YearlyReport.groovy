package com.startup.inventory

class YearlyReport {
    CategoryType categoryType
    ProductItem productItem
    Integer janToAmount = 0
    Integer febToAmount = 0
    Integer marToAmount = 0
    Integer aprToAmount = 0
    Integer mayToAmount = 0
    Integer junToAmount = 0
    Integer julToAmount = 0
    Integer augToAmount = 0
    Integer sepToAmount = 0
    Integer octToAmount = 0
    Integer novToAmount = 0
    Integer decToAmount = 0
    String year

    static constraints = {
        categoryType nullable: true, blank: true
        productItem nullable: true, blank: true
        janToAmount nullable: true, blank: true
        febToAmount nullable: true, blank: true
        marToAmount nullable: true, blank: true
        aprToAmount nullable: true, blank: true
        mayToAmount nullable: true, blank: true
        junToAmount nullable: true, blank: true
        julToAmount nullable: true, blank: true
        augToAmount nullable: true, blank: true
        sepToAmount nullable: true, blank: true
        octToAmount nullable: true, blank: true
        novToAmount nullable: true, blank: true
        decToAmount nullable: true, blank: true
        year nullable: true, blank: true
    }

}
