package com.startup.inventory

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_SUPER_ADMIN'])
class HomeController {

    def index() { }
}
