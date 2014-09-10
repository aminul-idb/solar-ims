<%@ page import="com.startup.inventory.CategoryType" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Category</title>
    <asset:stylesheet src="formDataTable.css"/>
    <asset:javascript src="formDataTable.js"/>

</head>

<body>

<div class="row wrapper border-bottom white-bg page-heading">

    <div class="col-lg-8">
        <h2>Create Category</h2>
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

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="name" class="control-label">Category</label>
                                                <g:textField class="form-control" id="name" tabindex="1" name="name"
                                                             placeholder="Enter Product Name."/>
                                                <span class="help-block" for="name"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="description" class="control-label">Category Description</label>
                                                <g:textField class="form-control" id="description" tabindex="2"
                                                             name="description" placeholder="Enter Description."/>
                                                <span class="help-block" for="description"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="categoryType" class=" control-label">Product</label><br>
                                                <g:select class="form-control" id="categoryType" name='categoryType'
                                                          noSelection="${['': 'Select Product...']}"
                                                          from='${CategoryType.list()}'
                                                          optionKey="id" optionValue="name"></g:select>
                                                <span class="help-block" for="productItem"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-4">
                                            <div class="col-md-12">
                                                <label for="status" class=" control-label">Status </label><br>
                                                <g:select class="form-control" id="status" name='status'
                                                          from='${com.startup.inventory.Status.values()}'
                                                          optionKey="key" optionValue="value"></g:select>
                                                <span class="help-block" for="status"></span>
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
                <div class="row">
                    <div class="col-sm-12">
                        <section class="panel">

                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table text-center table-striped table-hover table-bordered" id="list-table">
                                        <thead>
                                        <tr>
                                            <th class="text-center">Serial</th>
                                            <th class="text-center">Name</th>
                                            <th class="text-center">Description</th>
                                            <th class="text-center">Status</th>
                                            <th class="text-center">Product</th>
                                            <th class="text-center">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <g:each in="${dataReturn}" var="productItem">
                                            <tr>
                                                <td>${productItem[0]}</td>
                                                <td class="bigFont">${productItem[1]}</td>
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


    $('#create-form').validate({
        errorElement: 'small',
        errorClass: 'help-block',
        focusInvalid: false,
        rules: {
            name: {
                required: true
            },
            categoryType: {
                required: true
            }

        },
        messages: {
            name: {
                required: "Please provide a Name"
            },
            categoryType: {
                required: "Select Product Name"
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
                url: "${createLink(controller: 'productItem', action: 'save')}",
                type: 'post',
                dataType: "json",
                data: $("#create-form").serialize(),
                success: function (data) {
                    clearForm(form);
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
        "sDom": "<'row'<'col-md-4'><'col-md-4'><'col-md-4'f>r>t<'row'<'col-md-4'l><'col-md-4'i><'col-md-4'p>>",
//            "bProcessing": true,
            "bAutoWidth": true,
            "bServerSide": true,
            "deferLoading": ${totalCount},
            "sAjaxSource": "${g.createLink(controller: 'productItem',action: 'list')}",
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                if (aData.DT_RowId == undefined) {
                    return true;
                }
                $('td:eq(1)', nRow).addClass('bigFont');
                $('td:eq(5)', nRow).html(getActionButtons(nRow, aData));
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
                { "bSortable": false }

            ]
        });
        $('#add-new-btn').click(function (e) {
            $("#productNameCreate").toggle(500);
            $("#name").focus();
            e.preventDefault();
        });

        $('#list-table').on('click', 'a.edit-reference', function (e) {
            var control = this;
            var referenceId = $(control).attr('referenceId');
            jQuery.ajax({
                type: 'POST',
                dataType: 'JSON',
                url: "${g.createLink(controller: 'productItem',action: 'edit')}?id=" + referenceId,
                success: function (data, textStatus) {
                    if (data.isError == false) {
                        clearForm('#create-form');
                        $('#id').val(data.obj.id);
                        $('#name').val(data.obj.name);
                        $('#description').val(data.obj.description);
                        $('#status').val(data.obj.status ? data.obj.status.name :'');
                        $('#categoryType').val(data.obj.categoryType.id);
                        $("#productNameCreate").show(500);
                        $("#name").focus();
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
                    url: "${g.createLink(controller: 'productItem',action: 'delete')}?id=" + referenceId,
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