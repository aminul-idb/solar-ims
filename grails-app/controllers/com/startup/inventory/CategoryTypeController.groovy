package com.startup.inventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
//import org.springframework.dao.DataIntegrityViolationException
//import org.springsource.loaded.C

@Secured(['ROLE_SUPER_ADMIN'])
class CategoryTypeController {
    def categoryTypeService

    def index() {
        LinkedHashMap resultMap = categoryTypeService.categoryTypePaginateList(params)

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
        LinkedHashMap resultMap = categoryTypeService.categoryTypePaginateList(params)

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

    @Transactional
    def save(CategoryType categoryType) {

        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/categoryType/index')
            return
        }

        if (categoryType == null) {
            def result = [isError: true, message: "Have some problem in data!!"]
            render result as JSON
            return
        }

        if (categoryType.hasErrors()) {
            def result = [isError: true, message: "Have some problem in data!!"]
            render result as JSON
            return
        }

        CategoryType categoryTypeSaved = categoryType.save(flush: true)
        if (!categoryTypeSaved) {
            def result = [isError: true, message: "Category update failed!!"]
            render result as JSON
            return
        }

        def result = [isError: false, message: "Category save successsfully!"]
        render result as JSON
    }

    def edit(Long id) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/categoryType/index')
            return
        }

        CategoryType categoryType = CategoryType.read(id)
        if (!categoryType) {
            def result = [isError: true, message: "Category name not found!!"]
            render result as JSON
            return
        }

        def result = [isError: false, obj: categoryType]
        render result as JSON
        return
    }

    def delete(Long id) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/categoryType/index')
            return
        }

        CategoryType categoryType = CategoryType.get(id)
        if (!categoryType) {
            def result = [isError: true, message: "Category name not found!!"]
            render result as JSON
            return
        }

        categoryType.delete(flush: true)
        def result = [isError: true, message: "Category deleted successfully!!"]
        render result as JSON
        return
    }
}



class CategoryTypeCommand{

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
