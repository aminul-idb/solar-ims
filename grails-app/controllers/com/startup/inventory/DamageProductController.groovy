package com.startup.inventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException


@Secured(['ROLE_SUPER_ADMIN'])
class DamageProductController {

    DamageProductService damageProductService

    def index() {
        List<ProductItem> productItemList = ProductItem.list(sort: 'categoryType')
        LinkedHashMap resultMap = damageProductService.damageProductPaginateList(params)
        if (!resultMap || resultMap.totalCount == 0) {
            render(view: 'index', model: [dataReturn: null, totalCount: 0, productItemList: productItemList])
            return
        }
        int totalCount = resultMap.totalCount
        render(view: 'index', model: [dataReturn: resultMap.results, totalCount: totalCount, productItemList: productItemList])
    }

    @Transactional
    def save(DamageProduct damageProduct) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/damageProduct/index')
            return
        }

        if (damageProduct == null) {
            def result = [isError: true, message: "Have some problem in data!!"]
            render result as JSON
            return
        }

        if (damageProduct.hasErrors()) {
            def result = [isError: true, message: "Have some problem in data!!"]
            render result as JSON
            return
        }

        DamageProduct damageProductSaved = damageProduct.save(flush: true)
        if(!damageProductSaved){
            def result = [isError: true, message: "Damage product failed saved!!"]
            render result as JSON
            return
        }
        def result = [isError: false, message: "Damage Product Updated successfully!"]
        render result as JSON
    }

    def edit(Long id) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/damageProduct/index')
            return
        }
        DamageProduct damageProduct = DamageProduct.read(id)
        if (!damageProduct) {
            def result = [isError: true, message: "Damage Product name not found!!"]
            render result as JSON
            return
        }
        def result = [isError: true, obj: damageProduct, product: damageProduct.productItem ]
        render result as JSON
    }

    def delete(Long id) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/damageProduct/index')
            return
        }
        DamageProduct damageProduct = DamageProduct.get(id)
        if (damageProduct) {
            def result = [isError: true, message: "Damage Product name not found!!"]
            render result as JSON
            return
        }
        damageProduct.delete(flush: true)
        def result = [isError: true, message: "Damage Product deleted successfully!!"]
        render result as JSON
    }

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap = damageProductService.damageProductPaginateList(params)

        if (!resultMap || resultMap.totalCount == 0) {
            gridData = [iTotalRecords: 0, iTotalDisplayRecords: 0, aaData: null]
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
