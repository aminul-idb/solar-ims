<%@ page import="com.startup.inventory.BranchDistribution; com.startup.inventory.Import; com.startup.inventory.CategoryType; com.startup.inventory.ProductItem" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Japan Solar Tech</title>
    <meta name="layout" content="main"/>
</head>

<body>

%{--Optional Sub Header--}%
<g:render template='/subHead'/>

%{--<h1>${category}</h1>--}%
    <div class="row">
        <div class="col-lg-12">
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content p-xl">
                    <div class="row">
                        <div class="col-sm-12">
                            <section class="panel">
                                <g:if test="${com.startup.inventory.CategoryType}">
                                <div class="panel-body">
                                    <%
                                        def category = CategoryType.list(sort: 'priority', order: "asc")
                                        for (int i=0; i<category.size(); i++){
                                    %>
                                        <h1>${category?.name[i]}</h1>
                                        <div class="table-responsive">
                                            <table class="table table-striped table-hover table-bordered" id="list-table">

                                                <thead>
                                                <tr>
                                                    <th>Category Name</th>
                                                    <th>Total Quantity</th>
                                                    <th>Dhaka Branch</th>
                                                    <th>Chittagong Branch</th>
                                                </tr>
                                                </thead>
                                                <%
                                                    def productList = ProductItem.findAllByCategoryType(category[i])
                                                    for (int j=0; j<productList.size(); j++){
                                                %>

                                                <tbody id="importProductDiv">

                                                    <td>${productList?.name[j]}</td>
                                                    <%
                                                        int importAmount=0;
                                                        def anImport = Import.findAllByProductItem(productList[j])
                                                        for(int k=0; k<anImport.size(); k++) {
                                                            importAmount += anImport.amount[k] as Integer
                                                        }
                                                    %>
                                                    <td>${importAmount}</td>
                                                    <%
                                                            def branchProduct = BranchDistribution.findByProductItem(productList[j])
                                                            if (branchProduct?.productItem?.name == null){
                                                                continue;
                                                            }
                                                            def productName = branchProduct?.productItem
                                                            def fromBranch = branchProduct?.fromBranch
//                                                            def toBranch = branchProduct?.toBranch
                                                            int chittagongTotalProduct = 0

                                                            def dhakaCriteria = BranchDistribution?.createCriteria()
                                                            def dhaka = dhakaCriteria.get {
                                                                and {
                                                                    eq("fromBranch", fromBranch)
                                                                    eq("productItem", productName)
                                                                }
                                                                projections {
                                                                    sum("amount")
                                                                }
                                                            }
                                                            int dhakaTotalProduct = dhaka as Integer

                                                            def chittagongCriteria = BranchDistribution?.createCriteria()
                                                            def chittagong = chittagongCriteria.get {
                                                                and {
                                                                    eq("toBranch", fromBranch)
                                                                    eq("productItem", productName)
                                                                }
                                                                projections {
                                                                    sum("amount")
                                                                }
                                                            }
                                                            if(chittagong){
                                                                chittagongTotalProduct = chittagong as Integer
                                                            }

                                                            def dhakaTo = dhakaTotalProduct - chittagongTotalProduct
                                                        %>
                                                    <td>${dhakaTo}</td>
                                                    <td>${importAmount - dhakaTo}</td>

                                                </tbody>
                                                <%
                                                    }
                                                %>
                                            </table>
                                        </div>
                                    <%
                                        }
                                    %>
                                </div>
                                </g:if>
                            </section>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</body>
</html>