package com.startup.inventory

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class ImportService {

    static final String[] sortColumns = ['id','lcSettings']
    LinkedHashMap importPaginateList(GrailsParameterMap params){
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
        def c = Import.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {

            createAlias('lcSettings', 'lc')
            createAlias('productItem', 'pm')
            createAlias('categoryType', 'cat')
            if (sSearch) {
                or{
                    ilike("lc.lcNo", sSearch)
                    ilike("pm.name", sSearch)
                    ilike("pm.name", sSearch)
                    ilike("cat.name", sSearch)
                }
            }

            order(sortColumn, sSortDir)
        }
        int totalCount = results.totalCount
        int serial = iDisplayStart;
        String entryDateStr
        String importDateStr
        if (totalCount > 0) {
            if (sSortDir.equals(CommonUtils.SORT_ORDER_DESC)) {
                serial = (totalCount + 1) - iDisplayStart
            }
            results.each { Import anImport ->
                if (sSortDir.equals(CommonUtils.SORT_ORDER_ASC)) {
                    serial++
                } else {
                    serial--
                }
                entryDateStr =anImport.entryDate? CommonUtils.getUiDateStr(anImport.entryDate):''
                importDateStr =anImport.importDate? CommonUtils.getUiDateStr(anImport.importDate):''
                dataReturns.add([DT_RowId: anImport.id, 0: serial, 1: anImport.lcSettings.lcNo,2:anImport.categoryType.name, 3:anImport.productItem.name, 4:anImport.amount, 5:anImport.status.value, 6:''])
            }
        }
        return [totalCount:totalCount,results:dataReturns]
    }
}
