<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Distribution Product</title>
    <asset:stylesheet src="formDataTable.css"/>
    <asset:javascript src="formDataTable.js"/>
    <asset:javascript src="bootstrap-datepicker.min.js"/>

</head>

<body>

<div class="row wrapper border-bottom white-bg page-heading">

    <div class="col-lg-8">
        <h2>Distribution Product Search</h2>
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
                                    <form class="cmxform form-horizontal " id="create-form-product">
                                        <g:hiddenField name="id"/>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="fromBranch" class="control-label">From</label>
                                                <g:select class="form-control fromBranch" id="fromBranch" name='fromBranch'
                                                          noSelection="${['': 'Select One...']}"
                                                          from='${com.startup.inventory.Branch.values()}'
                                                          optionKey="key" optionValue="value"></g:select>
                                                <span class="help-block" for="fromBranch"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="toCustomer" class="control-label">Customer</label>
                                                <g:textField class="form-control toCustomer" id="toCustomer"
                                                             name="toCustomer" placeholder="Enter Customer Name."/>
                                                <span class="help-block" for="description"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="address" class="control-label">Address</label>
                                                <g:textField class="form-control address" id="address"
                                                             name="address" placeholder="Enter Customer Name."/>
                                                <span class="help-block" for="address"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="datepicker" class="control-label ">Date</label>
                                                <input type="text" class="form-control datepicker" id="datepicker"
                                                       name="distributionDate" placeholder="Enter distribution Date."/>
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
                                                <span class="help-block" for="description"></span>
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
                                                <span class="help-block" for="status"></span>
                                            </div>
                                        </div>
                                        %{-- end single edit--}%


                                        <div class="form-group">
                                            <div class="col-md-offset-8 col-md-4">
                                                <button name="submit" class="btn btn-primary" tabindex="3" type="submit">Search</button>
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


<div class="row">
    <div class="col-lg-12">
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox-content p-xl">
                <div class="row">
                    <div class="col-sm-12">
                        <section class="panel">

                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover table-bordered" id="list-table">
                                        <thead>
                                        <tr>
                                            <th class="text-center">Form Branch</th>
                                            <th class="text-center">To Customer</th>
                                            <th class="text-center">Product Name</th>
                                            %{--<th class="text-center">Category Name</th>--}%
                                            <th class="text-center">Amount</th>
                                            <th class="text-center">Date</th>
                                            <th class="text-center">Address</th>
                                        </tr>
                                        </thead>
                                        <tbody id="distributionProductDiv">

                                        </tbody>
                                    </table>
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

<script type="text/javascript">

    jQuery(function ($) {

        $('#datepicker').datepicker({
            format: 'dd/mm/yyyy',
            gotoCurrent: true,
            autoclose: true
        });

        $('#create-form-product').validate({
            errorElement: 'label',
            errorClass: 'help-block',
            focusInvalid: false,
            rules: {
                name: {
                    required: false,
                    minlength: 2
                }
            },
            messages: {
                name: {
                    required: "Please provide LC",
                    minlength: "LC must be at least 2 characters long"
                }
            },
            invalidHandler: function (event, validator) {
                $('.alert-danger', $('#currencyForm')).show();
            },

            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },

            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
                $(e).remove();
            },
            submitHandler: function (form) {
                $.ajax({
                    url: "${createLink(controller: 'productDistribution', action: 'productDistributionSearch')}" ,
                    type: 'post',
                    dataType: "json",
                    data: $("#create-form-product").serialize(),
                    success: function (data) {
                        if(data.isError == false) {
                            $('tbody#distributionProductDiv').html('');
                            for (var i = 0; i < data.resultList.length; i++) {
                                var html = "<tr>";
                                html += "<td>" + data.resultList[i][0] + "</td>";
                                html += "<td>" + data.resultList[i][1] + "</td>";
                                html += "<td>" + data.resultList[i][2] + "</td>";
                                html += "<td>" + data.resultList[i][3] + "</td>";
                                html += "<td>" + data.resultList[i][4] + "</td>";
                                html += "<td>" + data.resultList[i][5] + "</td>";
                                html += "</tr>";
                                $('tbody#distributionProductDiv').append(html);
                            }
                        }else{
                            setTimeout(function() {
                                $.gritter.add({
                                    title: data.message
                                });
                            }, 2000);
                        }
                    },
                    failure: function (data) {
                    }
                })
            }
        });

    });

</script>

</body>
</html>