package com.startup.inventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.io.FileUtils
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef

//import org.codehaus.groovy.grails.commons.ApplicationHolder
//import org.codehaus.groovy.grails.commons.ConfigurationHolder

@Secured(['ROLE_SUPER_ADMIN'])
class ImportController {

    def importService
    def jasperService
    def grailsApplication

    def index() {
        List<ProductItem> productItemList = ProductItem.list(sort: 'categoryType')

        LinkedHashMap resultMap = importService.importPaginateList(params)

        if (!resultMap || resultMap.totalCount == 0) {
            render(view: 'index', model: [dataReturn: null, totalCount: 0, productItemList: productItemList])
            return
        }
        int totalCount = resultMap.totalCount
        render(view: 'index', model: [dataReturn: resultMap.results, totalCount: totalCount, productItemList: productItemList])
    }

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap = importService.importPaginateList(params)

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

    //@Transactional
    def save() {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/import/index')
            return
        }

        if(params.edit == "edit"){
            Import anImport = Import.get(params.importProductId as Long)
            if (!anImport) {
                def result = [isError: true, message: "Have some problem in data!!"]
                render result as JSON
                return
            }
            anImport.amount = params.amount
            anImport.productPrice = params.productPrice
            anImport.save(flush: true)
            def result = [isError: true, message: "Import Updated successfully!!"]
            render result as JSON
            return
        }

        // save
        def productItemList = params.productItemId
        def amountList = params.amount
        print(">>productPrice >> "+ params.productPrice)
        def productPriceList = params.productPrice
        def productCheckList = params.productCheck

        for(int i=0; i<productItemList.length; i++){
            Import anImport = new Import()
            if ( (amountList[i] != '') && (productItemList[i] in productCheckList == true)  ){
                anImport.importDate = Date.parse('dd/MM/yyyy', params.importDate)
                anImport.amount = amountList[i] as String
                anImport.productPrice = productPriceList[i] as String
                anImport.status = params.status
                anImport.lcSettings = LcSettings.get(params.lcSettings as Long)
                anImport.productItem = ProductItem.get(productItemList[i] as Long)
                anImport.categoryType = anImport.productItem.categoryType
                Import importSaved = anImport.save(flush:true)
                if(!importSaved){
                    def result = [isError: true, message: "Import failed save!!"]
                    render result as JSON
                    return
                }
            }
        }

        def result = [isError: true, message: "Import Updated successfully!"]
        render result as JSON
    }

    def edit(Long id) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/import/index')
            return
        }
        Import anImport = Import.read(id)
        if (!anImport) {
            def result = [isError: true, message: "Import name not found!"]
            render result as JSON
            return
        }
        def result = [isError: false, import:anImport, productItem: anImport.productItem.name, categoryType: anImport.productItem.categoryType.name]
        render result as JSON
    }

    def delete(Long id) {
        if (!request.method == 'POST') {
            flash.message = "Method Problem!"
            render(view: '/import/index')
            return
        }
        Import anImport = Import.get(id)
        if (anImport) {
            def result = [isError: true, message: "Import name not found!"]
            render result as JSON
            return
        }
        anImport.delete(flush: true)
        def result = [isError: true, message: "Import deleted successfully!"]
        render result as JSON
    }

    def importSearch() {
        if (request.method == 'POST') {
            def toImportDate = (params.toImportDate) ? Date.parse('dd/MM/yyyy', params.toImportDate) : null
            def fromImportDate = (params.fromImportDate) ? Date.parse('dd/MM/yyyy', params.fromImportDate) : null

            def importList = Import.createCriteria()
            def results = importList.list() {
                and {
                    if (toImportDate && fromImportDate) {
                        between("importDate", toImportDate, fromImportDate)
                    }
                    if (params.lcNo) {
                        eq("lcSettings", LcSettings.get(params.lcNo as Long))
                    }
                    if (params.productItem) {
                        eq("productItem", ProductItem.get(params.productItem as Long))
                    }
                    if (params.categoryType) {
                        eq("categoryType", CategoryType.get(params.categoryType as Long))
                    }
                }
            }

            def resultList = []
            for (int i=0; i<results.size(); i++){
                def result = [
                        results.lcSettings.lcNo[i],
                        results.productItem.name[i],
                        results.amount[i],
                        results.entryDate[i].format('dd/MM/yyyy'),
                        results.importDate[i].format('dd/MM/yyyy'),
                        results.categoryType.name[i]
                ]
                resultList << result
            }

            LinkedHashMap result = new LinkedHashMap()
            result.put('resultList', resultList)
            def outPut = result as JSON
            render outPut
        }
    }


    private static final String JASPER_FILE = 'reportInventoryImport.jasper'
    private static final String REPORT_FILE_FORMAT = 'pdf'
    private static final String OUTPUT_FILE_NAME = "reportInventoryImport.jasper"

    def importReport() {
//        //def companyLogo = grailsApplication.mainContext.servletContext.getRealPath("/reports/images/Logo.jpg")
//
//        Map reportMap = new LinkedHashMap()
//        reportMap.put("lcNo",12)
//
//        def reportFolder1 = grailsApplication.config.grails.databinding.dateFormats;
//        def reportDir = grailsApplication.config.jasper.dir.reports;
//
////        ApplicationHolder.getApplication().getParentContext().getResource("file/recsys").getFile();
//
//       // grailsAttributes.getApplicationContext().getResource("/relative/path/").getFile().toString()
////        String reportDir = reportFolder.absolutePath;
//        println "reportDir " + reportDir
//        JasperReportDef reportDef = new JasperReportDef(name: 'report1.jasper', fileFormat: JasperExportFormat.PDF_FORMAT,
//                parameters: reportMap)
//        ByteArrayOutputStream report = jasperService.generateReport(reportDef)            // generate report
//
//        response.contentType = grailsApplication.config.grails.mime.types[REPORT_FILE_FORMAT]
//        response.setHeader("Content-disposition", "inline;filename=${OUTPUT_FILE_NAME}")
//        response.outputStream << report.toByteArray()


        Map paramsMap = new LinkedHashMap()
        paramsMap.put("lcNo",12)
        String outputFileName = 'studentList.pdf'
        JasperReportDef reportDef = new JasperReportDef(name: JASPER_FILE, fileFormat: JasperExportFormat.PDF_FORMAT, parameters: paramsMap)

        ByteArrayOutputStream report = jasperService.generateReport(reportDef)
        response.contentType ='application/pdf'
        response.setHeader("Content-disposition", "inline;filename=${outputFileName}")
        response.outputStream << report.toByteArray()

//        response.outputStream.flush()
//        response.outputStream.close()


    }
}
