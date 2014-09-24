package com.startup.inventory

class LcSettings {

    String lcNo
    Date entryDate = new Date()
    Date lcDate
    String description
    Status status

    static constraints = {
        lcNo nullable: false, blank: false, unique: true
        entryDate nullable: true, blank: true
        description nullable: true, blank: true
    }
}
