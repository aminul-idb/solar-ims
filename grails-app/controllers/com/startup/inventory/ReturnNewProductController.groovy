package com.startup.inventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_SUPER_ADMIN'])
class ReturnNewProductController {

    ReturnNewProductService returnNewProductService

    def index() {
        List<ProductItem> productItemList = ProductItem.list(sort: 'categoryType')
        LinkedHashMap resultMap = returnNewProductService.returnNewProductPaginateList(params)
        if (!resultMap || resultMap.totalCount == 0) {
            render(view: 'index', model: [dataReturn: null, totalCount: 0, productItemList: productItemList])
            return
        }
        int totalCount = resultMap.totalCount
        render(view: 'index', model: [dataReturn: resultMap.results, totalCount: totalCount, productItemList: productItemList])
    }

    @Transactional
    def save(ReturnNewProduct returnNewProduct) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/returnNewProduct/index')
            return
        }
        if (returnNewProduct == null) {
            def result = [isError: true, message: "Have some problem in data!!"]
            render result as JSON
            return
        }
        if (returnNewProduct.hasErrors()) {
            def result = [isError: true, message: "Have some problem in data!!"]
            render result as JSON
            return
        }
        ReturnNewProduct returnNewProductSaved = returnNewProduct.save(flush:true)
        if(!returnNewProductSaved){
            def result = [isError: true, message: "Return New Product Updated Failed!!"]
            render result as JSON
            return
        }
        def result = [isError: true, message: "Return New Product Updated successfully!!"]
        render result as JSON
    }

    def edit(Long id) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/returnNewProduct/index')
            return
        }
        ReturnNewProduct returnNewProduct = ReturnNewProduct.read(id)
        if (!returnNewProduct) {
            def result = [isError: true, message: "Return New Product name not found!"]
            render result as JSON
            return
        }
        def result = [isError: false, obj: returnNewProduct, product: returnNewProduct.productItem]
        render result as JSON
    }

    def delete(Long id) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/returnNewProduct/index')
            return
        }
        ReturnNewProduct returnNewProduct = ReturnNewProduct.get(id)
        if (!returnNewProduct) {
            def result = [isError: true, message: "Return New Product name not found!"]
            render result as JSON
            return
        }
        returnNewProduct.delete(flush: true)
        def result = [isError: false, message: "Return New Product deleted successfully"]
        render result as JSON
    }

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap = returnNewProductService.returnNewProductPaginateList(params)

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
}
