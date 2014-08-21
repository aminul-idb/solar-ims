<%@ page import="com.startup.inventory.ProductItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Damage Product</title>
    <asset:stylesheet src="formDataTable.css"/>
    <asset:javascript src="formDataTable.js"/>
    <asset:javascript src="bootstrap-datepicker.min.js"/>
</head>

<body>

<div class="row wrapper border-bottom white-bg page-heading">

    <div class="col-lg-8">
        <h2>Damage Product</h2>
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

<div class="row" id="productNameCreate" style="display:none">
    <div class="col-lg-12">
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="ibox-content p-xl">

                <div class="row">
                    <div class="col-lg-12">
                        <section class="panel">
                            <div class="panel-body">
                                <div class="form">
                                    <form class="cmxform form-horizontal " id="create-form">
                                        <g:hiddenField name="id"/>

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="productItem" class="control-label">Product Name</label>
                                                <g:select class="form-control" id="productItem" name='productItem'
                                                          noSelection="${['': 'Select One...']}"
                                                          from='${ProductItem.list()}'
                                                          optionKey="id" optionValue="name"></g:select>
                                                <span for="name" class="help-block"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="productAmount" class="control-label">Product Amount</label>
                                                <g:textField class="form-control" id="productAmount" tabindex="2"
                                                             name="productAmount" placeholder="Product Amount."/>
                                                <span class="help-block" for="productAmount"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="datepicker" class="control-label ">Date</label>
                                                <input type="text" class="form-control datepicker" id="datepicker"
                                                       name="damageDate" placeholder="Enter Damage Date."/>
                                                <span class="help-block" for="datepicker"></span>
                                            </div>
                                        </div>


                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="description" class="control-label">Product Description</label>
                                                <g:textField class="form-control" id="description" tabindex="2"
                                                             name="description" placeholder="Enter Description."/>
                                                <span class="help-block" for="description"></span>
                                            </div>
                                        </div>


                                        <div class="form-group">
                                            <div class="col-md-offset-8 col-lg-4">
                                                <button name="submit" class="btn btn-primary" tabindex="3" type="submit">Save</button>
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
                                    <table class="table text-center table-striped table-hover table-bordered" id="list-table">
                                        <thead>
                                        <tr>
                                            <th class="text-center">Serial</th>
                                            <th class="text-center">Product Name</th>
                                            <th class="text-center">Product Amount</th>
                                            <th class="text-center">Damage Date</th>
                                            <th class="text-center">Description</th>
                                            <th class="text-center">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <g:each in="${dataReturn}" var="productItem">
                                            <tr>
                                                <td>${productItem[0]}</td>
                                                <td>${productItem[1]}</td>
                                                <td>${productItem[2]}</td>
                                                <td>${productItem[3]}</td>
                                                <td>${productItem[4]}</td>
                                                <td>
                                                    <sec:access controller="productItem" action="edit">
                                                        <span class="col-xs-6"><a href=""
                                                                                  referenceId="${productItem.DT_RowId}"
                                                                                  class="edit-reference"
                                                                                  title="Edit"><span
                                                                    class="green fa fa-edit"></span>&nbsp;Edit&nbsp;</a>
                                                        </span>
                                                    </sec:access>
                                                    <sec:access controller="productItem" action="delete">
                                                        <span class="col-xs-6"><a href=""
                                                                                  referenceId="${productItem.DT_RowId}"
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

    $('#datepicker').datepicker({
        format: 'dd/mm/yyyy',
        gotoCurrent: true,
        autoclose: true
    });


    $('#create-form').validate({
        errorElement: 'label',
        errorClass: 'help-block',
        focusInvalid: false,
        rules: {
            name: {
                required: true,
                minlength: 2
            },
            description: {
                maxlength: 200
            },
            status: {
                required: true
            },
            subCat: {
                required: true
            }
        },
        messages: {
            name: {
                required: "Please provide a Name",
                minlength: "Name must be at least 2 characters long"
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
                url: "${createLink(controller: 'damageProduct', action: 'save')}",
                type: 'post',
                dataType: "json",
                data: $("#create-form").serialize(),
                success: function (data) {
                    $("#productNameCreate").toggle(500);
                    var table = $('#list-table').DataTable();
                    table.ajax.reload();
                    setTimeout(function() {
                        $.gritter.add({
                            title: data.message
                        });
                    }, 2000);
                },
                failure: function (data) {
                }
            })
        }
    });

    jQuery(function ($) {
        var oTable1 = $('#list-table').dataTable({
//        "sDom": "<'row'<'col-md-4'><'col-md-4'><'col-md-4'f>r>t<'row'<'col-md-4'l><'col-md-4'i><'col-md-4'p>>",
//            "bProcessing": true,
            "bAutoWidth": true,
            "bServerSide": true,
            //"deferLoading": ${totalCount},
            "sAjaxSource": "${g.createLink(controller: 'damageProduct',action: 'list')}",
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                if (aData.DT_RowId == undefined) {
                    return true;
                }
                $('td:eq(5)', nRow).html(getActionButtons(nRow, aData));
                return nRow;
            },
            "aoColumns": [
                null,
                null,
                { "bSortable": false },
                { "bSortable": false },
                { "bSortable": false },
                { "bSortable": false }

            ]
        });
        $('#add-new-btn').click(function (e) {
            $("#productNameCreate").toggle(500);
            $("#productItem").focus();
            $("#create-form")[ 0 ].reset();
            e.preventDefault();
        });

        $('#list-table').on('click', 'a.edit-reference', function (e) {
            var control = this;
            var referenceId = $(control).attr('referenceId');
            jQuery.ajax({
                type: 'POST',
                dataType: 'JSON',
                url: "${g.createLink(controller: 'damageProduct',action: 'edit')}?id=" + referenceId,
                success: function (data, textStatus) {
                    if (data.isError == false) {
                        $('#id').val(data.obj.id);
                        $('#productItem').val(data.product.id);
                        $('#description').val(data.obj.description);
                        $('#productAmount').val(data.obj.productAmount);
//                        $('#datepicker').val(data.obj.damageDate);
                        $('.datepicker').datepicker('setDate', new Date(data.obj.damageDate));
                        $("#productNameCreate").show(500);
                        $("#productItem").focus();
                    } else {
                        setTimeout(function() {
                            $.gritter.add({
                                title: data.message
                            });
                        }, 2000);
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
                    url: "${g.createLink(controller: 'damageProduct',action: 'delete')}?id=" + referenceId,
                    success: function (data, textStatus) {
                        if (data.isError == false) {
                            $("#list-table").DataTable().row(selectRow).remove().draw();
                        } else {
                            setTimeout(function() {
                                $.gritter.add({
                                    title: data.message
                                });
                            }, 2000);
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
        actionButtons += '<sec:access controller="productItem" action="edit"><span class="col-xs-6"><a href="" referenceId="' + aData.DT_RowId + '" class="edit-reference" title="Edit">';
        actionButtons += '<span class="green green fa fa-edit"></span>';
        actionButtons += '&nbsp;Edit&nbsp;</a></span></sec:access>';
        actionButtons += '<sec:access controller="productItem" action="delete"><span class="col-xs-6"><a href="" referenceId="' + aData.DT_RowId + '" class="delete-reference" title="Delete">';
        actionButtons += '<span class="red green fa fa-cut"></span>';
        actionButtons += '&nbsp;Delete&nbsp;</a></span></sec:access>';
        return actionButtons;
    }

</script>

</body>
</html>