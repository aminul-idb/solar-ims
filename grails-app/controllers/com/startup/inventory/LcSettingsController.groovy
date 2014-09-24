package com.startup.inventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException


@Secured(['ROLE_SUPER_ADMIN'])
class LcSettingsController {

    def lcSettingsService

    def index() {
        LinkedHashMap resultMap = lcSettingsService.lcSettingsPaginateList(params)

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
        LinkedHashMap resultMap = lcSettingsService.lcSettingsPaginateList(params)

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
    def save(LcSettings lcSettings) {
        if (request.method != 'POST') {
            flash.message = "Method Problem!"
            render(view: '/lcSettings/index')
            return
        }

        if (lcSettings == null) {
            def result = [isError: true, message: "Have some problem in data!!"]
            render result as JSON
            return
        }

        if (lcSettings.hasErrors()) {
            def result = [isError: true, message: "LC No. must be unique!!"]
            render result as JSON
            return
        }

        LcSettings lcSettingsSaved =  lcSettings.save(flush: true)
        if (params.edit == "edit"){
            def anImport = Import.findAllByLcSettings(lcSettingsSaved)
            for (def i=0; i<anImport.size(); i++){
                def importUpdate = Import.get(anImport.id[i])
                importUpdate.importDate = lcSettings.lcDate
                importUpdate.save(flush: true)
            }
        }



        if(!lcSettingsSaved){
            def result = [isError: true, message: "LC failed save!!"]
            render result as JSON
            return
        }
        def result = [isError: false, message: "LC Added successfully!"]
        render result as JSON
    }

    def edit(Long id) {
        if (request.method != 'POST') {
            flash.message = "Method Problem!"
            render(view: '/lcSettings/index')
            return
        }

        LcSettings anImport = LcSettings.read(id)
        if (!anImport) {
            def result = [isError: true, message: "Import name not found!!"]
            render result as JSON
            return
        }

        def result = [isError: false, obj: anImport]
        render result as JSON
    }

    def delete(Long id) {
        if (request.method != 'POST') {
            flash.message = "Method Problem!"
            render(view: '/lcSettings/index')
            return
        }

        LcSettings anImport = LcSettings.get(id)
        if (!anImport) {
            def result = [isError: true, message: "Import name not found!!"]
            render result as JSON
            return
        }

        anImport.delete(flush: true)
        def result = [isError: false, message: "Import deleted successfully!"]
        render result as JSON
   }

}
