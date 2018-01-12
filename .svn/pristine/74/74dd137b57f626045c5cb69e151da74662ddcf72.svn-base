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

<body class="theme-purple" onload="findAppointment()">
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
            <!-- Example Tab -->
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                    <div class="card">
                        <div class="header bg-light-green">
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
                                    <label class="control-lal col-md-4 col-sm-4 col-xs-12">Requested Doctor</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <p id="docName">Requested Doctor</p>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">Date</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <p id="date">Date</p>
                                    </div>
                                </div>
								<div class="clearfix"></div>
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">Time</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <p id="time">Time</p>
                                    </div>
                                </div>
								<div class="clearfix"></div>
                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">Initial symptoms</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <p id="symptom">Initial symptoms</p>
                                    </div>
                                </div>

                                <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            <!-- #END# Example Tab -->

            <!-- Example Tab -->
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                    <div class="card">
                        <div class="header bg-light-green">
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

                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">Name</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <p style="margin: 8px 0px" id="patName">Name</p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-md-4 col-sm-4 col-xs-12">Contact</label>
                                    <div class="col-md-8 col-sm-8 col-xs-12">
                                        <p style="margin: 8px 0px" id="contact">123456</p>
                                    </div>
                                </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <center><img style="width:45%" src="../../images/approvedStamp.png"></center>
                    
                </div>
            <!-- #END# Example Tab -->
            <div class="col-xs-12">
                <a href="#" class="btn btn-default btn-lg" onclick="history.go(-1)">Back</a>
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

    <!-- Waves Effect Plugin Js -->
    <script src="../../plugins/node-waves/waves.js"></script>

    <!-- Jquery CountTo Plugin Js -->
    <script src="../../plugins/jquery-countto/jquery.countTo.js"></script>

    <!-- Custom Js -->
    <script src="../../js/admin.js"></script>
    <script src="../../js/pages/index.js"></script>

    <!-- Demo Js -->
    <script src="../../js/demo.js"></script>
    
    <script>
	function findAppointment() {
		var appointmentId = readCookie('appointment');
		$("#loader").show();
		$.ajax({
			url : "../findAppointmentWS.do?appointmentId="+appointmentId,
			type : "GET",
			dataType : "JSON",
			contentType : "application/json",
			mimeType : "application/json",
			success : function(data) {
				$("#specialty").text(data.specialty);
				$("#docName").text(
						"Dr. " + data.doctor.firstname + " "
								+ data.doctor.lastname);
				
				if(data.preferChoice == 1){
					$("#date").text(data.preferDate1);
					$("#time").text(data.preferTime1);
				}else{
					$("#date").text(data.preferDate2);
					$("#time").text(data.preferTime2);
				}
			
				if (data.initialSymptom == null) {
					$("#symptom").text("-");
				} else {
					$("#symptom").text(data.initialSymptom);
				}

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