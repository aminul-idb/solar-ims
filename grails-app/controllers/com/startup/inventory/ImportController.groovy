package com.startup.inventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.io.FileUtils
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef

@Secured(['ROLE_SUPER_ADMIN'])
class ImportController {

    def importService
    def jasperService

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

//        print(params.productItemId)
//        def productItem = Arrays.asList(params.productItemId)
//        print("productItem >> "+ productItem)
//        if (productItem.size())

        // save
        def productItemList = Arrays.asList(params.productItemId)
        def amountList = Arrays.asList(params.amount)
        def productPriceList = Arrays.asList(params.productPrice)
        def productCheckList = Arrays.asList(params.productCheck)
        def lcNo = LcSettings.get(params.lcSettings as Long)

        for(int i=0; i<productItemList.size(); i++){
            Import anImport = new Import()
            if ( (amountList[i] != '') && (productItemList[i] in productCheckList == true)  ){
//                anImport.importDate = Date.parse('dd/MM/yyyy', lcNo.lcDate)
                anImport.amount = amountList[i] as String
                anImport.productPrice = productPriceList[i] as String
                anImport.status = params.status
                anImport.lcSettings = lcNo
                anImport.importDate = lcNo.lcDate
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
        if (!anImport) {
            def result = [isError: true, message: "Import name not found!"]
            render result as JSON
            return
        }
        anImport.delete(flush: true)
        def result = [isError: false, message: "Import deleted successfully!"]
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
                        //results.importDate[i].format('dd/MM/yyyy'),
                        results.categoryType.name[i]
                ]
                print("??" + results.categoryType.name[0])
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
//        def companyLogo = grailsApplication.mainContext.servletContext.getRealPath("/reports/images/Logo.jpg")

        if(params.submit == "report"){
            Map paramsMap = new LinkedHashMap()
            paramsMap.put("lcNo", params.lcNo)
            String outputFileName = 'reportInventoryImport.pdf'
            JasperReportDef reportDef = new JasperReportDef(
                    name: JASPER_FILE,
                    fileFormat: JasperExportFormat.PDF_FORMAT,
                    parameters: paramsMap
            )

            ByteArrayOutputStream report = jasperService.generateReport(reportDef)
            response.contentType ='application/pdf'
            response.setHeader("Content-disposition", "inline;filename=${outputFileName}")
            response.outputStream << report.toByteArray()
        }

    }
}
