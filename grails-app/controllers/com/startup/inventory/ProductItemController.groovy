package com.startup.inventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_SUPER_ADMIN'])
class ProductItemController {

    def productItemService

    def index() {
        LinkedHashMap resultMap = productItemService.productNamePaginateList(params)

        if (!resultMap || resultMap.totalCount == 0) {
            render(view: 'index', model: [dataReturn: null, totalCount: 0])
            return
        }
        int totalCount = resultMap.totalCount
        render(view: 'index', model: [dataReturn: resultMap.results, totalCount: totalCount])
    }

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap = productItemService.productNamePaginateList(params)

        if (!resultMap || resultMap.totalCount == 0) {
            gridData = [iTotalRecords: 0, iTotalDisplayRecords: 0, aaData: []]
            result = gridData as JSON
            render result
            return
        }
        int totalCount = resultMap.totalCount
        gridData = [iTotalRecords: totalCount, iTotalDisplayRecords: totalCount, aaData: resultMap.results]
        result = gridData as JSON
        render result

    }

    @Transactional
    def save(ProductItem productItem) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/productItem/index')
            return
        }

        if (productItem == null) {
            def result = [isError: true, message: "Have some problem in data!!"]
            render result as JSON
            return
        }

        if (productItem.hasErrors()) {
            def result = [isError: true, message: "Have some problem in data!!"]
            render result as JSON
            return
        }

        ProductItem productItemSaved = productItem.save(flush: true)
        if(!productItemSaved){
            def result = [isError: true, message: "Product failed saved!!"]
            render result as JSON
            return
        }

        def result = [isError: false, message: "Product Updated successfully!"]
        render result as JSON
    }

    def edit(Long id) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/productItem/index')
            return
        }
        ProductItem productItem = ProductItem.read(id)
        if (!productItem) {
            def result = [isError: true, message: "Product name not found!"]
            render result as JSON
            return
        }

        def result = [isError: false, obj: productItem]
        render result as JSON
    }

    def delete(Long id) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/productItem/index')
            return
        }
        ProductItem productItem = ProductItem.get(id)
        if (!productItem) {
            def result = [isError: true, message: "Product name not found!"]
            render result as JSON
            return
        }

        productItem.delete(flush: true)
        def result = [isError: false, message: "Product deleted successfully!"]
        render result as JSON
    }
}


class ProductItemCommand{

    Long id
    String name
    String description
    Status status

    static constraints = {
        id nullable: true
        name nullable: false
        description nullable: true
        status nullable: true
    }
}
