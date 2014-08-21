package com.startup.inventory

/**
 * Created by Hasnat on 7/17/2014.
 */
public enum Status {

    ACTIVE("Active"),
    INACTIVE("In Active")


    final String value
    Status(String value) {
        this.value = value
    }

    String toString() { value }
    String getKey() { name() }

}