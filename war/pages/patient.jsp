<%@ page errorPage="../login.do" %>
<%@taglib
	uri="http://www.springframework.org/tags/form"
	prefix="form"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>Welcome To | Appointment Management</title>
    <!-- Favicon-->
    <link rel="icon" href="../../favicon.ico" type="image/x-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">

    <!-- Bootstrap Core Css -->
    <link href="../plugins/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Waves Effect Css -->
    <link href="../plugins/node-waves/waves.css" rel="stylesheet" />

    <!-- Animation Css -->
    <link href="../plugins/animate-css/animate.css" rel="stylesheet" />
    
    <!-- Sweetalert Css -->
    <link href="../plugins/sweetalert/sweetalert.css" rel="stylesheet" />

    <!-- JQuery DataTable Css -->
    <link href="../plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.css" rel="stylesheet">

    <!-- Custom Css -->
    <link href="../css/style.css" rel="stylesheet">

    <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
    <link href="../css/themes/all-themes.css" rel="stylesheet" />
</head>

<body class="theme-purple" onload="listActUsers();listDeactUsers()">
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
   <%@ include file="./sidebar.jsp" %>

<section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>
                    USER
                </h2>
            </div>
            <!-- Basic Examples -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="body">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs tab-nav-right tab-col-lime" role="tablist">
                                <li role="presentation" class="active"><a href="#activate" data-toggle="tab">Activiated</a></li>
                                <li role="presentation"><a href="#deactivate" data-toggle="tab">Deactivated</a></li>
                            </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                                <div role="tabpanel" class="tab-pane fade in active" id="activate">
                                    <b>Pending List</b><br /><br />
                                    <table id="datatable_act" class="table table-bordered table-striped table-hover js-basic-example actpatientTable">
                                        <thead>
                                            <tr>
                                                <th>HN</th>
                                                <th>Firstname</th>
                                                <th>Lastname</th>
                                                <th>Contact</th>
                                                <th>Facebook</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                                <div role="tabpanel" class="tab-pane fade" id="deactivate">
                                    <b>Approve List</b><br /><br />
                                    <table id="datatable_dis" class="table table-bordered table-striped table-hover js-basic-example dataTable deactpatientTable">
                                        <thead>
                                            <tr>
                                                <th>HN</th>
                                                <th>Firstname</th>
                                                <th>Lastname</th>
                                                <th>Contact</th>
                                                <th>Facebook</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Basic Examples -->
        </div>
    </section>
    
