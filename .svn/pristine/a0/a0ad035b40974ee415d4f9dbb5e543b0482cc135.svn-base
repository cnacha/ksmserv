<%@ page errorPage="../login.do"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title>Welcome To | Appointment Management</title>
<!-- Favicon-->
<link rel="icon" href="../favicon.ico" type="image/x-icon">

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext"
	rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet" type="text/css">

<!-- Bootstrap Core Css -->
<link href="../plugins/bootstrap/css/bootstrap.css" rel="stylesheet">

<!-- Waves Effect Css -->
<link href="../plugins/node-waves/waves.css" rel="stylesheet" />

<!-- Animation Css -->
<link href="../plugins/animate-css/animate.css" rel="stylesheet" />

<!-- Wait Me Css -->
<link href="../plugins/waitme/waitMe.css" rel="stylesheet" />

<!-- Bootstrap Select Css -->
<link href="../plugins/bootstrap-select/css/bootstrap-select.css"
	rel="stylesheet" />

<!-- Custom Css -->
<link href="../css/style.css" rel="stylesheet">

<!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
<link href="../css/themes/all-themes.css" rel="stylesheet" />
</head>

<body class="theme-purple" onload="intialDate();listCancel();">
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
				<a href="javascript:void(0);" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar-collapse"
					aria-expanded="false"></a> <a href="javascript:void(0);"
					class="bars"></a> <a class="navbar-brand" href="../dashboard.do">KASEMRAD
					SRIBURIN</a>
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
					<div class="name" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false">Da Chonthida</div>
					<div class="btn-group user-helper-dropdown">
						<i class="material-icons" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="true">keyboard_arrow_down</i>
						<ul class="dropdown-menu pull-right">
							<li><a href="#editPro" data-toggle="modal"><i
									class="material-icons">settings</i> Account Settings</a></li>
						</ul>
					</div>
				</div>
			</div>
			<!-- #User Info -->
			<!-- Menu -->
			<div class="menu">
				<ul class="list">
					<li class="header">MAIN NAVIGATION</li>
					<li><a href="../dashboard.do"> <i class="material-icons">home</i>
							<span>Home</span>
					</a></li>

					<li><a href="javascript:void(0);" class="menu-toggle"> <i
							class="material-icons">event_note</i> <span>Appointment</span>
					</a>
						<ul class="ml-menu">
							<li><a href="../appointment.do">Appointment Lists</a></li>
							<li><a href="../appointmentremind.do">Reminding</a></li>
						</ul></li>
					<li><a href="../doctor.do"> <i class="material-icons">account_circle</i>
							<span>Doctors</span>
					</a></li>
					<li><a href="../patient.do"> <i class="material-icons">people</i>
							<span>Patient</span>
					</a></li>
					<li class="active"><a href="../report.do"> <i
							class="material-icons">view_list</i> <span>Report</span>
					</a></li>
				</ul>
			</div>
			<!-- #Menu -->
			<!-- Footer -->
			<div class="legal">
				<div class="copyright">
					&copy; 2016 <a href="javascript:void(0);">Appointment
						management</a>.
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
				<h2>REPORT</h2>
			</div>
			<!-- Basic Examples -->
			<div class="row clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="card">
						<div class="body">
							<!-- Nav tabs -->
							<ul class="nav nav-tabs tab-nav-right tab-col-lime"
								role="tablist">
								<li role="presentation" class="active"><a href="#index"
									data-toggle="tab">Appointment Cancellation Report</a></li>
							</ul>

							<!-- Tab panes -->
							<div class="tab-content">
								<div role="tabpanel" class="tab-pane fade in active" id="index">

									<!--date-->
									<div class="col-sm-4">
										<div class="form-group">
											<div class="form-line">
												<input type="date" class="datepicker form-control"
													onchange="listCancelPick();" id="datePick" name="datePick">
											</div>
										</div>
									</div>
									<div class="clearfix"></div>

									<h4 id="nowdate"></h4>
									<br>
									<table class="cancelTable table table-striped">
										<thead>
											<tr>
												<th>#</th>
												<th>Patient's HN</th>
												<th>Patient's Name</th>
												<th>Contact</th>
											</tr>
										</thead>
									</table>
									<a href="#"><button class="btn bg-lime">Save to
											PDF</button></a>
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
	<form:form id="loginForm" method="post" action="../login.do"
		modelAttribute="loginBean">
	</form:form>


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

	<!-- Autosize Plugin Js -->
	<script src="../plugins/autosize/autosize.js"></script>

	<!-- Moment Plugin Js -->
	<script src="../plugins/momentjs/moment.js"></script>

	<!-- Custom Js -->
	<script src="../js/admin.js"></script>
	<script src="../js/pages/forms/basic-form-elements.js"></script>

	<!-- Demo Js -->
	<script src="../js/demo.js"></script>

	<script>
		var currentPickDate = '';

		function intialDate() {
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth() + 1; //January is 0!
			var yyyy = today.getFullYear();

			if (dd < 10) {
				dd = '0' + dd
			}

			if (mm < 10) {
				mm = '0' + mm
			}

			today = yyyy + '-' + mm + '-' + dd;

			currentPickDate = today.toString();

			$('#datePick').val(today);

		}

		function listCancelPick() {
			var newCurrentDate = $('#datePick').val().toString();
			currentPickDate = newCurrentDate;
			$("#loader").show();
			$
					.ajax({
						url : "../listAppointmentCancellationOnSpecificDateWS.do?date="
								+ currentPickDate,
						type : "GET",
						success : function(data) {

							var html = '';
							html += "<thead>";
							html += "<tr>";
							html += "<th>#</th>" + "<th>Patient's HN</th>"
									+ "<th>Patient's Name</th>"
									+ "<th>Contact</th>"
							html += "</tr>";
							html += "</thead>";
							html += "<tbody>";
							var count = 1;
							for (var i = 0; i < data.length; i++) {
								html += "<tr>";
								html += "<td>" + count + "</td>" + "<td>"
										+ data[i].hospitalNumber + "</td>"
										+ "<td>" + data[i].patientFirstname
										+ " " + data[i].patientLastname
										+ "</td>" + "<td>" + data[i].contact
										+ "</td>"
								html += "</tr>";
								count++;
							}
							if ((count - 1) == 0) {
								html += "<tr>";
								html += "<td colspan=\"4\"><center> No cancelled appointment </center></td>"
								html += "</tr>";
							}
							html += "</tbody>";
							$('.cancelTable').html(html);
							$("#count").text(
									"Total " + (count - 1) + " persons");
							$("#nowdate").text(
									"Cancelled appointment list on "
											+ currentPickDate);
							$("#loader").hide();
						},
						error : function(data, status, er) {
							//alert('error');
							$("#loader").hide();
						}
					});
		}

		function listCancel() {
			$("#loader").show();
			$
					.ajax({
						url : "../listAppointmentCancellationOnSpecificDateWS.do?date="
								+ currentPickDate,
						type : "GET",
						success : function(data) {

							var html = '';
							html += "<thead>";
							html += "<tr>";
							html += "<th>#</th>" + "<th>Patient's HN</th>"
									+ "<th>Patient's Name</th>"
									+ "<th>Contact</th>"
							html += "</tr>";
							html += "</thead>";
							html += "<tbody>";
							var count = 1;
							for (var i = 0; i < data.length; i++) {
								html += "<tr>";
								html += "<td>" + count + "</td>" + "<td>"
										+ data[i].hospitalNumber + "</td>"
										+ "<td>" + data[i].patientFirstname
										+ " " + data[i].patientLastname
										+ "</td>" + "<td>" + data[i].contact
										+ "</td>"
								html += "</tr>";
								count++;
							}
							if ((count - 1) == 0) {
								html += "<tr>";
								html += "<td colspan=\"4\"><center> No cancelled appointment </center></td>"
								html += "</tr>";
							}
							html += "</tbody>";
							$('.cancelTable').html(html);
							$("#count").text(
									"Total " + (count - 1) + " persons");
							$("#nowdate").text(
									"Cancelled appointment list on "
											+ currentPickDate);
							$("#loader").hide();
						},
						error : function(data, status, er) {
							//alert('error');
							$("#loader").hide();
						}
					});
		}
	</script>
</body>
</html>