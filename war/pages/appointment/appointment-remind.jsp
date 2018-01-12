<%@ page errorPage="../login.do" %>
<%@taglib
	uri="http://www.springframework.org/tags/form"
	prefix="form"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>Welcome To | Appointment Management</title>
    <!-- Favicon-->
    <link rel="icon" href="../../favicon.ico" type="image/x-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">

    <!-- Bootstrap Core Css -->
    <link href="../../plugins/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Waves Effect Css -->
    <link href="../../plugins/node-waves/waves.css" rel="stylesheet" />

    <!-- Animation Css -->
    <link href="../../plugins/animate-css/animate.css" rel="stylesheet" />

    <!-- Custom Css -->
    <link href="../../css/style.css" rel="stylesheet">

    <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
    <link href="../../css/themes/all-themes.css" rel="stylesheet" />
</head>

<body class="theme-purple">
    <!-- Page Loader -->
    <div class="page-loader-wrapper">
        <div class="loader">
            <div class="preloader">
                <div class="spinner-layer pl-purple">
                    <div class="circle-clipper left">
                        <div class="circle"></div>
                    </div>
                    <div class="circle-clipper right">
                        <div class="circle"></div>
                    </div>
                </div>
            </div>
            <p>Please wait...</p>
        </div>
    </div>
    <!-- #END# Page Loader -->
    <!-- Overlay For Sidebars -->
    <div class="overlay"></div>
    <!-- #END# Overlay For Sidebars -->
    <!-- Top Bar -->
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="javascript:void(0);" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false"></a>
                <a href="javascript:void(0);" class="bars"></a>
                <a class="navbar-brand" href="index.html">KASEMRAD SRIBURIN</a>
            </div>
            <div class="collapse navbar-collapse" id="navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="logout.do"> Log Out</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- #Top Bar -->
    <section>
        <!-- Left Sidebar -->
        <aside id="leftsidebar" class="sidebar">
            <!-- User Info -->
            <div class="user-info">
                <div class="info-container">
                    <div class="name" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Da Chonthida</div>
                    <div class="btn-group user-helper-dropdown">
                        <i class="material-icons" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">keyboard_arrow_down</i>
                        <ul class="dropdown-menu pull-right">
                            <li><a href="#editPro" data-toggle="modal"><i class="material-icons">settings</i> Account Settings</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- #User Info -->
            <!-- Menu -->
            <div class="menu">
                <ul class="list">
                    <li class="header">MAIN NAVIGATION</li>
                    <li>
                        <a href="../dashboard.do">
                            <i class="material-icons">home</i>
                            <span>Home</span>
                        </a>
                    </li>

                    <li class="active">
                        <a href="javascript:void(0);" class="menu-toggle">
                            <i class="material-icons">event_note</i>
                            <span>Appointment</span>
                        </a>
                        <ul class="ml-menu">
                            <li>
                                <a href="../appointment.do">Appointment Lists</a>
                            </li>
                            <li class="active">
                                <a href="../appointmentremind.do">Reminding</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="../doctor.do">
                            <i class="material-icons">account_circle</i>
                            <span>Doctors</span>
                        </a>
                    </li>
                    <li>
                        <a href="../patient.do">
                            <i class="material-icons">people</i>
                            <span>Patient</span>
                        </a>
                    </li>
                    <li>
                        <a href="../report.do">
                            <i class="material-icons">view_list</i>
                            <span>Report</span>
                        </a>
                    </li>
                </ul>
            </div>
            <!-- #Menu -->
            <!-- Footer -->
            <div class="legal">
                <div class="copyright">
                    &copy; 2016 <a href="javascript:void(0);">Appointment management</a>.
                </div>
                <div class="version">
                    <b>Version: </b> 1.0
                </div>
            </div>
            <!-- #Footer -->
        </aside>
        <!-- #END# Left Sidebar -->
    </section>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>Appointment Lists</h2>
            </div>

            <!-- Example Tab -->
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                APPOINTMENT REMINDING SETTING
                            </h2>
                        </div>
                        <div class="body">
                            <div class="col-md-4 col-sm-4 col-xs-12">
                                <strong>2 days before appointment date</strong>
                                <h4>At 10:00 AM</h4>
                            </div>
                            <div class="col-md-6 col-sm-6 col-xs-12">
                                <strong>Reminding Message :</strong>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante</p>
                            </div>
                            <div class="col-md-2 col-sm-2 col-xs-12">
                                <div class="pull-right">
                                    <div class="switch">
                                        <label><input type="checkbox" checked><span class="lever switch-col-lime"></span></label>
                                    </div>
                                    <br />
                                    <button class="btn btn-default btn-sm btn-block pull-right" data-toggle="modal" data-target="#editRemind" id="btnEdit">Edit</button>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Example Tab -->
        </div>
    </section>

<%@include file="../../settings.jsp"%>
<form:form  id="loginForm" method="post" action="../login.do" modelAttribute="loginBean">
</form:form>

<!-- Modal -->
<div id="editRemind" class="modal fade" role="dialog">
    <div class="modal-dialog modal-lg">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Update Reminding</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div style="padding-left: 3%">
                        <div class="col-md-6 col-sm-12">
                            <p>
                                Notify <input type="number" style="width: 37px"> days
                                before appointment date
                            </p>
                        </div>
                        <div class="col-md-col-sm-12">
                            <p>
                                At <input type="number" style="width: 42px" placeholder="HH">
                                : <input type="number" style="width: 42px" placeholder="MM">
                            </p>
                        </div>
                        <div class="clearfix"></div><br>
                        <div class="col-md-12 col-xs-12">
                            <strong>Reminding Message</strong>
                            <div class="form-group">
                                <div class="form-line">
                                    <textarea rows="2" id="makeAppointmentInitialSymptoms" class="form-control no-resize" placeholder="Type Reminding Message here..."></textarea>
                                </div>
                            </div>
                        <div class="clearfix"></div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-link waves-effect">SAVE CHANGES</button>
                <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">CLOSE</button>
            </div>
        </div>

    </div>
</div>
<!-- /Modal -->

    <!-- Jquery Core Js -->
    <script src="../../plugins/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="../../plugins/bootstrap/js/bootstrap.js"></script>

    <!-- Select Plugin Js -->
    <script src="../../plugins/bootstrap-select/js/bootstrap-select.js"></script>

    <!-- Slimscroll Plugin Js -->
    <script src="../../plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="../../plugins/node-waves/waves.js"></script>

    <!-- Jquery CountTo Plugin Js -->
    <script src="../../plugins/jquery-countto/jquery.countTo.js"></script>

    <!-- Custom Js -->
    <script src="../../js/admin.js"></script>

    <!-- Demo Js -->
    <script src="../../js/demo.js"></script>
</body>

</html>