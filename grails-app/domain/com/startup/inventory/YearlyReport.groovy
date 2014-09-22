package com.startup.inventory

class YearlyReport {
    CategoryType categoryType
    ProductItem productItem
    String janToAmount
    String febToAmount
    String marToAmount
    String aprToAmount
    String mayToAmount
    String junToAmount
    String julToAmount
    String augToAmount
    String sepToAmount
    String octToAmount
    String novToAmount
    String decToAmount
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
