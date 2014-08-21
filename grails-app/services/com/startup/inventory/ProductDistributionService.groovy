package com.startup.inventory

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class ProductDistributionService {

    static final String[] sortColumns = ['id','fromBranch','toCustomer','distributionDate']
    LinkedHashMap productDistributionPaginateList(GrailsParameterMap params){
        int iDisplayStart = params.iDisplayStart ? params.getInt('iDisplayStart') : CommonUtils.DEFAULT_PAGINATION_START
        int iDisplayLength = params.iDisplayLength ? params.getInt('iDisplayLength') : CommonUtils.DEFAULT_PAGINATION_LENGTH
        String sSortDir = params.sSortDir_0 ? params.sSortDir_0 : CommonUtils.DEFAULT_PAGINATION_SORT_ORDER
        int iSortingCol = params.iSortingCols ? params.getInt('iSortingCols') : CommonUtils.DEFAULT_PAGINATION_SORT_IDX
        //Search string, use or logic to all fields that required to include
        String sSearch = params.sSearch ? params.sSearch : null
        if (sSearch) {
            sSearch = CommonUtils.PERCENTAGE_SIGN + sSearch + CommonUtils.PERCENTAGE_SIGN
        }
        String sortColumn = CommonUtils.getSortColumn(sortColumns,iSortingCol)
        List dataReturns = new ArrayList()
        def c = ProductDistribution.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {

            if (sSearch) {
                like("fromBranch", sSearch)
            }

            order(sortColumn, sSortDir)
        }
        int totalCount = results.totalCount
        int serial = iDisplayStart;
        String bdDate
        if (totalCount > 0) {
            if (sSortDir.equals(CommonUtils.SORT_ORDER_DESC)) {
                serial = (totalCount + 1) - iDisplayStart
            }
            results.each { ProductDistribution productDistribution ->
                if (sSortDir.equals(CommonUtils.SORT_ORDER_ASC)) {
                    serial++
                } else {
                    serial--
                }
                bdDate =productDistribution.distributionDate? CommonUtils.getUiDateStr(productDistribution.distributionDate):''
                dataReturns.add([DT_RowId: productDistribution.id, 0: serial, 1: productDistribution.fromBranch, 2:productDistribution.toCustomer, 3:productDistribution.productItem.name, 4:productDistribution.productItem.categoryType.name, 5:productDistribution.amount, 6:bdDate, 7:productDistribution.status.value, 8:productDistribution.address, 9:''])
            }
        }
        return [totalCount:totalCount,results:dataReturns]
    }
}
