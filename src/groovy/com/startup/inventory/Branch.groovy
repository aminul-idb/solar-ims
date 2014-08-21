package com.startup.inventory

/**
 * Created by DELL on 8/6/2014.
 */
public enum Branch {

    DHAKA("Dhaka"),
    CHITTAGONG("Chittagong")


    final String value
    Branch(String value) {
        this.value = value
    }

    String toString() { value }
    String getKey() { name() }
}
