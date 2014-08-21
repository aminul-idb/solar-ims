package com.startup.inventory

class LcSettings {

    String lcNo
    Date entryDate = new Date()
    String description
    Status status

    static constraints = {
        entryDate nullable: true, blank: true
        description nullable: true, blank: true
    }
}
