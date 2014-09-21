package com.startup.inventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.dao.DataIntegrityViolationException


@Secured(['ROLE_SUPER_ADMIN'])
class ProductDistributionController {
    def jasperService
    def productDistributionService

    def index() {

        List<ProductItem> productItemList = ProductItem.list(sort: 'categoryType')

        LinkedHashMap resultMap = productDistributionService.productDistributionPaginateList(params)

        if (!resultMap || resultMap.totalCount == 0) {
            render(view: 'index', model: [dataReturn: null, totalCount: 0, productItemList: productItemList])
            return
        }
        int totalCount = resultMap.totalCount
        render(view: 'index', model: [dataReturn: resultMap.results, totalCount: totalCount, productItemList: productItemList])


    }

    def create() {}

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap = productDistributionService.productDistributionPaginateList(params)

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

    def save() {
        if (request.method != 'POST') {
            flash.message = "Method Problem!"
            render(view: '/productDistribution/index')
            return
        }

        if(params.edit == "edit"){
            ProductDistribution productDistribution = ProductDistribution.get(params.productDistributionId as Long)
            if(!productDistribution){
                def result = [isError: true, message: "Product Distribution Delete Already!!"]
                render result as JSON
                return
            }
            productDistribution.amount = params.amount
            productDistribution.toCustomer = params.toCustomer
            productDistribution.description = params.description
            productDistribution.fromBranch = params.fromBranch
            productDistribution.distributionDate = Date.parse('dd/MM/yyyy', params.distributionDate)
            productDistribution.address = params.address
            productDistribution.status = params.status
            productDistribution.productPrice = params.productPrice
            productDistribution.save(flush: true)

            def result = [isError: true, message: "Product Distribution Updated successfully!!"]
            render result as JSON
            return
        }

        // save
        def productItemList = Arrays.asList(params.productItemId)
        def amountList = Arrays.asList(params.amount)
        def productPriceList = Arrays.asList(params.productPrice)
        def productCheckList = Arrays.asList(params.productCheck)

        for (int i = 0; i < productItemList.size(); i++) {
            ProductDistribution productDistribution = new ProductDistribution()
            if ((amountList[i] != '') && (productItemList[i] in productCheckList == true)) {
                println "amount =" + amountList[i] + "| product Id =" + productItemList[i]
                productDistribution.distributionDate = Date.parse('dd/MM/yyyy', params.distributionDate)
                /*Date.parse('dd/MM/yyyy', params.distributionDate)*/
                productDistribution.toCustomer = params.toCustomer
                productDistribution.fromBranch = params.fromBranch
                productDistribution.description = params.description
                productDistribution.address = params.address
                productDistribution.status = params.status
                productDistribution.amount = amountList[i] as String
                productDistribution.productPrice = productPriceList[i] as String
                productDistribution.productItem = ProductItem.get(productItemList[i] as Long)
                productDistribution.categoryType = productDistribution.productItem.categoryType
                productDistribution.save(flush: true)
            }
        }
        def result = [isError: true, message: "Product Distribution Save successfully!!"]
        render result as JSON
    }

    def edit(Long id) {
        if (request.method != 'POST') {
            flash.message = "Method Problem!"
            render(view: '/productDistribution/index')
            return
        }
        ProductDistribution productDistribution = ProductDistribution.read(id)
        if (!productDistribution) {
            def result = [isError: true, message: "Product Distribution name not found!"]
            render result as JSON
            return
        }
        def result = [isError: false, productDistribution: productDistribution, productItem: productDistribution.productItem.name, categoryType:productDistribution.productItem.categoryType.name]
        render result as JSON
    }

    def delete(Long id) {
        if (request.method != 'POST') {
            flash.message = "Method Problem!"
            render(view: '/productDistribution/index')
            return
        }
        ProductDistribution anImport = ProductDistribution.get(id)
        if(!anImport) {
            def result = [isError: true, message: "Product Distribution name not found!"]
            render result as JSON
            return
        }
        anImport.delete(flush: true)
        def result = [isError: false, message: "Product Distribution deleted successfully!"]
        render result as JSON
    }

    def productDistributionSearch() {
        if (request.method == 'POST') {
            def toImportDate = (params.toImportDate) ? Date.parse('dd/MM/yyyy', params.toImportDate) : null
            def fromImportDate = (params.fromImportDate) ? Date.parse('dd/MM/yyyy', params.fromImportDate) : null

            def productDistribution = ProductDistribution.createCriteria()
            def results = productDistribution.list() {
                and {
                    if (toImportDate && fromImportDate) {
                        between("distributionDate", toImportDate, fromImportDate)
                    }
                    if (params.toCustomer) {
                        ilike("toCustomer", "%"+params.toCustomer+"%")
                    }
                    if (params.address) {
                        ilike("address", "%"+params.address+"%")
                    }
                    if (params.categoryType) {
                        eq("categoryType", CategoryType.get(params.categoryType as Long))
                    }
                    if (params.productItem) {
                        eq("productItem", ProductItem.get(params.productItem as Long))
                    }
                }
            }

            def resultList = []
            for (int i=0; i<results.size(); i++){
                def result = [
                        results.fromBranch[i],
                        results.toCustomer[i],
                        results.address[i],
                        results.distributionDate[i].format('dd/MM/yyyy'),
                        results.productItem.name[i],
                        results.categoryType.name[i]
                ]
                resultList << result
            }
            LinkedHashMap result = new LinkedHashMap()
            result.put('resultList', resultList)
            result.put('isError', false)
            def outPut = result as JSON
            render outPut
        }
    }

    private static final String JASPER_FILE = 'reportInventoryProductDistribution.jasper'
    private static final String REPORT_FILE_FORMAT = 'pdf'
    private static final String OUTPUT_FILE_NAME = "reportInventoryProductDistribution.jasper"

    def productDistributionReport() {
        if(params.submit == "report"){

            Map paramsMap = new LinkedHashMap()
            if(params.toDate){
                def toDate = (params.toDate) ? Date.parse('dd/MM/yyyy', params.toDate) : null
                paramsMap.put("toDate", toDate)
            }

            if(params.fromDate){
                def fromDate = (params.fromDate) ? Date.parse('dd/MM/yyyy', params.fromDate) : null
                paramsMap.put("fromDate", fromDate)
            }

            if(params.categoryType){
                def categoryType = params.categoryType as Long
                paramsMap.put("categoryType", categoryType)
            }

            if(params.productItem){
                def productItem = params.productItem as Long
                paramsMap.put("productItem", productItem)
            }

            String outputFileName = 'reportInventoryProductDistribution.pdf'
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
