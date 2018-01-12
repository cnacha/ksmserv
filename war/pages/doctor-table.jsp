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

<!-- Sweetalert Css -->
<link href="../plugins/sweetalert/sweetalert.css" rel="stylesheet" />
    
<!-- Wait Me Css -->
<link href="../plugins/waitme/waitMe.css" rel="stylesheet" />


<!-- JQuery DataTable Css -->
<link
	href="../plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.css"
	rel="stylesheet">

<!-- Custom Css -->
<link href="../css/style.css" rel="stylesheet">

<!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
<link href="../css/themes/all-themes.css" rel="stylesheet" />
</head>

<body class="theme-purple"
	onload="intialDate();listAppointmentPick();storeDoctorInfo();listPatients();">
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
					<span id="doctorName"></span>'s appointment list
				</h2>
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
									data-toggle="tab">Appointment List</a></li>
							</ul>

							<!-- Tab panes -->
							<div class="tab-content">
								<div role="tabpanel" class="tab-pane fade in active" id="index">

									<button class="btn btn-info pull-right" data-toggle="modal"
										data-target="#addApp">
										<span class="fa fa-plus" style="color: white"></span> New
										Appointment
									</button>
									<div class="col-sm-4">
										<div class="form-group">
											<div class="form-line">
												<input type="date" name="datePick"
													onchange="listAppointmentPick();" id="datePick"
													class="datepicker form-control">
											</div>
										</div>
									</div>
									<div class="clearfix"></div>
									<table id="appointmentTable"
										class="table table-bordered table-striped table-hover js-basic-example dataTable appointmentTable">
										<thead>
											<tr>
												<th>Date</th>
												<th>Time</th>
												<th>Patient's HN</th>
												<th>Specialty</th>
												<th></th>
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
	<form:form id="loginForm" method="post" action="../login.do"
		modelAttribute="loginBean">
	</form:form>

	<!-- Modal -->
	<div id="editApp" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Edit an appointment</h4>
				</div>
				<div class="modal-body">
					<form>
						<h4>Appointment information</h4>
						<hr>
						<div class="form-group">
							<div class="form-line">
								<input type="date" name="editAppointmentDate"
									id="editAppointmentDate" class="datepicker form-control"
									onchange="listPreferTimeEdit()">
							</div>
						</div>
						<div class="form-group">
							<label style="padding-left: 0px"
								class="col-md-2 col-sm-2 col-xs-12">Time <span
								class="required">*</span>
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<select class="form-control listTime2" id="editAppointmentTime"
									style="color: #73879c; font-size: 98%">
									<option>08:00 - 08:30</option>
									<option>08:30 - 09:00</option>
									<option>09:00 - 09:30</option>
									<option>09:30 - 10:00</option>
									<option>10:00 - 10:30</option>
									<option>10:30 - 11:00</option>
									<option>11:00 - 11:30</option>
									<option>11:30 - 12:00</option>
									<option>12:00 - 12:30</option>
									<option>12:30 - 13:00</option>
									<option>13:00 - 13:30</option>
									<option>13:30 - 14:00</option>
									<option>14:00 - 14:30</option>
									<option>14:30 - 15:00</option>
									<option>15:00 - 15:30</option>
									<option>15:30 - 16:00</option>
								</select>
								<h6>* Show only available times</h6>
							</div>
						</div>
						<div class="clearfix"></div>
						<br>
						<h4>Patient information</h4>
						<hr>

						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12">Hospital
								Number </label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<p style="padding-top: 9px" id="hospitalNumber">1234</p>
							</div>
						</div>
						<div class="clearfix"></div>
						<br>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12">Name</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<p style="padding-top: 9px" id="name">Name</p>
							</div>
						</div>
						<input type="hidden" name="appointmentId" value=""
							id="appointmentId" /> <input type="hidden" name="patientId"
							value="" id="patientId" /> <input type="hidden" name="doctorId"
							value="" id="doctorId" /> <input type="hidden" name="status"
							value="" id="status" /> <input type="hidden" name="preferDate1"
							value="" id="preferDate1" /><input type="hidden" name="preferDate2"
							value="" id="preferDate2" /> <input type="hidden" name="preferTime1"
							value="" id="preferTime1" /><input type="hidden" name="preferTime2"
							value="" id="preferTime2" />
					</form>
					<div class="clearfix"></div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-link waves-effect"
						name="btnSubmitEditAppointment" id="btnSubmitEditAppointment"
						onclick="updateAppointment()" data-dismiss="modal">SAVE CHANGES</button>
					<button type="button" class="btn btn-link waves-effect"
						data-dismiss="modal">CLOSE</button>
				</div>
			</div>

		</div>
	</div>
	<!-- /Modal -->

	<!-- Modal -->
	<div id="addApp" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Make an appointment</h4>
				</div>
				<div class="modal-body">
					<form>
						<h4>Appointment information</h4>
						<hr>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12">Specialty
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<p id="makeAppointmentSpecialty" style="padding-top: 9px"></p>
							</div>
						</div>
						<div class="clearfix"></div>
						<br>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12">Requested
								Doctor</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<p id="makeAppointmentDoctorName" style="padding-top: 9px"></p>
							</div>
						</div>
						<div class="clearfix"></div>
						<br>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12">Date
								<span class="required">*</span>
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<input type="date" name="makeAppointmnetDate"
									id="makeAppointmnetDate" class="datepicker form-control"
									onchange="listPreferTime()">
							</div>
						</div>
						<div class="clearfix"></div>
						<br>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12">Time
								<span class="required">*</span>
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<select id="makeAppointmentTime" class="form-control listTime"
									style="color: #73879c; font-size: 98%">
									<option disabled selected>Choose date first...</option>

								</select>
								<h6>* Show only available times</h6>
							</div>
						</div>
						<div class="clearfix"></div>
						<br>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12">Initial
								symptoms </label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<div class="form-group">
									<div class="form-line">
										<textarea rows="3" id="makeAppointmentInitialSymptoms"
											class="form-control no-resize"
											placeholder="Please initial symptoms..."></textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<h4>Patient information</h4>
						<hr>

						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12">Hospital
								Number (HN)<span class="required">*</span>
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<input list="patients" id="makeAppointmentPatient"
									class="form-control" required="required"
									style="border-bottom: 1px solid #D5D5D5;">
								<datalist id="patients" class="listPatient">
									<option></option>
								</datalist>
							</div>
						</div>
					</form>
					<div class="clearfix"></div>
				</div>
				<div class="modal-footer">
					<a  class="btn btn-link waves-effect"
						name="btnSubmitCreateAppointment" id="btnSubmitCreateAppointment" onclick="createAppointment();" data-dismiss="modal">SUBMIT</a>
					<button type="button" class="btn btn-link waves-effect"
						data-dismiss="modal">CLOSE</button>
				</div>
			</div>

		</div>
	</div>
	<!-- /Modal -->

	
	<!-- Jquery Core Js -->
	<script src="../plugins/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core Js -->
	<script src="../plugins/bootstrap/js/bootstrap.js"></script>


	<!-- Slimscroll Plugin Js -->
	<script src="../plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

	<!-- Waves Effect Plugin Js -->
	<script src="../plugins/node-waves/waves.js"></script>

	<!-- Autosize Plugin Js -->
	<script src="../plugins/autosize/autosize.js"></script>
	
	<!-- SweetAlert Plugin Js -->
    <script src="../plugins/sweetalert/sweetalert.min.js"></script>

	<!-- Moment Plugin Js -->
	<script src="../plugins/momentjs/moment.js"></script>


	<!-- Jquery DataTable Plugin Js -->
	<script src="../plugins/jquery-datatable/jquery.dataTables.js"></script>
	<script
		src="../plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>

	<!-- Custom Js -->
	<script src="../js/admin.js"></script>
	<script src="../js/pages/tables/jquery-datatable.js"></script>
	<script src="../js/pages/forms/basic-form-elements.js"></script>
	<script src="../js/pages/ui/dialogs.js"></script>

	<!-- Demo Js -->
	<script src="../js/demo.js"></script>

	<script type="text/javascript">
		var checkSubmitDisabled = false;
		$(document).ready(function() {
			$('#btnSubmitCreateAppointment').prop('disabled', 'disabled');

		});

		$(document).ready(function() {
			$('#btnSubmitEditAppointment').prop('disabled', 'disabled');

		});

		function storeDoctorInfo() {

			var doctorId = readCookie('doctor');

			$.ajax({
				url : "../findDoctorById.do?doctorId=" + doctorId,
				type : "GET",
				dataType : "JSON",
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					writeCookie("doctoerFirstname", data.firstname, 1);
					writeCookie("doctorLastname", data.lastname, 1);
					writeCookie("doctorSpecialty", data.specialty, 1);

					$("#doctorName").html(
							readCookie("doctoerFirstname") + " "
									+ readCookie("doctorLastname"));
					$("#makeAppointmentSpecialty").html(
							readCookie("doctorSpecialty"));
					$("#makeAppointmentDoctorName").html(
							readCookie("doctoerFirstname") + " "
									+ readCookie("doctorLastname"));
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});

		}

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
		
		function successalert(){
			swal("Success", "Your request has been done!", "success");
			setTimeout(function(){ 
				listAppointmentPick();
			}, 3000);
		}
		
		function onclickCancelAppointment(appointmentId,preferDate,doctorId,patientId,status,preferChoice,preferTime,preferDate2,preferTime2) {
			swal({
				  title: "Are you sure?",
				  text: "You will not be able to recover",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonColor: "#DD6B55",
				  confirmButtonText: "Yes, cancel it!",
				  closeOnConfirm: false,
				  html: false
				}, function(){
					removeAppointment(appointmentId,preferDate,doctorId,patientId,status,preferChoice,preferTime,preferDate2,preferTime2);
				  swal("Canceled!",
				  "This appointment has been canceled.",
				  "success");
				  setTimeout(function(){ 
						listAppointmentPick();
					}, 3000);
				  
				});
		}

		function listAppointmentPick() {
			var doctorId = readCookie('doctor');
			var newCurrentDate = $('#datePick').val().toString();
			currentPickDate = newCurrentDate;
			$("#loader").show();
			$
					.ajax({
						url : "../listAppointmentByDoctorAndDateIdWS.do?doctorId="
								+ doctorId
								+ "&date="
								+ newCurrentDate,
						type : "GET",
						dataType : "JSON",
						contentType : "application/json",
						mimeType : "application/json",
						success : function(data) {

							var html = '';
							html += "<thead>";
							html += "<tr>";
							html +=  "<th>Start Time</th>"
									+ "<th>End Time</th>" + "<th>Patient's HN</th>"
									+ "<th>Patient Name</th>"
									+ "<th>Specialty</th>" + "<th>&nbsp;</th>"
									+ "<th>&nbsp;</th>"
							html += "</tr>";
							html += "</thead>";
							html += "<tbody>";
							for (var i = 0; i < data.length; i++) {
								if (data[i].status == 1) {

									html += "<tr>";
									html +=
											 "<td>"
											+ data[i].startDateTime
											+ "</td>"
											+ "<td>"
											+ data[i].endDateTime
											+ "</td>"
											+ "<td>"
											+ data[i].patient.codeHN
											+ "</td>"
											+ "<td>"
											+ data[i].patient.firstname  + " " + data[i].patient.lastname
											+ "</td>"
											+ "<td>"
											+ data[i].specialty
											+ "</td>"
											+ "<td style=\"width:10%\"><a role=\"button\" class=\"btn btn-default btn-sm btn-block\" data-toggle=\"modal\" href=\"#editApp\" data-todo='{\"appointmentId\":\"" + data[i].keyString + "\",\"preferDate\":\"" + data[i].preferDate1 + "\",\"doctorId\":\"" + data[i].doctorId + "\",\"patientId\":\"" + data[i].patientId + "\",\"status\":\"1\",\"hn\":\""+data[i].patient.codeHN+"\",\"name\":\"" + data[i].patient.firstname + " " + data[i].patient.lastname +"\",\"time\":\""+data[i].preferTime1+"\",\"preferChoice\":\""+data[i].preferChoice+"\",\"preferDate2\":\""+data[i].preferDate2+"\",\"time2\":\""+data[i].preferTime2+"\"}'>Edit</a></td>"
											+ "<td style=\"width:10%\"><a class=\"btn bg-red btn-block btn-sm\" href=\"#\" onclick=\"onclickCancelAppointment('" + data[i].keyString + "','" + data[i].preferDate1 + "','" + data[i].doctorId + "','" + data[i].patientId + "','3','" + data[i].preferChoice + "','" + data[i].preferTime1 + "','" + data[i].preferDate2 + "','" + data[i].preferTime2 + "')\">Cancel</a></td>";
									html += "</tr>";
								}
							}
							html += "</tbody>";
							//alert(html);
							$('.appointmentTable').html(html);
							$('#datatable').dataTable();
							$("#loader").hide();
						},
						error : function(data, status, er) {
							//alert('error');
							$("#loader").hide();
						}
					});
		}
