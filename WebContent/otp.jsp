<%@ page language="java" pageEncoding="UTF-8"%>
    
<%@page import="java.io.*"%>
<%@page import="javax.servlet.*"%>

<% 
    if(null == session.getAttribute("logdIn") || Integer.parseInt(session.getAttribute("logdIn").toString()) == 0) {
        session.setAttribute("login", 0);
        
    } else {
        int status = Integer.parseInt(session.getAttribute("usr_status").toString());
        
        switch(status) {
            case 1:
                response.sendRedirect("owner/dashboard.jsp");
                break;
            case 2:
                response.sendRedirect("user/dashboard.jsp");
                break;
            case 3:
                response.sendRedirect("ca/dashboard.jsp");
                break;
            case 4:
                response.sendRedirect("aa/dashboard.jsp");
                break;
            case 5:
                response.sendRedirect("cloud/dashboard.jsp");
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Title -->
        <title>Welcome to xACS</title>

        <!-- Required Meta Tags Always Come First -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">

        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/favicon/favicon.ico">
        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet">
        <!-- CSS Global Compulsory -->
        <link rel="stylesheet" href="assets/vendor/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="assets/vendor/icon-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="assets/vendor/icon-line-pro/style.css">
        <link rel="stylesheet" href="assets/vendor/animate.css">

        <!-- CSS Global Icons -->
        <link rel="stylesheet" href="assets/vendor/icon-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="assets/vendor/icon-line/css/simple-line-icons.css">
        <link rel="stylesheet" href="assets/vendor/icon-etlinefont/style.css">
        <link rel="stylesheet" href="assets/vendor/icon-line-pro/style.css">
        <link rel="stylesheet" href="assets/vendor/icon-hs/style.css">

        <!-- CSS Unify -->
        <link rel="stylesheet" href="assets/css/unify-core.css">
        <link rel="stylesheet" href="assets/css/unify-components.css">
        <link rel="stylesheet" href="assets/css/unify-globals.css">

        <!-- CSS Customization -->
        <link rel="stylesheet" href="assets/css/custom.css">
    </head>

    <body>
        <main>
            <!-- Signup -->
            <section class="g-min-height-100vh g-flex-centered g-bg-lightblue-radialgradient-circle">
                <div class="container g-py-50">
                    <div class="row justify-content-center">
                        <div class="col-sm-10 col-md-9 col-lg-7">
                            <div class="u-shadow-v24 g-bg-white rounded g-py-40 g-px-30">
                                <header class="text-center mb-4">
                                    <!--<img class="img-fluid" src="assets/img/logo/logo.png" height="32" alt="Image Description" style="height: 32px !important; display:inline;">-->

                                    <h2 class="h2 g-color-black g-font-weight-600" style="display:inline; white-space:nowrap;">xACS Signup</h2>
                                </header>
                                
                                <div class="alert alert-dismissible fade show g-bg-red g-color-white rounded-0" role="alert" id="failAlert" style="visibility: hidden; display: none;">
                                    <button type="button" class="close u-alert-close--light" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>

                                    <div class="media">
                                        <span class="d-flex g-mr-10 g-mt-5">
                                            <i class="icon-ban g-font-size-25"></i>
                                        </span>
                                        <span class="media-body align-self-center">
                                            <strong>Unable to signup!</strong> Try submitting again.
                                        </span>
                                    </div>
                                </div>
                                
                                <div class="alert alert-dismissible fade show g-bg-gray-dark-v2 g-color-white rounded-0" role="alert" id="sqlAlert" style="visibility: hidden; display: none;">
                                    <button type="button" class="close u-alert-close--light" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>

                                    <div class="media">
                                        <span class="d-flex g-mr-10 g-mt-5">
                                            <i class="icon-question g-font-size-25"></i>
                                        </span>
                                        <span class="media-body align-self-center">
                                            <strong>Warning!</strong> Unable to connect to SQL server.
                                        </span>
                                    </div>
                                </div>
                                
                                <% 

                                String[] dataArr = (String[])session.getAttribute("dataArray");
                                
                                %>
                                
                                <!-- Form -->
                                <form class="g-py-15" action="signupScript">

                                    <div class="row justify-content-between">
                                        <div class="col-9 mb-4">
                                            <label class="g-color-gray-dark-v2 g-font-weight-600 g-font-size-13">Please enter OTP sent on <%=dataArr[8]%>:</label>
                                            <input class="form-control g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v4 g-brd-primary--hover rounded g-py-15 g-px-15" name="otp" type="text" required="">
                                        </div>
                                        <div class="col-3 align-self-center text-right">
                                            <button class="btn btn-md u-btn-primary rounded g-py-13 g-px-25" type="submit">Submit</button>
                                        </div>
                                    </div>
                                </form>
                                <!-- End Form -->

                                <footer class="text-center">
                                    <p class="g-color-gray-dark-v5 g-font-size-13 mb-0">Already have an account? <a class="g-font-weight-600" href="index.jsp">login</a>
                                    </p>
                                </footer>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- End Signup -->
        </main>

        <div class="u-outer-spaces-helper"></div>


        <!-- JS Global Compulsory -->
        <script src="assets/vendor/jquery/jquery.min.js"></script>
        <script src="assets/vendor/jquery-migrate/jquery-migrate.min.js"></script>
        <script src="assets/vendor/popper.min.js"></script>
        <script src="assets/vendor/bootstrap/bootstrap.min.js"></script>


        <!-- JS Unify -->
        <script src="assets/js/hs.core.js"></script>

        <!-- JS Customization -->
        <script src="assets/js/custom.js"></script>

        <script>
            $(document).ready(function () {

                if (window.location.href.indexOf('#signupFail') !== -1) {
                    $('#failAlert').css({
                        'visibility': 'visible',
                        'display': 'block'
                    });
                    $('#failAlert').show();
                } else {
                    $('#failAlert').hide();
                }

                if (window.location.href.indexOf('#SQLfail') !== -1) {
                    $('#sqlAlert').css({
                        'visibility': 'visible',
                        'display': 'block'
                    });
                    $('#sqlAlert').show();
                } else {
                    $('#sqlAlert').hide();
                }

            });

        </script>

    </body>

</html>
