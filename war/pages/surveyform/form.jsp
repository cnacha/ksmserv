<%@ page errorPage="../login.do" %>
<%@taglib
	uri="http://www.springframework.org/tags/form"
	prefix="form"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>Welcome To | Survey Management</title>
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

    <!-- Wait Me Css -->
    <link href="../plugins/waitme/waitMe.css" rel="stylesheet" />

    <!-- Bootstrap Select Css -->
    <link href="../plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />

    <!-- JQuery DataTable Css -->
    <link href="../plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.css" rel="stylesheet">

    <!-- Custom Css -->
    <link href="../css/style.css" rel="stylesheet">

    <!-- AdminBSB Themes. You can choose a theme from css/themes instead of get all themes -->
    <link href="../css/themes/all-themes.css" rel="stylesheet" />
</head>

<body class="theme-purple" onload="list();">
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
            <div class="block-header">
                <h2>
                    Survey Form
                </h2>
            </div>
            <!-- Basic Examples -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="body">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs tab-nav-right tab-col-lime" role="tablist">
                                <li role="presentation" class="active"><a href="#index" data-toggle="tab">Forms</a></li>
                            </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                                <div role="tabpanel" class="tab-pane fade in active" id="index">
                                    <button class="btn btn-info pull-right" data-toggle="modal" data-target="#add">
                                        <i class="material-icons">add</i> New Form
                                    </button><br /><br /><br />
 
                                    <table id="datatable_doctor" class="table table-bordered table-striped table-hover js-basic-example dataTable doctorTable">
                                        <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Description</th>
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