<%@include file="../settings.jsp"%>
<form:form  id="loginForm" method="post" action="../login.do" modelAttribute="loginBean">
</form:form>


        <!-- Modal -->
            <div id="editPat" tabindex="-1" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Patient</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <center>
                                    <h4>Do you want to save changes?</h4>
                                    <input type="hidden" name="userId" value="" id="userId" />
                                    <input type="hidden" name="action" value="" id="action" />
                                    <div class="clearfix"></div>
                                </center>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-link waves-effect" data-dismiss="modal" onclick="switchUser()">SAVE CHANGES</button>
                            <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">CLOSE</button>
                        </div>
                    </div>

                </div>
            </div>
        <!-- /Modal -->


    <!-- Jquery Core Js -->
    <script src="../plugins/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="../plugins/bootstrap/js/bootstrap.js"></script>

    <!-- Select Plugin Js -->
    <script src="../plugins/bootstrap-select/js/bootstrap-select.js"></script>

    <!-- Slimscroll Plugin Js -->
    <script src="../plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="../plugins/node-waves/waves.js"></script>
    
    <!-- SweetAlert Plugin Js -->
    <script src="../plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Jquery DataTable Plugin Js -->
    <script src="../plugins/jquery-datatable/jquery.dataTables.js"></script>
    <script src="../plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>

    <!-- Custom Js -->
    <script src="../js/admin.js"></script>
    <script src="../js/pages/tables/jquery-datatable.js"></script>
    <script src="../js/pages/ui/dialogs.js"></script>

    <!-- Demo Js -->
    <script src="../js/demo.js"></script>

    <script type="text/javascript">
	function listActUsers() {
		$("#loader").show();
		$
				.ajax({
					url : "../listActivateUserWS.do",
					type : "GET",
					success : function(data) {
						var html = '';
						html += "<thead>";
						html += "<tr>";
						html += "<th>Firstname</th>" 
								+ "<th>Lastname</th>"
								+ "<th>HN</th>"
								+ "<th></th>"
						html += "</tr>";
						html += "</thead>";
						html += "<tbody>";
						for (var i = 0; i < data.length; i++) {
								html += "<tr>";
								html +=  "<td>" + data[i].firstname + "</td>" 
										+ "<td>" + data[i].lastname+ "</td>";
										
								if(data[i].codeHN !=null && data[i].codeHN!="" )		
									html +="<td>" + data[i].codeHN + "</td>" ;
								else
									html +="<td>No Record </td>" ;
								
								html += "<td style=\"width:9%\"><a class=\"btn bg-red btn-block btn-smk\" href=\"#editPat\" role=\"button\" data-toggle=\"modal\" data-todo='{\"userId\":\"" + data[i].keyString + "\",\"action\":\"d\"}'>Deactivate</a> </td>";
								html += "</tr>";
						}
						html += "</tbody>";
						$('.actpatientTable').html(html);
						$('#datatable_act').dataTable();
						$("#loader").hide();
					},
					error : function(data, status, er) {
						//alert('error');
						$("#loader").hide();
					}
				});
	}

	function listDeactUsers() {
		$("#loader").show();
		$
				.ajax({
					url : "../listDeactivateUserWS.do",
					type : "GET",
					success : function(data) {
						var html = '';
						html += "<thead>";
						html += "<tr>";
						html += "<th>HN</th>"
								+ "<th>Firstname</th>" + "<th>Lastname</th>"
								+ "<th>Contact</th>" + "<th>Facebook</th>"
								+ "<th></th>"
						html += "</tr>";
						html += "</thead>";
						html += "<tbody>";
						for (var i = 0; i < data.length; i++) {
								html += "<tr>";
								html += "<td>" + data[i].hospitalNumber
										+ "</td>" + "<td>" + data[i].firstname
										+ "</td>" + "<td>" + data[i].lastname
										+ "</td>" + "<td>" + data[i].contact
										+ "</td>";
								if (data[i].facebookId == null
										|| data[i].facebookId == "null") {
									html += "<td> - </td>";
								} else {
									html += "<td>" + data[i].facebookId
											+ "</td>";
								}
								html += "<td style=\"width:7%\"><a class=\"btn bg-lime btn-block btn-sm\" href=\"#editPat\" role=\"button\" data-toggle=\"modal\" data-todo='{\"userId\":\"" + data[i].keyString + "\",\"action\":\"a\"}'>Activate</a> </td>"
								html += "</tr>";
						}
						html += "</tbody>";
						$('.deactpatientTable').html(html);
						$('#datatable_dis').dataTable();
						$("#loader").hide();
					},
					error : function(data, status, er) {
						//alert('error');
						$("#loader").hide();
					}
				});
	}

	function switchUser() {
		$("#loader").show();

		if($('#action').val() == 'a'){
			$.ajax({
				url : "../activateUserWS.do?userKey="+$('#userId').val(),
				type : "GET",
				dataType : "JSON",
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					//alert('success');
					successalert();
					$("#loader").hide();
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});
		} else if($('#action').val() == 'd'){
			$.ajax({
				url : "../deactivateUserWS.do?userKey="+$('#userId').val(),
				type : "GET",
				dataType : "JSON",
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					//alert('success');
					successalert();
					$("#loader").hide();
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});
		}
	}
	
	function successalert(){
		swal("Success", "Your request has been done!", "success");
		setTimeout(function(){ 
			listActUsers();
			listDeactUsers();
		}, 3000);
	}

</script>

	<script>
		$('#editPat').on('show.bs.modal', function(e) {
			var id = $(e.relatedTarget).data('todo').userId;
			var action = $(e.relatedTarget).data('todo').action;
			$("#userId").val(id);
			$("#action").val(action);
		});
	</script>
</body>

</html>