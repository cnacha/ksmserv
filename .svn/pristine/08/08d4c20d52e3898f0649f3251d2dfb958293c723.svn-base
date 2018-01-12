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
    
      <!-- Sweetalert Css -->
    <link href="../../plugins/sweetalert/sweetalert.css" rel="stylesheet" />

    <!-- Custom Css -->
    <link href="../../css/style.css" rel="stylesheet">

    <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
    <link href="../../css/themes/all-themes.css" rel="stylesheet" />

    <style>
        .table-borderless tbody tr td, .table-borderless tbody tr th,
        .table-borderless thead tr th, .table-borderless thead tr td,
        .table-borderless tfoot tr th, .table-borderless tfoot tr td {
        border: none;
        }
    </style>
</head>

<body class="theme-purple" onload="findAppointment();">
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
    <%@ include file="../sidebar.jsp" %>

    <section class="content">
        <div class="container-fluid">
			<!-- Radio -->
            <div class="">
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                Appointment Information
                            </h2>
                        </div>
                        <div class="body">
                            <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">Specialty</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <p id="specialty">Specialty</p>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">Requested Doctor</label>
                                    <div id="doctorInput" class="col-md-8 col-sm-8 col-xs-12">
                               
                                    </div>
                                </div>
								<div class="clearfix"></div>
								<form id="myForm">
                                <div class="form-group">
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12">
                                            Choice 1st </label>
                                        <div class="col-md-9 col-sm-9 col-xs-12">
                                            <table class="table table-borderless" style="width:80%;margin-bottom:0px">
                                                <tr>
                                           			<td>
                                           				<input name="group1" type="radio" onclick="onClickChangeDateAndTime(this);" class="with-gap" id="radio_3" value="1" checked="checked"/>
                                						<label for="radio_3"></label>
                                					</td>
                                                    <td><p id="date1">10-10-2016</p></td>
                                                    <td><strong>Time</strong></td>
                                                    <td><p id="time1">10.00<p></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-2 col-sm-2 col-xs-12">
                                            Choice 2nd </label>
                                        <div class="col-md-9 col-sm-9 col-xs-12">
                                            <table class="table table-borderless" style="width:80%;margin-bottom:0px">
                                                <tr>
                                                	<td>
                                                		<input name="group1" type="radio"  onclick="onClickChangeDateAndTime(this);" id="radio_4" class="with-gap" value="2"/>
                                						<label for="radio_4"></label>
                               						</td>
                                                    <td><p id="date2">10-10-2016</p></td>
                                                    <td><strong>Time</strong></td>
                                                    <td><p id="time2">10.00<p></td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                               </form>
								<div class="clearfix"></div>
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-3 col-xs-12">Initial symptoms</label>
                                    <div class="col-md-9 col-sm-9 col-xs-12">
                                        <p id="symptom">Initial symptoms</p>
                                    </div>
                                </div>

                                <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Radio -->
            
            <!-- Radio -->
            <div class="">
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                Patient Information
                            </h2>
                        </div>
                        <div class="body">
                            	<div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">Hospital
                                        Number</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <p style="margin: 8px 0px" id="patHN">1234</p>
                                    </div>
                                </div>
								<div class="clearfix"></div>
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">Name</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <p style="margin: 8px 0px" id="patName">Name</p>
                                    </div>
                                </div>
								<div class="clearfix"></div>
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">Contact</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <p style="margin: 8px 0px" id="contact">123456</p>
                                    </div>
                                </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Radio -->
          
            <div class="col-xs-12">
                <div class="row clearfix js-sweetalert">
                	<a href="#" class="btn btn-default btn-lg" onclick="history.go(-1)">Back</a>
                	<a href="#" style="color:#fff" class="btn bg-red btn-lg" onclick="onclickRefuseButton()">Refuse</a>
                	<a href="#" style="color:#fff" class="btn bg-lime btn-lg" onclick="onclickApproveButton()">&nbsp&nbsp&nbsp Approve &nbsp&nbsp&nbsp</a>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </section>