<%@include file="../../settings.jsp"%>
<form:form  id="loginForm" method="post" action="../login.do" modelAttribute="loginBean">
</form:form>

        <!-- Modal -->
            <div id="add" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Add Form</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <input type="hidden" id="key" value="0">
                                <div style="padding-left: 3%">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" id="name" name="name" required="required" class="form-control" placeholder="Name" />
                                        </div>
                                    </div>
                                    <div class="form-group" >
                                        <div class="form-line">
                                            <input type="text" id="description" name="description" required="required" class="form-control" placeholder="Description" />
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </form>
                        </div>

                        <div class="modal-footer">
                            
                            <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">Close</button>
                            <button type="submit" class="bbtn btn-link waves-effect" onclick="create()" data-dismiss="modal">Submit</button>
                        </div>
                    </div>

                </div>
            </div>
            <!-- /Modal -->

            <!-- Modal -->
            <div id="edit" class="modal fade" role="dialog">
                <div class="modal-dialog">

                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Update Form information</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <input type="hidden" id="key-edit" name="key-edit">
                                <div style="padding-left: 3%">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" id="name-edit" name="name-edit" required="required" class="form-control" value=""/>
                                        </div>
                                    </div>
                                    <div class="form-group" >
                                        <div class="form-line">
                                            <input type="text" id="description-edit" name="description-edit" required="required" class="form-control" value=""/>
                                        </div>
                                    </div>
                            
                                    <div class="clearfix"></div>
                                </div>
                            </form>
                        </div>

                        <div class="modal-footer">
                            
                            <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">Close</button>
                            <button type="submit" class="bbtn btn-link waves-effect" data-dismiss="modal" onclick="update()">Submit</button>
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
   <!--  <script src="../../plugins/bootstrap-select/js/bootstrap-select.js"></script> -->

    <!-- Slimscroll Plugin Js -->
    <script src="../../plugins/jquery-slimscroll/jquery.slimscroll.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="../../plugins/node-waves/waves.js"></script>

    <!-- Autosize Plugin Js -->
    <script src="../../plugins/autosize/autosize.js"></script>

    <!-- Moment Plugin Js -->
    <script src="../../plugins/momentjs/moment.js"></script>
    
    <!-- SweetAlert Plugin Js -->
    <script src="../../plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Jquery DataTable Plugin Js -->
    <script src="../../plugins/jquery-datatable/jquery.dataTables.js"></script>
    <script src="../../plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>


    <!-- Custom Js -->
    <script src="../../js/admin.js"></script>
    <script src="../../js/pages/tables/jquery-datatable.js"></script>
    <script src="../../js/pages/ui/dialogs.js"></script>

    <!-- Demo Js -->
    <script src="../../js/demo.js"></script>
    
    <script type="text/javascript">
    $(document).on("click", ".open-edit", function () {
		var key = $(this).data('todo').key;
		var name = $(this).data('todo').name;
		var description = $(this).data('todo').description;
		
		$("#key-edit").val(key);
		$("#name-edit").val(name);
		$("#description-edit").val(description);
		

	});
    
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
	
	function onclickRemove(key) {
		swal({
			  title: "Are you sure?",
			  text: "You will not be able to recover",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "Yes, delete it!",
			  closeOnConfirm: false,
			  html: false
			}, function(){
				remove(key);
			  swal("Deleted!",
			  "Form has been deleted.",
			  "success");
			  setTimeout(function(){ 
					list();
				}, 3000);
			  
			});
	}


	function list() {
		$("#loader").show();
		$
				.ajax({
					url : "../listFormWS.do",
					type : "GET",
					success : function(data) {
						var html = '';
						//html += "<table id=\"datatable\" class=\"table table-striped table-bordered\">";
						html += "<thead>";
						html += "<tr>" 
								+ "<th>Name</th>" 
								+ "<th>Description</th>"
								+ "<th></th>"
								+ "<th></th>"
								+ "<th></th>";
						html += "</tr>";
						html += "</thead>";
						html += "<tbody>";
						for (var i = 0; i < data.length; i++) {
							html += "<tr>";
							html += "<td>"
									+ data[i].name
									+ "</td>"
									+ "<td>"
									+ data[i].description
									+ "</td>"
									+ "<td style=\"width: 8%\"> <a data-toggle=\"modal\" data-todo='{\"key\":\"" + data[i].keyString + "\",\"name\":\"" + data[i].name + "\",\"description\":\"" + data[i].description + "\"}'  class=\"open-edit btn btn-default btn-sm btn-block\" href=\"#edit\" >Edit</a> </td>"
									+ "<td style=\"width:8%\"><div class=\"row clearfix js-sweetalert\"><button class=\"btn bg-red waves-effect\" onclick=\"onclickRemove('"+ data[i].keyString +"')\">Delete</button></div></td>"
									+ "<td style=\"width:8%\"><div class=\"row clearfix js-sweetalert\"><a  class=\"open-edit btn btn-default btn-sm btn-block\" href=\"question.do?formkey="+ data[i].keyString +"\">Question</a></div></td>";
							html += "</tr>";
						}
						html += "</tbody>";
						//html += "</table>";
						$('.doctorTable').html(html);
						$("#loader").hide();
					},
					error : function(data, status, er) {
						alert('error');
						$("#loader").hide();
					}
				});
	}
	
	window.setInterval(function(){
		list();
	}, 3000);

	function create() {
		$("#loader").show();
		var obj = {
			name : $('#name').val(),
			description : $('#description').val(),
		};
		// alert(JSON.stringify(obj));
		$.ajax({
			url : "../saveFormWS.do",
			type : "POST",
			dataType : "JSON",
			data : JSON.stringify(obj),
			contentType : "application/json",
			mimeType : "application/json",
			success : function(data) {
				list();
				successalert();
				$("#loader").hide();
			},
			error : function(data, status, er) {
				alert('error');
				$("#loader").hide();
			}
		});
	}

	function update() {
		$("#loader").show();
		var obj = {
			keyString : $('#key-edit').val(),
			name : $('#name-edit').val(),
			description : $('#description-edit').val(),

		};
	//	alert(JSON.stringify(obj));
		$.ajax({
			url : "../saveFormWS.do",
			type : "POST",
			dataType : "JSON",
			data : JSON.stringify(obj),
			contentType : "application/json",
			mimeType : "application/json",
			success : function(data) {
				list();
				//alert('success');
				successalert();
				
			},
			error : function(data, status, er) {
				alert('error');
				$("#loader").hide();
			}
		});
	}
	
	function successalert(){
		swal("Success", "Your request has been done!", "success");
		setTimeout(function(){ 
			list();
		}, 3000);
	}

	function remove(key) {
//		alert("removing key");
		$("#loader").show();
		$.ajax({
			url : "../deleteFormWS.do?key="+key,
			type : "DELETE",
			dataType : "JSON",
			contentType : "application/json",
			mimeType : "application/json",
			success : function(data) {
				//alert('success');
				$("#loader").hide();
			},
			error : function(data, status, er) {
				alert('error');
				$("#loader").hide();
			}
		});
	}

	</script>
</body>

</html>