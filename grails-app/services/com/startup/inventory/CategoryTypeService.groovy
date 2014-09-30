package com.startup.inventory

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class CategoryTypeService {

    static final String[] sortColumns = ['id', 'name', 'priority']
    LinkedHashMap categoryTypePaginateList(GrailsParameterMap params){
        int iDisplayStart = params.iDisplayStart ? params.getInt('iDisplayStart') : CommonUtils.DEFAULT_PAGINATION_START
        int iDisplayLength = params.iDisplayLength ? params.getInt('iDisplayLength') : CommonUtils.DEFAULT_PAGINATION_LENGTH
        String sSortDir = params.sSortDir_0 ? params.sSortDir_0 : CommonUtils.DEFAULT_PAGINATION_SORT_ORDER
        def iSortingCol = params.iSortingCols ? params.getInt('iSortingCols') : CommonUtils.DEFAULT_PAGINATION_SORT_IDX
        //Search string, use or logic to all fields that required to include
        String sSearch = params.sSearch ? params.sSearch : null
        if (sSearch) {
            sSearch = CommonUtils.PERCENTAGE_SIGN + sSearch + CommonUtils.PERCENTAGE_SIGN
        }
        String sortColumn = CommonUtils.getSortColumn(sortColumns,iSortingCol)
        List dataReturns = new ArrayList()
        def c = CategoryType.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {

            if (sSearch) {
                like("name", sSearch)
            }

            order(sortColumn, sSortDir)
//            order("priority", 'asc')
        }
        int totalCount = results.totalCount
        int serial = iDisplayStart;
        if (totalCount > 0) {
            if (sSortDir.equals(CommonUtils.SORT_ORDER_ASC)) {
                serial = (totalCount + 1) - iDisplayStart
            }
            results.each { CategoryType categoryType ->
                if (sSortDir.equals(CommonUtils.SORT_ORDER_DESC)) {
                    serial++
                } else {
                    serial--
                }
                dataReturns.add([DT_RowId: categoryType.id, 0: serial, 1: categoryType.name,2:categoryType.description, 3:categoryType.status.name(), 4: ''])
            }
        }
        return [totalCount:totalCount,results:dataReturns]
    }
}