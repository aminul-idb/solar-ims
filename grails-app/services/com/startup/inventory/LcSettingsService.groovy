package com.startup.inventory

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class LcSettingsService {

    static final String[] sortColumns = ['id','lcNo']
    LinkedHashMap lcSettingsPaginateList(GrailsParameterMap params){
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
        def c = LcSettings.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {

            if (sSearch) {
                like("lcNo", sSearch)
            }

            order(sortColumn, sSortDir)
        }
        int totalCount = results.totalCount

        int serial = iDisplayStart;
        String lcDate
        if (totalCount > 0) {
            if (sSortDir.equals(CommonUtils.SORT_ORDER_DESC)) {
                serial = (totalCount + 1) - iDisplayStart
            }
            results.each { LcSettings anImport ->
                if (sSortDir.equals(CommonUtils.SORT_ORDER_ASC)) {
                    serial++
                } else {
                    serial--
                }
                lcDate =anImport.lcDate? CommonUtils.getUiDateStr(anImport.lcDate):''
                dataReturns.add([DT_RowId: anImport.id, 0: serial, 1: anImport.lcNo, 2: anImport.description,3: lcDate, 4:anImport.status.value, 5:''])
            }
        }
        return [totalCount:totalCount,results:dataReturns]
    }
}
