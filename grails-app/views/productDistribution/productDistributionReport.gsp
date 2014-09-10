<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Product Distribution Report</title>
    <asset:javascript src="bootstrap-datepicker.min.js"/>

</head>

<body>

<div class="row wrapper border-bottom white-bg page-heading">

    <div class="col-lg-8">
        <h2>Product Distribution Report</h2>
    </div>

</div>

<div class="row" id="animportCreate">
    <div class="col-lg-12">
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox-content p-xl">

                <div class="row">
                    <div class="col-md-12">
                        <section class="panel">
                            <div class="panel-body">
                                <div class="form">
                                    <form class="cmxform form-horizontal" method="post" action="${createLink(controller: 'productDistribution', action: 'productDistributionReport')}" id="create-form-product">

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="toDatepicker" class="control-label ">To Date</label>
                                                <input type="date" class="form-control datepicker" id="toDatepicker"
                                                       name="toDate" placeholder="Enter Import Date." required="required"/>
                                                <span class="help-block" for="datepicker"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="fromDatepicker" class="control-label ">From Date</label>
                                                <input type="date" class="form-control datepicker" id="fromDatepicker"
                                                       name="fromDate" placeholder="Enter Import Date." required="required"/>
                                                <span class="help-block" for="datepicker"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="categoryType" class="control-label">Category</label>
                                                <select name="categoryType" id="categoryType" class="form-control lcSettings">
                                                    <option value="">Select Lc</option>
                                                    <g:each in="${com.startup.inventory.CategoryType.list(sort: 'name')}" var="categoryType" >
                                                        <option value="${categoryType?.id}">${categoryType.name}</option>
                                                    </g:each>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="productItem" class=" control-label">Product Name </label><br>
                                                <select name="productItem" id="productItem" class="form-control productItem">
                                                    <option value="">Select Product</option>
                                                    <g:each in="${com.startup.inventory.ProductItem.list(sort: 'categoryType')}" var="productItem" >
                                                        <option value="${productItem?.id}">${productItem?.name}</option>
                                                    </g:each>
                                                </select>
                                            </div>
                                        </div>



                                        <div class="form-group">
                                            <div class="col-md-offset-8 col-md-4">
                                                <button name="submit" class="btn btn-primary" tabindex="3" value="report" type="submit">Report</button>
                                                <button class="btn btn-default" tabindex="4" type="reset">Reset</button>
                                            </div>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </section>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<!-- page end-->

<script type="application/javascript">
    jQuery(function ($) {
        $('#toDatepicker').datepicker({
            format: 'dd/mm/yyyy',
            gotoCurrent: true,
            autoclose: true
        });

        $('#fromDatepicker').datepicker({
            format: 'dd/mm/yyyy',
            gotoCurrent: true,
            autoclose: true
        });
    });
</script>

</body>
</html>