<%@ page errorPage="../login.do"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title>Welcome To | Appointment Management</title>
<!-- Favicon-->
<link rel="icon" href="favicon.ico" type="image/x-icon">

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext"
	rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet" type="text/css">

<!-- Bootstrap Core Css -->
<link href="plugins/bootstrap/css/bootstrap.css" rel="stylesheet">

<!-- Waves Effect Css -->
<link href="plugins/node-waves/waves.css" rel="stylesheet" />

<!-- Animation Css -->
<link href="plugins/animate-css/animate.css" rel="stylesheet" />

<!-- Custom Css -->
<link href="css/style.css" rel="stylesheet">

<!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
<link href="css/themes/all-themes.css" rel="stylesheet" />
</head>

<body class="theme-purple"
	onload="sumApproveAppointments();sumAppointments();sumPendingAppointments();sumPatients();sumDoctors();sumCancel();">
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
	<%@ include file="./pages/sidebar.jsp" %>

	<section class="content">
		<div class="container-fluid">
			<div class="block-header">
				<h2>DASHBOARD</h2>
			</div>

			<!-- Widgets -->
			<div class="row clearfix">
				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
					<div class="info-box bg-cyan hover-expand-effect">
						<div class="icon">
							<i class="material-icons">subject</i>
						</div>
						<div class="content">
							<div class="text">Total Appointments</div>
							<h3 id="totalAppoint" style="color: #fff; margin-top: 2px"></h3>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
					<div class="info-box bg-orange hover-expand-effect">
						<div class="icon">
							<i class="material-icons">update</i>
						</div>
						<div class="content">
							<div class="text">Total Pending Appointments</div>
							<h3 id="totalPending" style="color: #fff; margin-top: 2px"></h3>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
					<div class="info-box bg-light-green hover-expand-effect">
						<div class="icon">
							<i class="material-icons">playlist_add_check</i>
						</div>
						<div class="content">
							<div class="text">Total Approval Appointments</div>
							<h3 id="totalApprove" style="color: #fff; margin-top: 2px"></h3>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
					<div class="info-box bg-teal hover-expand-effect">
						<div class="icon">
							<i class="material-icons">people</i>
						</div>
						<div class="content">
							<div class="text">Total Users</div>
							<h3 id="totalUser" style="color: #fff; margin-top: 2px"></h3>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
					<div class="info-box bg-deep-purple hover-expand-effect">
						<div class="icon">
							<i class="material-icons">person_add</i>
						</div>
						<div class="content">
							<div class="text">Total Doctors</div>
							<h3 id="totalDoctor" style="color: #fff; margin-top: 2px"></h3>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
					<div class="info-box bg-red hover-expand-effect">
						<div class="icon">
							<i class="material-icons">not_interested</i>
						</div>
						<div class="content">
							<div class="text">Today cancellation</div>
							<h3 id="totalCancel" style="color: #fff; margin-top: 2px"></h3>
						</div>
					</div>
				</div>
			</div>
			<!-- #END# Widgets -->
		</div>
	</section>


	<%@include file="settings.jsp"%>
	<form:form id="loginForm" method="post" action="../login.do"
		modelAttribute="loginBean">
	</form:form>

	<!-- Jquery Core Js -->
	<script src="plugins/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core Js -->
	<script src="plugins/bootstrap/js/bootstrap.js"></script>

	<!-- Select Plugin Js -->
	<script src="plugins/bootstrap-select/js/bootstrap-select.js"></script>

	<!-- Slimscroll Plugin Js -->
	<script src="plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

	<!-- Waves Effect Plugin Js -->
	<script src="plugins/node-waves/waves.js"></script>

	<!-- Jquery CountTo Plugin Js -->
	<script src="plugins/jquery-countto/jquery.countTo.js"></script>

	<!-- Custom Js -->
	<script src="js/admin.js"></script>

	<!-- Demo Js -->
	<script src="js/demo.js"></script>

	<script>
		var pend = '';
		var approve = '';
		function sumApproveAppointments() {
			$("#loader").show();
			$.ajax({
				url : "listApproveAppointmentWS.do",
				type : "GET",
				success : function(data) {
					$("#totalApprove").text(data.length);
					approve = data.length;
					sumAppointments();
					$("#loader").hide();
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});
		}

		function sumPendingAppointments() {
			$("#loader").show();
			$.ajax({
				url : "listPendingAppointmentWS.do",
				type : "GET",
				success : function(data) {
					$("#totalPending").text(data.length);
					pend = data.length;
					sumAppointments();
					$("#loader").hide();
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});
		}

		function sumAppointments() {
			var totalApp = pend + approve;
			$("#totalAppoint").text(totalApp);
		}

		function sumPatients() {
			$("#loader").show();
			$.ajax({
				url : "listActivateUserWS.do",
				type : "GET",
				success : function(data) {
					$("#totalUser").text(data.length);
					$("#loader").hide();
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});
		}

		function sumDoctors() {
			$("#loader").show();
			$.ajax({
				url : "listDoctorWS.do",
				type : "GET",
				success : function(data) {
					$("#totalDoctor").text(data.length);
					$("#loader").hide();
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});
		}

		function sumCancel() {
			$("#loader").show();
			$.ajax({
				url : "listCancelAppointmentWS.do",
				type : "GET",
				success : function(data) {
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

					var currentDate = today.toString();
					var count = 0;
					for (var i = 0; i < data.length; i++) {
						if (data[i].preferChoice == 1) {
							if (data[i].preferDate1 == currentDate) {
								count++;
							}
						} else {
							if (data[i].preferDate2 == currentDate) {
								count++;
							}
						}
					}
					$("#totalCancel").text(count);
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