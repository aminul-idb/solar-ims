<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Import Product</title>
    <asset:stylesheet src="formDataTable.css"/>
    <asset:javascript src="formDataTable.js"/>
    <asset:stylesheet src="datepicker.css"/>
    <asset:javascript src="bootstrap-datepicker.js"/>

</head>

<body>

<div class="row wrapper border-bottom white-bg page-heading">

    <div class="col-lg-8">
        <h2>Import Product</h2>
    </div>

    <div class="col-lg-4">
        <div class="title-action">
            <span class="tools pull-right">
                <div class="btn-group">
                    <button id="add-new-btn" class="btn btn-primary">
                        Add New&nbsp;&nbsp;<i class="fa fa-plus"></i>
                    </button>
                </div>
            </span>
        </div>
    </div>

</div>

<div class="row" id="animportCreate" style="display:none">
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
                                                <label for="lcSettings" class="control-label">LC No.</label>

                                                <select name="lcSettings" id="lcSettings" class="form-control lcSettings" required="required">
                                                    <option value="">Select Lc</option>
                                                    <g:each in="${com.startup.inventory.LcSettings.list()}" var="lcSettings" >
                                                        <option value="${lcSettings.id}">${lcSettings.lcNo}</option>
                                                    </g:each>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="description" class="control-label">Description</label>
                                                <g:textField class="form-control" id="description"
                                                             name="description" placeholder="Enter Description."/>
                                                <span class="help-block" for="description"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="status" class=" control-label">Status </label><br>
                                                <g:select class="form-control" id="status" name='status'
                                                          from='${com.startup.inventory.Status.values()}'
                                                          optionKey="key" optionValue="value"></g:select> %{--noSelection="${['': 'Select One...']}"--}%
                                                <span class="help-block" for="status"></span>
                                            </div>
                                        </div>

                                        <div class="col-md-12">
                                            <table class="table table-striped table-hover table-bordered" id="productList-table">
                                                <thead>
                                                <tr>
                                                    <th>Check Mark</th>
                                                    <th>Category</th>
                                                    <th>Product Name</th>
                                                    <th>Quantity</th>
                                                    <th>Price</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <g:each in="${productItemList}" var="productItem" status="i">
                                                    <tr>
                                                        <input type="hidden" name="productItemId" value="${productItem?.id}" />
                                                        <td><input type="checkbox" name="productCheck" value="${productItem?.id}" /></td>
                                                        <td>${productItem?.name}</td>
                                                        <td>${productItem?.categoryType?.name}</td>
                                                        <td><input type="number" name="amount"/></td>
                                                        <td><input type="number" name="productPrice" /></td>
                                                    </tr>
                                                </g:each>

                                                </tbody>
                                            </table>

                                        </div>

                                        %{-- end single edit--}%


                                        <div class="form-group">
                                            <div class="col-md-offset-8 col-md-4">
                                                <button name="submit" class="btn btn-primary" tabindex="3" type="submit">Save</button>
                                                <button class="btn btn-default" tabindex="4" type="reset">Cancel</button>
                                            </div>
                                        </div>

                                    </form>

                                    <form class="cmxform form-horizontal" id="edit-form-product">
                                        <input type="hidden" id="edit" name="edit" value="edit" />
                                        <input type="hidden" id="importProduct" name="importProductId" />

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="lcSettings" class="control-label">LC No.</label>

                                                <select name="lcSettings" id="editLcSettings" class="form-control" required="required">
                                                    <option value="">Select Lc</option>
                                                    <g:each in="${com.startup.inventory.LcSettings.list()}" var="lcSettings" >
                                                        <option value="${lcSettings.id}">${lcSettings.lcNo}</option>
                                                    </g:each>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="description" class="control-label">Import Description</label>
                                                <g:textField class="form-control" id="description"
                                                             name="description" placeholder="Enter Description."/>
                                                <span class="help-block" for="description"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="status" class=" control-label">Status </label><br>
                                                <g:select class="form-control" id="status" name='status'
                                                          from='${com.startup.inventory.Status.values()}'
                                                          optionKey="key" optionValue="value"></g:select> %{--noSelection="${['': 'Select One...']}"--}%
                                                <span class="help-block" for="status"></span>
                                            </div>
                                        </div>

                                        <div class="col-md-12">
                                            %{-- single edit--}%
                                            <table class="table table-striped table-hover table-bordered" id="importProductEdit">
                                                <thead>
                                                <tr>
                                                    <th>Category</th>
                                                    <th>Product Name</th>
                                                    <th>Quantity</th>
                                                    <th>Category Price</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td><input type="text" name="productItem" id="productItem" disabled="disabled" /></td>
                                                    <td><input type="text" name="categoryType" id="categoryType" disabled="disabled" /></td>
                                                    <td><input type="number" name="amount"/></td>
                                                    <td><input type="number" name="productPrice" id="productPrice" /></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>

                                        %{-- end single edit--}%


                                        <div class="form-group">
                                            <div class="col-md-offset-8 col-md-4">
                                                <button name="submit" class="btn btn-primary" tabindex="3" id="editButton" type="submit">Save</button>
                                                <button class="btn btn-default" tabindex="4" type="reset">Cancel</button>
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
                                            <th class="text-center">Serial</th>
                                            <th class="text-center">LC No.</th>
                                            <th class="text-center">Product</th>
                                            <th class="text-center">Category</th>
                                            <th class="text-center">Quantity</th>
                                            <th class="text-center">Status</th>
                                            <th class="text-center">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody class="text-center">
                                        <g:each in="${dataReturn}" var="animated">
                                            <tr>
                                                <td>${animated[0]}</td>
                                                <td>${animated[1]}</td>
                                                <td class="bigFont">${animated[2]}</td>
                                                <td>${animated[3]}</td>
                                                <td>${animated[4]}</td>
                                                <td>${animated[5]}</td>
                                                <td>
                                                    <sec:access controller="import" action="edit">
                                                        <span class="col-md-6">
                                                            <a href="" referenceId="${animated.DT_RowId}"
                                                                                  class="edit-reference"
                                                                                  title="Edit"><span
                                                                    class="green fa fa-edit"></span>&nbsp;Edit&nbsp;</a>
                                                        </span>
                                                    </sec:access>
                                                    <sec:access controller="import" action="delete">
                                                        <span class="col-md-6"><a href=""
                                                                                  referenceId="${animated.DT_RowId}"
                                                                                  class="delete-reference"
                                                                                  title="Delete"><span
                                                                    class="green fa fa-cut"></span>&nbsp;Delete&nbsp;
                                                        </a></span>
                                                    </sec:access>
                                                </td>
                                            </tr>
                                        </g:each>

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