<%@include file="../../settings.jsp"%>
<form:form  id="loginForm" method="post" action="../login.do" modelAttribute="loginBean">
</form:form>

    <!-- Jquery Core Js -->
    <script src="../../plugins/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="../../plugins/bootstrap/js/bootstrap.js"></script>

    <!-- Slimscroll Plugin Js -->
    <script src="../../plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

    <!-- Bootstrap Notify Plugin Js -->
    <script src="../../plugins/bootstrap-notify/bootstrap-notify.js"></script>
    
    <!-- SweetAlert Plugin Js -->
    <script src="../../plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="../../plugins/node-waves/waves.js"></script>

    <!-- Custom Js -->
    <script src="../../js/admin.js"></script>
    <script src="../../js/pages/ui/dialogs.js"></script>
    <script src="../../js/pages/forms/basic-form-elements.js"></script>

    <!-- Demo Js -->
    <script src="../../js/demo.js"></script>
    
    <script>
	
	var appointmentId = '';
	var preferDate1 = '';
	var	preferTime1 = '';
	var preferDate2 = '';
	var	preferTime2 = '';
	var	initialSymptom = '';
	var	doctorId = '';
	var	patientId = '';
	var specialty = '';
	var choice = 1;

	function onclickRefuseButton(){
		swal({
			  title: "Are you sure?",
			  text: "You will not be able to recover this appointment!",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "Yes, refuse it!",
			  closeOnConfirm: false,
			  html: false
			}, function(){
			refuseAppointment();
			  swal("Refused!",
			  "This appointment has been refused.",
			  "success");
			  setTimeout(function () {
				  window.location.href = "appointment.do";
              }, 3000);
				

			});
	}
	
	function onclickApproveButton(){
		swal({
			  title: "Are you sure?",
			  text: "You will approve this appointment!",
			  showCancelButton: true,
			  confirmButtonColor: "#A7B671",
			  confirmButtonText: "Yes, approve it!",
			  closeOnConfirm: false,
			  html: false
			}, function(){
				approveAppointment();
			});
	}
	
	function onClickChangeDateAndTime(preferChoice){
		choice = preferChoice.value;
		listDoctorBySpecialty(choice);
	}
	
	function listDoctorBySpecialty(preferChoice){

		choice = preferChoice;
		
		var preferDate = "";
		var preferTime = "";
		
		if(choice == 1){
			preferDate = preferDate1;
			preferTime = preferTime1;
		}else{
			preferDate = preferDate2;
			preferTime = preferTime2;
		}
		
		$.ajax({
			url : "../listDoctorBySpecialtyAndPreferDateAndPreferTimeWS.do?specialty="+specialty+"&preferDate="+preferDate+"&preferTime="+preferTime+"&preferChoice="+choice,
			type : "GET",
			dataType : "JSON",
			contentType : "application/json",
			mimeType : "application/json",
			success : function(data) {
				
				var doctorElement = "<select id=\"doctorName\" class=\"form-control\" style=\"color: #73879c; font-size: 98%\">";
				
				for(var i = 0;i < data.length; i++){
					doctorElement += "<option value=\""+data[i].keyString+"\">"+data[i].firstname+" "+data[i].lastname+"</option>";
				}
				
				doctorElement += "</select>";
				
				$("#doctorInput").html(doctorElement);
				
			},
			error : function(data, status, er) {
				//alert('error');
				$("#loader").hide();
			}
		});
		
		
	}
	
	function findAppointment() {
		appointmentId = readCookie('appointment');
	
		$("#loader").show();
		$.ajax({
			url : "../findAppointmentWS.do?appointmentId="+appointmentId,
			type : "GET",
			dataType : "JSON",
			contentType : "application/json",
			mimeType : "application/json",
			success : function(data) {				
				
				 initialSymptom = data.initialSymptom;
				 
				 if(data.doctorId != null){
				 	doctorId = data.doctorId;
				 }
				 console.log(data.preferChoice);
				 if(data.preferChoice!=0){
					 var $radios = $('input:radio[name=group1]');
					$radios.filter('[value='+data.preferChoice+']').prop('checked', true);
					// $radios.attr('disabled', true);
				 }
				
				 preferDate1 = data.preferDate1;
				 preferDate2 = data.preferDate2;
				 preferTime1 = data.preferTime1;
				 preferTime2 = data.preferTime2;
				 
				 patientId = data.patientId;
				 specialty = data.doctor.specialty;
					
				$("#specialty").text(data.doctor.specialty);
			
				$("#date1").text(data.preferDate1);
				$("#time1").text(data.preferTime1);
				
				if (data.preferDate2 == null) {
					$("#date2").text("-");
				} else {
					$("#date2").text(data.preferDate2);
				}
				if (data.preferTime2 == null) {
					$("#time2").text("-");
				} else {
					$("#time2").text(data.preferTime2);
				}
				if (data.initialSymptom == null) {
					$("#symptom").text("-");
				} else {
					$("#symptom").text(data.initialSymptom);
				}
				
				var doctorElement = '';
				
				if(data.doctorId != null){
				
					doctorElement += "<p style=\"margin: 8px 0px\" >"+data.doctor.firstname +" "+ data.doctor.lastname+"</p>";
					
				}else{
				
					listDoctorBySpecialty('1');
					
				}
				
			
				$("#doctorInput").html(doctorElement);
				

				$("#patHN").text(data.patient.codeHN);
				$("#patName").text(
						data.patient.firstname + " " + data.patient.lastname);
				$("#contact").text(data.patient.contact);
			},
			error : function(data, status, er) {
				//alert('error');
				$("#loader").hide();
			}
		});
	}
	
	function approveAppointment(){
		if(doctorId == ''){
			doctorId = $("#doctorName").val();
		}
		
		var choiceNum = $('input[name=group1]:checked', '#myForm').val();//$('#group1').val();
		
		var obj = {
				keyString : appointmentId,
				preferDate1 : preferDate1,
				preferTime1 : preferTime1,
				preferDate2 : preferDate2,
				preferTime2 : preferTime2,
				initialSymptom : initialSymptom,
				specialty : specialty,
				status : 1,
				preferChoice : choiceNum,
				doctorId : doctorId,
				patientId : patientId
			};
		//	alert(choiceNum+"xxxx" + JSON.stringify(obj));
		$.ajax({
				url : "../saveAppointmentWS.do",
				type : "POST",
				dataType : "JSON",
				data : JSON.stringify(obj),
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					if(data == "1"){
					 	swal("Approve!",
					  			"This appointment has been approved.",
					  			"success");
					 	 setTimeout(function () {
							  window.location.href = "appointment.do";
			              }, 3000);
					} else{
						swal("Fail!",
								  "The chosen time option is not available, please select other time option.","warning");
					}
					return data;
				
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
					return "-1";
				}
			});
	
	}

	function refuseAppointment(){
		if(doctorId == ''){
			doctorId = $("#doctorName").val();
		}
		var obj = {
				keyString : appointmentId,
				preferDate1 : preferDate1,
				preferTime1 : preferTime1,
				preferDate2 : preferDate2,
				preferTime2 : preferTime2,
				initialSymptom : initialSymptom,
				specialty : specialty,
				status : 2,
				preferChoice : 0,
				doctorId : doctorId,
				patientId : patientId
			};
		
			$.ajax({
				url : "../saveAppointmentWS.do",
				type : "POST",
				dataType : "JSON",
				data : JSON.stringify(obj),
				contentType : "application/json",
				mimeType : "application/json",
				success : function(data) {
					//alert('Refuse Success');
				
				},
				error : function(data, status, er) {
					//alert('error');
					$("#loader").hide();
				}
			});
	}
</script>

	<script>
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
		</script>
</body>

</html>