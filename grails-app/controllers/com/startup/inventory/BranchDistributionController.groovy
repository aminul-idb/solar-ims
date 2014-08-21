package com.startup.inventory

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.dao.DataIntegrityViolationException

import java.text.DateFormat
import java.text.SimpleDateFormat

@Secured(['ROLE_SUPER_ADMIN'])
class BranchDistributionController {

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
        def productItemList = params.productItemId
        def amountList = params.amount
        def productPriceList = params.productPrice
        def productCheckList = params.productCheck

        for (int i = 0; i < productItemList.length; i++) {
            BranchDistribution branchDistribution = new BranchDistribution()
            if ((amountList[i] != '') && (productItemList[i] in productCheckList == true)) {
                println "amount =" + amountList[i] + "| product Id =" + productItemList[i]
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
            result.put('message', 'Import name not found')
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
}
