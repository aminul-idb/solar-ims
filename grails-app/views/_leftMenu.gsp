<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">
            <li class="nav-header">

                <div class="dropdown profile-element"> <span>
                    %{--<img alt="image" class="img-circle" src="img/profile_small.jpg">--}%
                    <asset:image src="abc.jpg" class="img-thumbnail img-responsive" alt="image"/>
                    </span>
                    <br>

                </div>

            </li>
            <li class="active">
                <a href="index.html"><i class="fa fa-th-large"></i> <span class="nav-label">Settings</span> <span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li><a href="${g.createLink(controller: 'categoryType',action: 'index')}">Product</a></li>
                    %{--<li><a href="${g.createLink(controller: 'subCat',action: 'index')}">Add Sub Category</a></li>--}%
                    <li><a href="${g.createLink(controller: 'productItem',action: 'index')}">Sub Product</a></li>
                </ul>
            </li>

            <li>
                <a href="#"><i class="fa fa-bar-chart-o"></i> <span class="nav-label">Product Manipulation</span><span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li><a href="${g.createLink(controller: 'lcSettings', ction: 'index')}">LC Settings</a></li>
                    <li><a href="${g.createLink(controller: 'import', action: 'index')}">Import Product</a></li>
                    <li><a href="${g.createLink(controller: 'branchDistribution', action: 'index')}">Branch distribution</a></li>
                    <li><a href="${g.createLink(controller: 'productDistribution', action: 'index')}">Product Distribution</a></li>
                    <li><a href="${g.createLink(controller: 'damageProduct', action: 'index')}">Damage Product</a></li>
                    <li><a href="${g.createLink(controller: 'returnNewProduct', action: 'index')}">Return New Product</a></li>
                </ul>
            </li>

            <li>
                <a href="#"><i class="fa fa-bar-chart-o"></i> <span class="nav-label">Search</span><span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li><a href="${g.createLink(controller: 'import', action: 'importSearch')}">Import Product</a></li>
                    <li><a href="${g.createLink(controller: 'productDistribution', action: 'productDistributionSearch')}">Product Distribution</a></li>
                </ul>
            </li>

            <li>
                <a href="#"><i class="fa fa-bar-chart-o"></i> <span class="nav-label">Report</span><span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li><a href="${g.createLink(controller: 'import', action: 'importReport')}">Import Product</a></li>
                    <li><a href="${g.createLink(controller: 'branchDistribution', action: 'branchDistributionReport')}">Branch Distribution</a></li>
                </ul>
            </li>


            %{--<li>
                <a href="mailbox.html"><i class="fa fa-envelope"></i> <span class="nav-label">Mailbox </span><span class="label label-warning pull-right">16/24</span></a>
                <ul class="nav nav-second-level">
                    <li><a href="mailbox.html">Inbox</a></li>
                    <li><a href="mail_detail.html">Email view</a></li>
                    <li><a href="mail_compose.html">Compose email</a></li>
                </ul>
            </li>
            <li>
                <a href="widgets.html"><i class="fa fa-flask"></i> <span class="nav-label">Widgets</span> <span class="label label-info pull-right">NEW</span></a>
            </li>
            <li>
                <a href="#"><i class="fa fa-edit"></i> <span class="nav-label">Forms</span><span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li><a href="form_basic.html">Basic form</a></li>
                    <li><a href="form_advanced.html">Advanced Plugins</a></li>
                    <li><a href="form_wizard.html">Wizard</a></li>
                    <li><a href="form_file_upload.html">File Upload</a></li>
                    <li><a href="form_editors.html">Text Editor</a></li>
                </ul>
            </li>--}%
        </ul>
    </div>
</nav>