<script>

    jQuery(function ($) {

        //$('#edit-form-product').hide();
        $('#create-form-product').validate({
        errorElement: 'small',
        errorClass: 'help-block',
        focusInvalid: false,
        rules: {
            lcSettings: {
                required: true
            }
        },
        messages: {
            lcSettings: {
                required: "Please provide LC"
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
                url: "${createLink(controller: 'import', action: 'save')}" ,
                type: 'post',
                dataType: "json",
                data: $("#create-form-product").serialize(),
                success: function (data) {
                    if(data.isError == false){
                        $('#create-form-product')[0].reset();
                        $("#animportCreate").toggle(500);
                        var table = $('#list-table').DataTable();
                        table.ajax.reload();
                        setTimeout(function() {
                            $.gritter.add({
                                title: data.message
                            });
                        }, 2000);
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

        $('#editButton').click(function(e){
            jQuery.ajax({
                url: "${createLink(controller: 'import', action: 'save')}",
                type: 'post',
                dataType: "json",
                data: $("#edit-form-product").serialize(),
                success: function (data) {
                    if(data.isError == false){
                        $('#create-form-product')[0].reset();
                        $('#edit').val("edit");
                        $("#animportCreate").toggle(500);
                        var table = $('#list-table').DataTable();
                        table.ajax.reload();
                        setTimeout(function() {
                            $.gritter.add({
                                title: data.message
                            });
                        }, 2000);
                    }
                    else{
                        setTimeout(function() {
                            $.gritter.add({
                                title: data.message
                            });
                        }, 2000);
                    }

                },
                failure: function (data) {
                }
            });
            e.preventDefault();

        });

        $('#datepicker').datepicker({
            format: 'dd/mm/yyyy',
            gotoCurrent: true,
            autoclose: true
        });


        var oTable1 = $('#list-table').dataTable({
            "sDom": "<'row'<'col-md-4'><'col-md-4'><'col-md-4'f>r>t<'row'<'col-md-4'l><'col-md-4'i><'col-md-4'p>>",
//            "bProcessing": true,
            "bAutoWidth": true,
            "bServerSide": true,
            %{--"deferLoading": ${totalCount},--}%
            "sAjaxSource": "${g.createLink(controller: 'import',action: 'list')}",
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                if (aData.DT_RowId == undefined) {
                    return true;
                }
                $('td:eq(2)', nRow).addClass('bigFont');
                $('td:eq(6)', nRow).html(getActionButtons(nRow, aData));
                return nRow;
            },
            "iDisplayLength": 100,
            "aaSorting": [[0, 'desc']],
            "aoColumns": [
                null,
                null,
                { "bSortable": false },
                { "bSortable": false },
                { "bSortable": false },
                { "bSortable": false },
                { "bSortable": false }

            ]
        });

        $('#add-new-btn').click(function (e) {
            $("#animportCreate").toggle(500);
            $("#lcSettings").focus();
            $('#create-form-product').show();
            $('#edit-form-product').hide();
            $( "#create-form-product" )[ 0 ].reset();



            e.preventDefault();
        });

        $('#list-table').on('click', 'a.edit-reference', function (e) {
            var control = this;
            var referenceId = $(control).attr('referenceId');
            jQuery.ajax({
                type: 'POST',
                dataType: 'JSON',
                url: "${g.createLink(controller: 'import',action: 'edit')}?id=" + referenceId+"&edit="+"edit",
                success: function (data, textStatus) {

                    if (data.isError == false) {
                        $('#create-form-product').hide();
                        $('#edit-form-product').show();

                        $('#importProduct').val(data.import.id);
                        $('#editLcSettings').val(data.import.lcSettings.id);
                        //$('.datepicker').datepicker('setDate', new Date(data.import.importDate));
                        $('.description').val(data.import.description);
                        $('.status').val(data.import.status ? data.import.status.name :'');
                        $("#animportCreate").show(500);

                        $('#productItem').val(data.productItem);
                        $('#categoryType').val(data.categoryType);
                        $('#amount').val(data.import.amount);
                        $('#productPrice').val(data.import.productPrice);

                        document.getElementById("lcSettings").disabled=true;
                        //document.getElementById("datepicker").disabled=true;

                    } else {
                        alert(data.message);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
            e.preventDefault();
        });

        $('#list-table').on('click', 'a.delete-reference', function (e) {
            var selectRow = $(this).parents('tr');
            var confirmDel = confirm("Are you sure?");
            if (confirmDel == true) {
                var control = this;
                var referenceId = $(control).attr('referenceId');
                jQuery.ajax({
                    type: 'POST',
                    dataType: 'JSON',
                    url: "${g.createLink(controller: 'import',action: 'delete')}?id=" + referenceId,
                    success: function (data, textStatus) {
                        if (data.isError == false) {
                            $("#list-table").DataTable().row(selectRow).remove().draw();
                            var table = $('#list-table').DataTable();
                            table.ajax.reload();
                        } else {
                            alert(data.message);
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
            }
            e.preventDefault();
        });
    });

    function getActionButtons(nRow, aData) {
        var actionButtons = "";
        actionButtons += '<sec:access controller="import" action="edit"><span class="col-md-6"><a href="" referenceId="' + aData.DT_RowId + '" class="edit-reference" title="Edit">';
        actionButtons += '<span class="green green fa fa-edit"></span>';
        actionButtons += '&nbsp;Edit&nbsp;</a></span></sec:access>';
        actionButtons += '<sec:access controller="import" action="delete"><span class="col-md-6"><a href="" referenceId="' + aData.DT_RowId + '" class="delete-reference" title="Delete">';
        actionButtons += '<span class="red green fa fa-cut"></span>';
        actionButtons += '&nbsp;Delete&nbsp;</a></span></sec:access>';
        return actionButtons;
    }

</script>

</body>
</html>