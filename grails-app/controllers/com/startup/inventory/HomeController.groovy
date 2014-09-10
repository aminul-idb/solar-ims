package com.startup.inventory

import grails.plugin.springsecurity.annotation.Secured

import javax.validation.constraints.Null

@Secured(['ROLE_SUPER_ADMIN'])
class HomeController {

    def index() {
        def category = CategoryType.list()
        for (int i=0; i<category.size(); i++){
            def productList = ProductItem.findAllByCategoryType(category[i])
            for (int j=0; j<productList.size(); j++) {
                def anImport = Import.findAllByProductItem(productList[j])
                for (int k = 0; k < anImport.size(); k++) {
//                    print("product amount:" + anImport.amount[k])
                }
                def branchProduct = BranchDistribution.findByProductItem(productList[j])
                print(">>>>>>>>>>>>>>>>>>>>>>>" + branchProduct?.productItem?.name)
                if (branchProduct?.productItem?.name == null){
                    continue
                }
//                for (int l=0; l<branchProduct.size(); l++){
                def productName = branchProduct?.productItem
                def fromBranch = branchProduct?.fromBranch
                def c = BranchDistribution?.createCriteria()
                def branchCount = c.get {
                    and {
                        eq("fromBranch", fromBranch)
                        eq("productItem", productName)
                    }
                    projections {
                        sum("amount")
                    }
                }
                print("__" + branchCount)
            }
        }
        //render(view: '/home/index', model: [category: category])
    }
}
