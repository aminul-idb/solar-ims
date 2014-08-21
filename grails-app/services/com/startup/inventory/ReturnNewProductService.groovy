package com.startup.inventory

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class ReturnNewProductService {

    static final String[] sortColumns = ['id']
    LinkedHashMap returnNewProductPaginateList(GrailsParameterMap params){
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
        def c = ReturnNewProduct.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {

            /*if (sSearch) {
                like("name", sSearch)
            }*/

            order(sortColumn, sSortDir)
        }
        int totalCount = results.totalCount
        int serial = iDisplayStart;
        def returnNewDate
        if (totalCount > 0) {
            if (sSortDir.equals(CommonUtils.SORT_ORDER_DESC)) {
                serial = (totalCount + 1) - iDisplayStart
            }
            results.each { ReturnNewProduct returnNewProduct ->
                if (sSortDir.equals(CommonUtils.SORT_ORDER_ASC)) {
                    serial++
                } else {
                    serial--
                }
                returnNewDate = returnNewProduct.returnNewDate? CommonUtils.getUiDateStr(returnNewProduct.returnNewDate):''
                dataReturns.add([DT_RowId: returnNewProduct.id, 0: serial, 1: returnNewProduct.productItem.name, 2:returnNewProduct.productAmount, 3: returnNewDate, 4: returnNewProduct.description, 5: ''])
            }
        }
        return [totalCount:totalCount,results:dataReturns]
    }
}
