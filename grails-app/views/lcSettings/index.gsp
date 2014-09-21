<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>LC Settings</title>
    <asset:stylesheet src="formDataTable.css"/>
    <asset:javascript src="formDataTable.js"/>
    <asset:javascript src="formDataTable.js"/>
    <asset:stylesheet src="datepicker.css"/>
    <asset:javascript src="bootstrap-datepicker.js"/>

</head>

<body>

<div class="row wrapper border-bottom white-bg page-heading">

    <div class="col-lg-8">
        <h2>LC Settings</h2>
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
                                    <form class="cmxform form-horizontal " id="create-form">
                                        <g:hiddenField name="id"/>
                                        <input type="hidden" name="edit" value="" id="edit" />

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="lcNo" class="control-label">LC No.</label>
                                                <g:textField class="form-control" id="lcNo" tabindex="1" name="lcNo"
                                                             placeholder="Enter LC."/>
                                                <span for="lcNo" class="help-block"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="datepicker" class="control-label">LC Date</label>
                                                <input type="text" required="required" class="form-control datepicker" id="datepicker"
                                                       name="lcDate" placeholder="Enter LC Date."/>
                                                <span class="help-block" for="datepicker"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="description" class="control-label">Lc Description</label>
                                                <g:textField class="form-control" id="description"
                                                             name="description" placeholder="Enter Description."/>
                                                <span class="help-block" for="description"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="status" class=" control-label">Status </label><br>
                                                <g:select class="form-control" id="status" name='status'
                                                          from='${com.startup.inventory.Status.values()}'
                                                          optionKey="key" optionValue="value"></g:select> %{--noSelection="${['': 'Select One...']}"--}%
                                                <span class="help-block" for="status"></span>
                                            </div>
                                        </div>

                                        <div class="form-group col-md-11">
                                            <div class="col-md-offset-10 col-md-4">
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
                                    <table class="table table-striped table-hover table-bordered text-center" id="list-table">
                                        <thead>
                                        <tr>
                                            <th class="text-center">Serial</th>
                                            <th class="text-center">LC No</th>
                                            <th class="text-center">LC Description</th>
                                            <th class="text-center">Date</th>
                                            <th class="text-center">Status</th>
                                            <th class="text-center">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <g:each in="${dataReturn}" var="animated">
                                            <tr>
                                                <td>${animated[0]}</td>
                                                <td>${animated[1]}</td>
                                                <td>${animated[2]}</td>
                                                <td>${animated[3]}</td>
                                                <td>${animated[4]}</td>
                                                <td>
                                                    <sec:access controller="import" action="edit">
                                                        <span class="col-xs-6"><a href=""
                                                                                  referenceId="${animated.DT_RowId}"
                                                                                  class="edit-reference"
                                                                                  title="Edit"><span
                                                                    class="green fa fa-edit"></span>&nbsp;Edit&nbsp;</a>
                                                        </span>
                                                    </sec:access>
                                                    <sec:access controller="import" action="delete">
                                                        <span class="col-xs-6"><a href=""
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


    $('#create-form').validate({
        errorElement: 'small',
        errorClass: 'help-block',
        focusInvalid: false,
        rules: {
            lcNo: {
                required: true
            },
            lcDate: {
                required: true
            }
        },
        messages: {
            lcNo: {
                required: " "
            },
            lcDate: {
                required: " "
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
                url: "${createLink(controller: 'lcSettings', action: 'save')}",
                type: 'post',
                dataType: "json",
                data: $("#create-form").serialize(),
                success: function (data) {
                    clearForm(form);
                    var table = $('#list-table').DataTable();
                    table.ajax.reload();
                    $("#animportCreate").toggle(500);
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
            "deferLoading": ${totalCount},
            "sAjaxSource": "${g.createLink(controller: 'lcSettings',action: 'list')}",
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                if (aData.DT_RowId == undefined) {
                    return true;
                }
                $('td:eq(5)', nRow).html(getActionButtons(nRow, aData));
                return nRow;
            },
            "aoColumns": [
                {"sClass": "center", "sWidth": "10%", "bSortable": true },
                {"sClass": "center", "sWidth": "15%", "bSortable": true },
                {"sClass": "center", "sWidth": "20%", "bSortable": false },
                {"sClass": "center", "sWidth": "15%", "bSortable": false },
                {"sClass": "center", "sWidth": "15%", "bSortable": false },
                {"sClass": "center", "sWidth": "20%", "bSortable": false }

            ]
        });

        $('#add-new-btn').click(function (e) {
            $("#animportCreate").toggle(500);
            $("#lcNo").focus();
            e.preventDefault();
        });

        $('#list-table').on('click', 'a.edit-reference', function (e) {
            var control = this;
            var referenceId = $(control).attr('referenceId');
            jQuery.ajax({
                type: 'POST',
                dataType: 'JSON',
                url: "${g.createLink(controller: 'lcSettings',action: 'edit')}?id=" + referenceId,
                success: function (data, textStatus) {
                    if (data.isError == false) {
                        clearForm('#create-form');
                        $('#id').val(data.obj.id);
                        $('#lcNo').val(data.obj.lcNo);
                        $('#edit').val("edit");
                        //$('#productItem').val(data.obj.productItem.id);
                        $('#datepicker').datepicker('setDate', new Date(data.obj.lcDate));
                        $('#description').val(data.obj.description);
                        $('#status').val(data.obj.status ? data.obj.status.name :'');
                        $("#animportCreate").show(500);
                        $("#lcNo").focus();
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
                    url: "${g.createLink(controller: 'lcSettings',action: 'delete')}?id=" + referenceId,
                    success: function (data, textStatus) {
                        if (data.isError == false) {
                            $("#list-table").DataTable().row(selectRow).remove().draw();
                            setTimeout(function() {
                                $.gritter.add({
                                    title: data.message
                                });
                            }, 2000);
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
        actionButtons += '<sec:access controller="lcSettings" action="edit"><span class="col-xs-6"><a href="" referenceId="' + aData.DT_RowId + '" class="edit-reference" title="Edit">';
        actionButtons += '<span class="green green fa fa-edit"></span>';
        actionButtons += '&nbsp;Edit&nbsp;</a></span></sec:access>';
        actionButtons += '<sec:access controller="lcSettings" action="delete"><span class="col-xs-6"><a href="" referenceId="' + aData.DT_RowId + '" class="delete-reference" title="Delete">';
        actionButtons += '<span class="red green fa fa-cut"></span>';
        actionButtons += '&nbsp;Delete&nbsp;</a></span></sec:access>';
        return actionButtons;
    }

</script>

</body>
</html>