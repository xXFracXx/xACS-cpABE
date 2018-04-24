<%@ page language="java" pageEncoding="UTF-8"%>

<%@page import="java.io.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>

<%
    if (null == session.getAttribute("logdIn") || Integer.parseInt(session.getAttribute("logdIn").toString()) == 0) {
        session.setAttribute("login", 0);

    } else {
        int status = Integer.parseInt(session.getAttribute("usr_status").toString());

        switch (status) {
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
            <!-- Login -->
            <section class="g-min-height-100vh g-flex-centered g-bg-lightblue-radialgradient-circle">
                <div class="container g-py-100">
                    <div class="row justify-content-center">
                        <div class="col-sm-10 col-lg-7">
                            <div class="u-shadow-v24 g-bg-white rounded g-py-40 g-px-30">
                                <header class="text-center mb-4">
                                    <h2 class="h2 g-color-black g-font-weight-600">xACS Login</h2>
                                </header>

                                <div class="alert alert-dismissible fade show g-bg-teal g-color-white rounded-0" role="alert" id="signupSuccAlert" style="visibility: hidden; display: none;">
                                    <button type="button" class="close u-alert-close--light" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>

                                    <div class="media">
                                        <span class="d-flex g-mr-10 g-mt-5">
                                            <i class="icon-check g-font-size-25"></i>
                                        </span>
                                        <span class="media-body align-self-center">
                                            <strong>Well done!</strong> You successfully signed up.
                                        </span>
                                    </div>
                                </div>

                                <div class="alert alert-dismissible fade show g-bg-red g-color-white rounded-0" role="alert" id="failAlert" style="visibility: hidden; display: none;">
                                    <button type="button" class="close u-alert-close--light" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>

                                    <div class="media">
                                        <span class="d-flex g-mr-10 g-mt-5">
                                            <i class="icon-ban g-font-size-25"></i>
                                        </span>
                                        <span class="media-body align-self-center">
                                            <strong>Incorrect username/password!</strong> Try submitting again.
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

                                <!-- Form -->
                                <form class="g-py-15" action="loginScript">
                                    <div class="mb-4">
                                        <label class="g-color-gray-dark-v2 g-font-weight-600 g-font-size-13">Username:</label>
                                        <input class="form-control g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v4 g-brd-primary--hover rounded g-py-15 g-px-15" id="username" name="name" type="text" onKeyDown="radioChange();" onpaste="return false;">
                                    </div>

                                    <div class="g-mb-35">
                                        <div class="row justify-content-between">
                                            <div class="col align-self-center">
                                                <label class="g-color-gray-dark-v2 g-font-weight-600 g-font-size-13">Password:</label>
                                            </div>
                                        </div>
                                        <input class="form-control g-color-black g-bg-white g-bg-white--focus g-brd-gray-light-v4 g-brd-primary--hover rounded g-py-15 g-px-15 mb-3" name="pass" type="password">
                                        <div class="row justify-content-between">
                                            <div class="col-9 align-self-center">
                                                <div class="btn-group justified-content">
                                                    <label class="u-check">
                                                        <input class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" name="status" type="radio" id="rOwner" value="1" checked="">
                                                        <span class="btn btn-md btn-block u-btn-outline-lightgray g-color-white--checked g-bg-primary--checked rounded-0">owner</span>
                                                    </label>
                                                    <label class="u-check">
                                                        <input class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" name="status" type="radio" id="rUser" value="2">
                                                        <span class="btn btn-md btn-block u-btn-outline-lightgray g-color-white--checked g-bg-primary--checked g-brd-left-none--md rounded-0">user</span>
                                                    </label>
                                                    <label class="u-check">
                                                        <input class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" name="status" type="radio" id="rCA" value="3">
                                                        <span class="btn btn-md btn-block u-btn-outline-lightgray g-color-white--checked g-bg-primary--checked g-brd-left-none--md rounded-0">CA</span>
                                                    </label>
                                                    <label class="u-check">
                                                        <input class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" name="status" type="radio" id="rAA" value="4">
                                                        <span class="btn btn-md btn-block u-btn-outline-lightgray g-color-white--checked g-bg-primary--checked g-brd-left-none--md rounded-0">AA</span>
                                                    </label>
                                                    <label class="u-check">
                                                        <input class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" name="status" type="radio" id="rCloud" value="5">
                                                        <span class="btn btn-md btn-block u-btn-outline-lightgray g-color-white--checked g-bg-primary--checked g-brd-left-none--md rounded-0">cloud</span>
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-3 align-self-center text-right">
                                                <button class="btn btn-md u-btn-primary rounded g-py-13 g-px-25" type="submit">Login</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <!-- End Form -->

                                <footer class="text-center">
                                    <p class="g-color-gray-dark-v5 g-font-size-13 mb-0">Don't have an account? <a class="g-font-weight-600" href="signup.jsp">signup</a>
                                    </p>
                                </footer>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- End Login -->
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

                if (window.location.href.indexOf('#fail') !== -1) {
                    $('#failAlert').css({
                        'visibility': 'visible',
                        'display': 'block'
                    });
                    $('#failAlert').show();
                } else {
                    $('#failAlert').hide();
                }

                if (window.location.href.indexOf('#signupSucc') !== -1) {
                    $('#signupSuccAlert').css({
                        'visibility': 'visible',
                        'display': 'block'
                    });
                    $('#signupSuccAlert').show();
                } else {
                    $('#signupSuccAlert').hide();
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

            function radioChange() {
                var str = document.getElementById("username").value;
                var res = str.substring(0, 3).toLowerCase();
                switch (res) {
                    case 'aid':
                        document.getElementById('rAA').checked = true;
                        break;
                    case 'uid':
                        document.getElementById('rUser').checked = true;
                        break;
                    case 'ca':
                        document.getElementById('rCA').checked = true;
                        break;
                    case 'own':
                        document.getElementById('rOwner').checked = true;
                        break;
                    case 'clo':
                        document.getElementById('rCloud').checked = true;
                        break;
                    default:
                        document.getElementById('rOwner').checked = true;
                        break;
                }
                return true;
            }

        </script>
    </body>

</html>
