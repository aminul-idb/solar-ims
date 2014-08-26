<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Import Product Report</title>
    <asset:stylesheet src="formDataTable.css"/>
    <asset:javascript src="formDataTable.js"/>
    <asset:javascript src="bootstrap-datepicker.min.js"/>

</head>

<body>

<div class="row wrapper border-bottom white-bg page-heading">

    <div class="col-lg-8">
        <h2>Import Product Report</h2>
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
                                    <form class="cmxform form-horizontal" action="${createLink(controller: 'import', action: 'importReport')}" id="create-form-product">
                                        <g:hiddenField name="id"/>

                                        <div class="form-group col-md-3">
                                            <div class="col-md-12">
                                                <label for="lcSettings" class="control-label">LC No.</label>

                                                <select name="lcNo" id="lcSettings" class="form-control lcSettings">
                                                    <option value="">Select Lc</option>
                                                    <g:each in="${com.startup.inventory.LcSettings.list()}" var="lcSettings" >
                                                        <option value="${lcSettings.lcNo}">${lcSettings.lcNo}</option>
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

</body>
</html>