/**
		function listAppointment() {
			var doctorId = readCookie('doctor');
			$("#loader").show();
			$
					.ajax({
						url : "../listAppointmentByDoctorIdWS.do?doctorId="
								+ doctorId,
						type : "GET",
						dataType : "JSON",
						contentType : "application/json",
						mimeType : "application/json",
						success : function(data) {

							var html = '';
							html += "<thead>";
							html += "<tr>";
							html += "<th>Date</th>"
									+ "<th>Time</th>" + "<th>Patient's HN</th>"
									+ "<th>Specialty</th>" + "<th></th>"
									+ "<th></th>"
							html += "</tr>";
							html += "</thead>";
							html += "<tbody>";
							for (var i = 0; i < data.length; i++) {
								if (data[i].appointmentStatus == 1
										&& data[i].preferDate1 == currentPickDate) {

									html += "<tr>";
									if (data[i].preferChoice == 1) {
										html += "<td>" + data[i].preferDate1
												+ "</td>" + "<td>"
												+ data[i].preferTime1 + "</td>";
									} else {
										html += "<td>" + data[i].preferDate2
												+ "</td>" + "<td>"
												+ data[i].preferTime2 + "</td>";
									}
									html += "<td>"
											+ data[i].hospitalNumber
											+ "</td>"
											+ "<td>"
											+ data[i].specialty
											+ "</td>"
											+ "<td style=\"width:10%\"><a role=\"button\" class=\"btn btn-default btn-sm btn-block\" data-toggle=\"modal\" href=\"#editApp\" data-todo='{\"appointmentId\":\"" + data[i].keyString + "\",\"preferDate\":\"" + data[i].preferDate1 + "\",\"doctorId\":\"" + data[i].doctorId + "\",\"patientId\":\"" + data[i].patientId + "\",\"status\":\"1\",\"hn\":\""+data[i].hospitalNumber+"\",\"name\":\"" + data[i].patientFirstname + " " + data[i].patientLastname +"\",\"time\":\""+data[i].preferTime1+"\",\"preferChoice\":\""+data[i].preferChoice+"\",\"preferDate2\":\""+data[i].preferDate2+"\",\"time2\":\""+data[i].preferTime2+"\"}'>Edit</a></td>"
											+ "<td style=\"width:10%\"><a class=\"btn bg-red btn-block btn-sm\" href=\"#\" onclick=\"onclickCancelAppointment('" + data[i].appointmentId + "','" + data[i].preferDate1 + "','" + data[i].doctorId + "','" + data[i].patientId + "','3','" + data[i].preferChoice + "','" + data[i].preferTime1 + "','" + data[i].preferDate2 + "',,'" + data[i].preferTime2 + "')\">Cancel</a></td>";
									html += "</tr>";
								}
							}
							html += "</tbody>";
							$('.appointmentTable').html(html);
							$('#datatable').dataTable();
							$("#loader").hide();
						},
						error : function(data, status, er) {
							//alert('error');
							$("#loader").hide();
						}
					});
		}
**/
		function listPreferTime() {
			var doctorId = readCookie('doctor');
			var newCurrentDate = $('#makeAppointmnetDate').val().toString();
			currentPickDate = newCurrentDate;
			$.ajax({
				url : "../listAppointmentByDoctorIdWS.do?doctorId=" + doctorId,
				type : "GET",
				dataType : "JSON",
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					var times = [ "08:00 - 08:30", "08:30 - 09:00", "09:00 - 09:30",
							"09:30 - 10:00", "10:00 - 10:30", "10:30 - 11:00",
							"11:00 - 11:30", "11:30 - 12:00", "12:00 - 12:30",
							"12:30 - 13:00", "13:00 - 13:30", "13:30 - 14:00",
							"14:00 - 14:30", "14:30 - 15:00", "15:00 - 15:30",
							"15:30 - 16:00" ];
					var dataTimes = [];
					for (var i = 0; i < data.length; i++) {
						if(data[i].status == 1){
							var chosenDate;
							var chosenTime;
							if(data[i].preferChoice == 1){
								chosenDate = data[i].preferDate1;
								chosenTime = data[i].preferTime1;
							}else if(data[i].preferChoice == 2){
								chosenDate = data[i].preferDate2;
								chosenTime = data[i].preferTime2;
							}
							if (chosenDate == currentPickDate) {
								dataTimes.push(chosenTime);
							}
						}
					}
				//	alert(dataTimes);
					var availableTimes = [];
					$.each(times, function(i, el) {
						if ($.inArray(el, dataTimes) == -1){
							availableTimes.push(el);
						}
					});
					
			
					var html = '';
					for (var i = 0; i < availableTimes.length; i++) {
						html += "<option>" + availableTimes[i] + "</option>";
					}
					$('.listTime').html(html);
					$("#loader").hide();

					$('#btnSubmitCreateAppointment').prop('disabled', false);
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});
		}

		function listPreferTimeEdit() {
			var doctorId = readCookie('doctor');
			var newCurrentDate = $('#editAppointmentDate').val().toString();
			currentPickDate = newCurrentDate;
			$.ajax({
				url : "../listAppointmentByDoctorIdWS.do?doctorId=" + doctorId,
				type : "GET",
				dataType : "JSON",
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					var times = [ "08:00 - 08:30", "08:30 - 09:00", "09:00 - 09:30",
							"09:30 - 10:00", "10:00 - 10:30", "10:30 - 11:00",
							"11:00 - 11:30", "11:30 - 12:00", "12:00 - 12:30",
							"12:30 - 13:00", "13:00 - 13:30", "13:30 - 14:00",
							"14:00 - 14:30", "14:30 - 15:00", "15:00 - 15:30",
							"15:30 - 16:00" ];
					var dataTimes = [];
					for (var i = 0; i < data.length; i++) {
						if(data[i].status == 1){
							var chosenDate;
							var chosenTime;
							if(data[i].preferChoice == 1){
								chosenDate = data[i].preferDate1;
								chosenTime = data[i].preferTime1;
							}else if(data[i].preferChoice == 2){
								chosenDate = data[i].preferDate2;
								chosenTime = data[i].preferTime2;
							}
							if (chosenDate == currentPickDate) {
								dataTimes.push(chosenTime);
							}
						}
					}
				//alert(dataTimes);
					var availableTimes = [];
					$.each(times, function(i, el) {
						if ($.inArray(el, dataTimes) == -1){
							availableTimes.push(el);
						}
					});
					
			
					var html = '';
					for (var i = 0; i < availableTimes.length; i++) {
						html += "<option>" + availableTimes[i] + "</option>";
					}
					$('.listTime2').html(html);
					$("#loader").hide();

					$('#btnSubmitEditAppointment').prop('disabled', false);
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});
		}

		function createAppointment() {
			var patientId = readCookie('patientSelection');
			var doctorId = readCookie('doctor');
			var preferTime = $("#makeAppointmentTime option:selected").text();
			var preferDate = $("#makeAppointmnetDate").val();
			$("#loader").show();
			var obj = {
				initialSymptom : $('#makeAppointmentInitialSymptoms').val(),
				specialty : readCookie("doctorSpecialty"),
				preferTime1 : preferTime,
				preferDate1 : preferDate,
				status : 1,
				doctorId : doctorId,
				patientId : patientId,
				preferChoice: 1

			};
		//	alert(JSON.stringify(obj));
			$.ajax({
				url : "../makeAppointmentWS.do",
				type : "POST",
				dataType : "JSON",
				data : JSON.stringify(obj),
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					//alert('success');
					successalert();
					$("#loader").hide();
				},
				error : function(data, status, er) {
					alert('error');
					$("#loader").hide();
				}
			});
		}

		function listPatients() {
			$("#loader").show();
			$
					.ajax({
						url : "../listPatientWS.do",
						type : "GET",
						success : function(data) {
							var html = '';
							for (var i = 0; i < data.length; i++) {
								html += "<option id=\"" +data[i].keyString + "\" value=\""+ data[i].codeHN + " " + data[i].firstname + " " + data[i].lastname + "\"></option>";
							}
							$('.listPatient').html(html);
							$("#loader").hide();
						},
						error : function(data, status, er) {
							//alert('error');
							$("#loader").hide();
						}
					});
		}

		function removeAppointment(appointmentId,preferDate,doctorId,patientId,status,preferChoice,preferTime,preferDate2,preferTime2) {
			var patientId = patientId;
			var doctorId = doctorId;
			$("#loader").show();
			var obj = {
				keyString : appointmentId,
				status : 3,
				doctorId : doctorId,
				patientId : patientId,
				preferChoice : preferChoice,
				preferDate1 : preferDate,
				preferTime1 : preferTime,
				preferDate2 : preferDate2,
				preferTime2 : preferTime2
			};
			//alert(JSON.stringify(obj));
			$.ajax({
				url : "../cancelAppointmentWS.do",
				type : "POST",
				dataType : "JSON",
				data : JSON.stringify(obj),
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					//alert('success');
					$("#loader").hide();
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});
		}

		function updateAppointment() {
			var patientId = $('#patientId').val();
			var preferTime = $("#editAppointmentTime option:selected").text();
			var doctorId = $('#doctorId').val();
			var preferDate1 = '';
			var preferDate2 = '';
			var preferTime1 = '';
			var preferTime2 = '';
			if($('#preferChoice').val() == 1){
				preferDate1 = $('#editAppointmentDate').val();
				preferTime1 = $('#editAppointmentTime').val();
				preferDate2 = $('#preferDate2').val();
				preferTime2 = $('#preferTime2').val();
			}else{
				preferDate2 = $('#editAppointmentDate').val();
				preferTime2 = $('#editAppointmentTime').val();
				preferDate1 = $('#preferDate1').val();
				preferTime1 = $('#preferTime1').val();
			}
			$("#loader").show();
			var obj = {
				keyString : $('#appointmentId').val(),
				preferDate1 : preferDate1,
				preferDate2 : preferDate2,
				preferTime2 : preferTime2,
				specialty : readCookie("doctorSpecialty"),
				preferTime1 : preferTime1,
				appointmentStatus : $('#status').val(),
				doctorId : doctorId,
				patientId : patientId,
				preferChoice : $('#preferChoice').val()
			};
			//alert(JSON.stringify(obj));
			$.ajax({
				url : "../saveAppointmentWS.do",
				type : "POST",
				dataType : "JSON",
				data : JSON.stringify(obj),
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
	</script>

	<script>
		function writeCookie(name, value, days) {
			var date, expires;
			if (days) {
				date = new Date();
				date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
				expires = "; expires=" + date.toGMTString();
			} else {
				expires = "";
			}
			document.cookie = name + "=" + value + expires + "; path=/";

		}
		function readCookie(name) {
			var i, c, ca, nameEQ = name + "=";
			ca = document.cookie.split(';');
			for (i = 0; i < ca.length; i++) {
				c = ca[i];
				while (c.charAt(0) == ' ') {
					c = c.substring(1, c.length);
				}
				if (c.indexOf(nameEQ) == 0) {
					return c.substring(nameEQ.length, c.length);
				}
			}
			return '';
		}

		$('#makeAppointmentPatient').on('input', function() {
			var opt = $('option[value="' + $(this).val() + '"]');
			writeCookie('patientSelection', opt.attr('id'), 1);

		});

		$('#editApp').on('show.bs.modal', function(e) {
			var id = $(e.relatedTarget).data('todo').appointmentId;
			var patient = $(e.relatedTarget).data('todo').patientId;
			var doctor = $(e.relatedTarget).data('todo').doctorId;
			var sta = $(e.relatedTarget).data('todo').status;
			var date = $(e.relatedTarget).data('todo').preferDate;
			var hospitalnum = $(e.relatedTarget).data('todo').hn;
			var patientName = $(e.relatedTarget).data('todo').name;
			var time = $(e.relatedTarget).data('todo').time;
			var choice = $(e.relatedTarget).data('todo').preferChoice;
			var date2 = $(e.relatedTarget).data('todo').preferDate2;
			var time2 = $(e.relatedTarget).data('todo').time2;
			
			$("#doctorId").val(doctor);
			$("#hospitalNumber").text(hospitalnum);
			$("#name").text(patientName);
			$("#patientId").val(patient);
			$("#status").val(sta);
		
			if(choice == 1){
				$("#editAppointmentDate").val(date);
				$("#editAppointmentTime").val(time);
			}else{
				$("#editAppointmentDate").val(date2);
				$("#editAppointmentTime").val(time2);
			}
			$("#preferDate1").val(date);
			$("#preferTime1").val(time);
			$("#preferDate2").val(date2);
			$("#preferTime2").val(time2);
			$("#appointmentId").val(id);
			$("#preferChoice").val(choice);
			
			
		});
	</script>
</body>
</html>
</body>

</html>