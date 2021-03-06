package com.startup.inventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.dao.DataIntegrityViolationException

import org.apache.commons.io.FileUtils

import java.text.DateFormat
import java.text.SimpleDateFormat

@Secured(['ROLE_SUPER_ADMIN'])
class BranchDistributionController {
    def jasperService
    def distributionService

    def index() {
        List<ProductItem> productItemList = ProductItem.list(sort: 'categoryType')
        LinkedHashMap resultMap = distributionService.distributionPaginateList(params)
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
        LinkedHashMap resultMap = distributionService.distributionPaginateList(params)

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
        LinkedHashMap result = new LinkedHashMap()
        result.put('isError', true)
        String outPut

         if(params.edit == "edit"){
            BranchDistribution anImport = BranchDistribution.get(params.branchDistributionId as Long)
            anImport.amount = params.amount
            anImport.productPrice = params.productPrice
            anImport.save(flush: true)
            result.put('isError', false)
            result.put('message', 'Branch Distribution Updated successfully')
            outPut = result as JSON
            render outPut
            return
        }

        // unique branch id generated
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        Date date = new Date();
        String branchUniqueId = (dateFormat.format(date) + params.fromBranch +"To" + params.toBranch) as String


        // save
        def productItemList = Arrays.asList(params.productItemId)
        def amountList = Arrays.asList(params.amount)
        def productPriceList = Arrays.asList(params.productPrice)
        def productCheckList = Arrays.asList(params.productCheck)
        for (int i=0; i<productItemList.size(); i++) {
            BranchDistribution branchDistribution = new BranchDistribution()
            if ((amountList[i] != '') && (productItemList[i] in productCheckList == true)) {
                branchDistribution.distributionDate = Date.parse('dd/MM/yyyy', params.distributionDate)
                branchDistribution.toBranch = params.toBranch
                branchDistribution.fromBranch = params.fromBranch
                branchDistribution.description = params.description
                branchDistribution.status = params.status
                branchDistribution.branchUniqueId = branchUniqueId
                branchDistribution.productPrice = productPriceList[i] as String
                branchDistribution.amount = amountList[i] as String
                branchDistribution.productItem = ProductItem.read(productItemList[i] as Long)
                branchDistribution.save(flush: true)
            }
        }
        result.put('isError', false)
        result.put('message', 'Branch Distribution Updated successfully')
        outPut = result as JSON
        render outPut
    }

    def edit(Long id) {

        if (!request.method == 'POST') {
            redirect(action: 'index')
            return
        }
        LinkedHashMap result = new LinkedHashMap()
        String outPut

        BranchDistribution branchDistribution = BranchDistribution.read(id)
        if (!branchDistribution) {
            result.put('isError', true)
            result.put('message', 'Branch Distribution name not found')
            outPut = result as JSON
            render outPut
            return
        }

        result.put('isError', false)
        result.put('branchDistribution', branchDistribution)
        result.put('productItem',branchDistribution.productItem.name)
        result.put('categoryType',branchDistribution.productItem.categoryType.name)



        outPut = result as JSON
        render outPut
    }

    def delete(Long id) {
        LinkedHashMap result = new LinkedHashMap()

        result.put('isError', true)
        String outPut
        BranchDistribution anImport = BranchDistribution.get(id)
        if (anImport) {
            try {
                anImport.delete(flush: true)
                result.put('isError', false)
                result.put('message', "Branch Distribution deleted successfully.")
                outPut = result as JSON
                render outPut
                return

            }

            catch (DataIntegrityViolationException e) {
                result.put('isError', true)
                result.put('message', "Branch Distribution could not deleted. Already in use.")
                outPut = result as JSON
                render outPut
                return
            }

        }
        result.put('isError', true)
        result.put('message', "Branch Distribution not found")
        outPut = result as JSON
        render outPut
        return
    }

    private static final String JASPER_FILE = 'reportInventoryBranchDistribution.jasper'
    private static final String REPORT_FILE_FORMAT = 'pdf'
    private static final String OUTPUT_FILE_NAME = "reportInventoryBranchDistribution.jasper"

    def branchDistributionReport() {
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

            String outputFileName = 'reportInventoryBranchDistribution.pdf'
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
