package com.startup.inventory

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class DistributionService {

        static final String[] sortColumns = ['id','fromBranch','toBranch','distributionDate']
        LinkedHashMap distributionPaginateList(GrailsParameterMap params){
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
            def c = BranchDistribution.createCriteria()
            def results = c.list(max: iDisplayLength, offset: iDisplayStart) {

                createAlias('productItem', 'pm')
                createAlias('productItem.categoryType', 'cat')

                if (sSearch) {
                    or{
                        ilike("fromBranch", sSearch)
                        ilike("toBranch", sSearch)
                        ilike("pm.name", sSearch)
                        ilike("cat.name", sSearch)
                    }

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
                results.each { BranchDistribution branchDistribution ->
                    if (sSortDir.equals(CommonUtils.SORT_ORDER_ASC)) {
                        serial++
                    } else {
                        serial--
                    }
                    bdDate =branchDistribution.distributionDate? CommonUtils.getUiDateStr(branchDistribution.distributionDate):''
                    dataReturns.add([DT_RowId: branchDistribution.id, 0: serial, 1: branchDistribution.toBranch, 2:branchDistribution.fromBranch, 3:branchDistribution.productItem.name, 4:branchDistribution.productItem.categoryType.name, 5:bdDate, 6:branchDistribution.amount, 7:branchDistribution.productPrice,  8:branchDistribution.status.value, 9:''])
                }
            }
            return [totalCount:totalCount,results:dataReturns]
        }
    }

