<%@ page language="java" pageEncoding="UTF-8"%>
    
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="DBcon.sqlStatus"%>
<%@page import="DBcon.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.concurrent.TimeUnit"%>

<%
    sqlStatus sqls = new sqlStatus();
    sqls.stat = true;

    String name = "", sysText = "";
    int nC = 0, tC = 0;

    if (null == session.getAttribute("logdIn") || Integer.parseInt(session.getAttribute("logdIn").toString()) != 1) {
        response.sendRedirect("../index.jsp");

    } else {
        int status = Integer.parseInt(session.getAttribute("usr_status").toString());
        System.out.println(status);
        switch (status) {
            case 1:
                response.sendRedirect("../owner/dashboard.jsp");
                break;
            case 2:
                response.sendRedirect("../user/dashboard.jsp");
                break;
            case 3:
                response.sendRedirect("../ca/dashboard.jsp");
                break;
            case 4:
                response.sendRedirect("../aa/dashboard.jsp");
                break;
            case 5:
                break;
            default:
                response.sendRedirect("../index.jsp");
                break;
        }
    }

    Connection con = null;
    Statement st = null;
    ResultSet rs = null;

%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Title -->
        <title>Cloud Dashboard | xACS </title>

        <!-- Required Meta Tags Always Come First -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">

        <!-- Favicon -->
        <link rel="shortcut icon" href="../assets/favicon/favicon.ico">
        <!-- Google Fonts -->
        <link rel="stylesheet" href="//fonts.googleapis.com/css?family=Open+Sans%3A400%2C300%2C500%2C600%2C700%7CPlayfair+Display%7CRoboto%7CRaleway%7CSpectral%7CRubik">
        <!-- CSS Global Compulsory -->
        <link rel="stylesheet" href="../assets/vendor/bootstrap/bootstrap.min.css">
        <!-- CSS Global Icons -->
        <link rel="stylesheet" href="../assets/vendor/icon-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="../assets/vendor/icon-line/css/simple-line-icons.css">
        <link rel="stylesheet" href="../assets/vendor/icon-etlinefont/style.css">
        <link rel="stylesheet" href="../assets/vendor/icon-line-pro/style.css">
        <link rel="stylesheet" href="../assets/vendor/icon-hs/style.css">

        <link rel="stylesheet" href="../assets/vendor/hs-admin-icons/hs-admin-icons.css">

        <link rel="stylesheet" href="../assets/vendor/animate.css">
        <link rel="stylesheet" href="../assets/vendor/malihu-scrollbar/jquery.mCustomScrollbar.min.css">

        <link rel="stylesheet" href="../assets/vendor/flatpickr/dist/css/flatpickr.min.css">
        <link rel="stylesheet" href="../assets/vendor/bootstrap-select/css/bootstrap-select.min.css">

        <link rel="stylesheet" href="../assets/vendor/chartist-js/chartist.min.css">
        <link rel="stylesheet" href="../assets/vendor/chartist-js-tooltip/chartist-plugin-tooltip.css">
        <link rel="stylesheet" href="../assets/vendor/fancybox/jquery.fancybox.min.css">

        <link rel="stylesheet" href="../assets/vendor/hamburgers/hamburgers.min.css">

        <!-- CSS Unify -->
        <link rel="stylesheet" href="../assets/css/unify-admin.css">

        <!-- CSS Customization -->
        <link rel="stylesheet" href="../assets/css/custom.css">

        <style type='text/css'>
            a.unclickable  { text-decoration: none; }
            a.unclickable:hover { cursor: not-allowed; }

            div.disabled {
                pointer-events: none;

                /* for "disabled" effect */
                opacity: 0.5;
                background: #CCC;


            }
            div.disabled:hover {cursor: not-allowed;}
        </style>
    </head>

    <body>
        <!-- Header -->
        <header id="js-header" class="u-header u-header--sticky-top">
            <div class="u-header__section u-header__section--admin-dark g-min-height-65">
                <nav class="navbar no-gutters g-pa-0">
                    <div class="col-auto d-flex flex-nowrap u-header-logo-toggler g-py-12">
                        <!-- Logo -->
                        <a href="/index.html" class="navbar-brand d-flex align-self-center g-hidden-xs-down g-line-height-1 py-0 g-mt-5">

                            <img class="img-fluid" src="../assets/img/logo/logo.png" height="32" alt="Image Description" style="height: 32px !important;">

                        </a>
                        <!-- End Logo -->

                        <!-- Sidebar Toggler -->
                        <a class="js-side-nav u-header__nav-toggler d-flex align-self-center ml-auto" href="#!" data-hssm-class="u-side-nav--mini u-sidebar-navigation-v1--mini" data-hssm-body-class="u-side-nav-mini" data-hssm-is-close-all-except-this="true" data-hssm-target="#sideNav">
                            <i class="hs-admin-align-left"></i>
                        </a>
                        <!-- End Sidebar Toggler -->
                    </div>

                    <!-- Top Search Bar -->
                    <!-- <form id="searchMenu" class="u-header--search col-sm g-py-12 g-ml-15--sm g-ml-20--md g-mr-10--sm" aria-labelledby="searchInvoker" action="#!">
                        <div class="input-group g-max-width-450">
                            <input class="form-control form-control-md g-rounded-4" type="text" placeholder="Enter search keywords">
                            <button type="submit" class="btn u-btn-outline-primary g-brd-none g-bg-transparent--hover g-pos-abs g-top-0 g-right-0 d-flex g-width-40 h-100 align-items-center justify-content-center g-font-size-18 g-z-index-2"><i class="hs-admin-search"></i>
                            </button>
                        </div>
                    </form>-->
                    <!-- End Top Search Bar -->

                    <!-- Messages/Notifications/Top Search Bar/Top User -->
                    <div class="col-auto d-flex g-py-12 g-pl-40--lg ml-auto">

                        <!-- Top Search Bar (Mobi) -->
                        <!--                        <a id="searchInvoker" class="g-hidden-sm-up text-uppercase u-header-icon-v1 g-pos-rel g-width-40 g-height-40 rounded-circle g-font-size-20" href="#!" aria-controls="searchMenu" aria-haspopup="true" aria-expanded="false" data-is-mobile-only="true" data-dropdown-event="click"
                                                   data-dropdown-target="#searchMenu" data-dropdown-type="css-animation" data-dropdown-duration="300" data-dropdown-animation-in="fadeIn" data-dropdown-animation-out="fadeOut">
                                                    <i class="hs-admin-search g-absolute-centered"></i>
                                                </a>-->
                        <!-- End Top Search Bar (Mobi) -->

                        <!-- Top User -->
                        <div class="col-auto d-flex g-pt-5 g-pt-0--sm g-pl-2 g-pl-2--sm">
                            <div class="g-pos-rel g-px-10--lg">
                                <a id="profileMenuInvoker" class="d-block" href="#!" aria-controls="profileMenu" aria-haspopup="true" aria-expanded="false" data-dropdown-event="click" data-dropdown-target="#profileMenu" data-dropdown-type="css-animation" data-dropdown-duration="300"
                                   data-dropdown-animation-in="fadeIn" data-dropdown-animation-out="fadeOut">
                                    <span class="g-pos-rel">
                                        <span class="u-badge-v2--xs u-badge--top-right g-hidden-sm-up g-bg-lightblue-v5 g-mr-5"></span>
                                        <img class="g-width-30 g-width-40--md g-height-30 g-height-40--md rounded-circle g-mr-10--sm" src="../assets/img/user.png" alt="Image description">
                                    </span>
                                    <span class="g-pos-rel g-top-2">
                                        <span class="g-hidden-sm-down"><%=session.getAttribute("usr_name")%></span>
                                        <i class="hs-admin-angle-down g-pos-rel g-top-2 g-ml-10"></i>
                                    </span>
                                </a>

                                <!-- Top User Menu -->
                                <ul id="profileMenu" class="g-pos-abs g-left-0 g-width-100x--lg g-nowrap g-font-size-14 g-py-20 g-mt-17 rounded" aria-labelledby="profileMenuInvoker">
                                    <li class="g-mb-10">
                                        <a class="media g-color-lightred-v2--hover g-py-5 g-px-20" href="#!">
                                            <span class="d-flex align-self-center g-mr-12">
                                                <i class="hs-admin-layout-accordion-list"></i>
                                            </span>
                                            <span class="media-body align-self-center">My Profile</span>
                                        </a>
                                    </li>
                                    <li class="mb-0">
                                        <a class="media g-color-lightred-v2--hover g-py-5 g-px-20" href="../signoutScript">
                                            <span class="d-flex align-self-center g-mr-12">
                                                <i class="hs-admin-shift-right"></i>
                                            </span>
                                            <span class="media-body align-self-center">Sign Out</span>
                                        </a>
                                    </li>
                                </ul>
                                <!-- End Top User Menu -->
                            </div>
                        </div>
                        <!-- End Top User -->
                    </div>
                    <!-- End Messages/Notifications/Top Search Bar/Top User -->

                </nav>


            </div>
        </header>
        <!-- End Header -->


        <main class="container-fluid px-0 g-pt-65">
            <div class="row no-gutters g-pos-rel g-overflow-x-hidden">
                <!-- Sidebar Nav -->
                <div id="sideNav" class="col-auto u-sidebar-navigation-v1 u-sidebar-navigation--dark">
                    <ul id="sideNavMenu" class="u-sidebar-navigation-v1-menu u-side-nav--top-level-menu g-min-height-100vh mb-0">
                        <!-- Dashboards -->
                        <li class="u-sidebar-navigation-v1-menu-item u-side-nav--top-level-menu-item has-active">
                            <a class="media u-side-nav--top-level-menu-link u-side-nav--hide-on-hidden g-px-15 g-py-12" href="dashboard.jsp">
                                <span class="d-flex align-self-center g-pos-rel g-font-size-18 g-mr-18">
                                    <i class="hs-admin-server"></i>
                                </span>
                                <span class="media-body align-self-center">Dashboard</span>
                            </a>
                        </li>
                        <li class="u-sidebar-navigation-v1-menu-item u-side-nav--top-level-menu-item">
                            <a class="media u-side-nav--top-level-menu-link u-side-nav--hide-on-hidden g-px-15 g-py-12" href="file_details.jsp">
                                <span class="d-flex align-self-center g-pos-rel g-font-size-18 g-mr-18">
                                    <i class="hs-admin-files"></i>
                                </span>
                                <span class="media-body align-self-center">File Details</span>
                            </a>
                        </li>
                        <li class="u-sidebar-navigation-v1-menu-item u-side-nav--top-level-menu-item">
                            <a class="media u-side-nav--top-level-menu-link u-side-nav--hide-on-hidden g-px-15 g-py-12" href="dl_details.jsp">
                                <span class="d-flex align-self-center g-pos-rel g-font-size-18 g-mr-18">
                                    <i class="hs-admin-download "></i>
                                </span>
                                <span class="media-body align-self-center">Download Details</span>
                            </a>
                        </li>
                    </ul>
                </div>
                <!-- End Sidebar Nav -->


                <div class="col g-bg-lightblue-v10-opacity-0_5 g-ml-45 g-ml-0--lg g-pb-65--md">
                    <div class="g-pa-20">

                        <div class="alert alert-danger alert-dismissible fade show" role="alert" id="sqlFailAlert" style="visibility: hidden; display: none;">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                            <h4 class="h5">
                                <i class="fa fa-minus-circle"></i>
                                Unable to connect to SQL Server!
                            </h4>
                            <!--Change a few things up and try submitting again.-->
                        </div>

                        <div class="alert alert-danger alert-dismissible fade show" role="alert" id="ntFailAlert" style="visibility: hidden; display: none;">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                            <h4 class="h5">
                                <i class="fa fa-minus-circle"></i>
                                Please make sure 0 &lt; <i>t</i> &lt; <i>n</i> &lt; 11! <small>(system testing constraints)</small>
                            </h4>
                            <!--Change a few things up and try submitting again.-->
                        </div>

                        <div class="alert alert-danger alert-dismissible fade show" role="alert" id="acsInfoFailAlert" style="visibility: hidden; display: none;">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                            <h4 class="h5">
                                <i class="fa fa-minus-circle"></i>
                                Unable to initialize ACS!
                            </h4>
                            <!--Change a few things up and try submitting again.-->
                        </div>

                        <div class="alert alert-danger alert-dismissible fade show" role="alert" id="AAIniFailAlert" style="visibility: hidden; display: none;">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                            <h4 class="h5">
                                <i class="fa fa-minus-circle"></i>
                                Unable to retrive mock user data!
                            </h4>
                            <!--Change a few things up and try submitting again.-->
                        </div>

                        <div class="row">

                            <div class="col-xl-3">
                                <!-- User Card -->
                                <div class="card g-brd-gray-light-v7 u-card-v1 u-card-v1 text-center g-pt-90 g-pt-110--md g-pb-50 g-mb-30">
                                    <header class="g-mb-30">
                                        <img class="img-fluid rounded-circle g-width-125 g-height-125 g-mb-14" src="../assets/img/user.png" alt="Image description">
                                        <h3 class="g-font-weight-300 g-font-size-22 g-color-black g-mb-2"><%= session.getAttribute("usr_name")%></h3>
                                        <em class="g-font-style-normal g-font-weight-300 g-color-gray-dark-v6"><%= session.getAttribute("usr_role")%></em>
                                    </header>

                                </div>
                                <!-- End User Card -->
                            </div>



                            <!-- Statistic Card -->
                            <div class="col-xl-9">
                                <!-- Statistic Card -->
                                <div class="card g-brd-gray-light-v7 u-card-v1 g-pa-15 g-pa-25-30--md g-mb-30">
                                    <header class="media g-mb-30">
                                        <h3 class="d-flex align-self-center text-uppercase g-font-size-12 g-font-size-default--md g-color-black mb-0">Statistics</h3>

                                        <ul class="list-unstyled d-flex align-self-center pull-right" style="margin-bottom: 0px !important; margin-left: 20px !important;" id="">
                                            <li class="media">
                                                <div class="d-flex align-self-center g-mr-8">
                                                    <span class="u-badge-v2--md g-pos-stc g-transform-origin--top-left g-bg-lightblue-v4"></span>
                                                </div>

                                                <div class="media-body align-self-center g-font-size-12 g-font-size-default--md">DATA1</div>
                                            </li>
                                            <li class="media g-ml-5 g-ml-35--md">
                                                <div class="d-flex align-self-center g-mr-8">
                                                    <span class="u-badge-v2--md g-pos-stc g-transform-origin--top-left g-bg-darkblue-v2"></span>
                                                </div>

                                                <div class="media-body align-self-center g-font-size-12 g-font-size-default--md">DATA2</div>
                                            </li>
                                        </ul>
                                    </header>
                                    
                                    <section>
                                        <div class="chart-container">
                                            <canvas id="line-chart" width="100%" height="281px"></canvas>
                                        </div>
                                    </section>
                                </div>
                                <!-- End Statistic Card -->
                            </div>
                            <!-- End Statistic Card -->

                        </div>

                        <%                            boolean reset = false;

                            try {
                                con = DbConnection.getConnection();
                                st = con.createStatement();

                                //rs = st.executeQuery("SELECT COUNT(*) as Visits FROM reg WHERE joinDate >= ( CURDATE() - INTERVAL 7 DAY )");
                                rs = st.executeQuery("SELECT * FROM reg");
                                if (!(rs.next())) {
                                    st = con.createStatement();
                                    rs = st.executeQuery("SELECT * FROM request");
                                    if (!(rs.next())) {
                                        st = con.createStatement();
                                        rs = st.executeQuery("SELECT * FROM download");
                                        if (!(rs.next())) {
                                            st = con.createStatement();
                                            rs = st.executeQuery("SELECT * FROM file_upload");
                                            if (!(rs.next())) {
                                                st = con.createStatement();
                                                rs = st.executeQuery("SELECT * FROM acs_info");
                                                if (!(rs.next())) {
                                                    reset = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            if (reset == false) {
                                try {
                                    con = DbConnection.getConnection();
                                    st = con.createStatement();
                                    rs = st.executeQuery("SELECT * FROM acs_info WHERE id = 1");
                                    rs.next();
                                    name = rs.getString("sysname");
                                    nC = rs.getInt("n");
                                    tC = rs.getInt("t");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                sysText = name + " – <i>n</i> (" + nC + ") – <i>t</i> (" + tC + ")";
                            }

                            String cclr = "", systext = "", sysIniStyle = "";

                            if (reset == true) {
                                systext = "System Reset";
                                cclr = "g-color-yellow";
                                sysIniStyle = "";
                            } else {
                                systext = "System Online";
                                cclr = "g-color-green";
                                sysIniStyle = "disabled";
                            }


                        %>

                        <div class="row">
                            <div class="col-4 g-mb-30">
                                <div class="d-flex align-items-center g-bg-white h-100 g-brd-around g-brd-gray-light-v7 g-rounded-2 g-px-15 g-py-20 u-card-v1">
                                    <div class="w-100">
                                        <header class="d-flex justify-content-end g-pos-rel g-mb-3">
                                            <div class="g-pos-rel">
                                                <a id="dropDown1Invoker" class="u-link-v5 g-line-height-0 g-font-size-24 g-color-gray-light-v6 g-color-lightblue-v3--hover" href="#!" aria-controls="dropDown1" aria-haspopup="true" aria-expanded="false" data-dropdown-event="click" data-dropdown-target="#dropDown1" data-dropdown-type="jquery-slide">
                                                    <i class="hs-admin-more-alt"></i>
                                                </a>

                                                <div id="dropDown1" class="u-shadow-v31 g-pos-abs g-right-0 g-z-index-3 g-bg-white u-dropdown--jquery-slide u-dropdown--hidden" aria-labelledby="dropDown2Invoker" style="display: none;">
                                                    <ul class="list-unstyled g-nowrap mb-0">
                                                        <li>
                                                            <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--hover g-font-size-12 g-font-size-default--md g-color-gray-dark-v6 g-px-25 g-py-14" href="../cloud_reset">
                                                                <i class="hs-admin-package g-font-size-18 g-color-gray-light-v6 g-mr-10 g-mr-15--md"></i>
                                                                Reset
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </header>

                                        <section class="text-center g-mb-15">
                                            <div class="u-icon-v2 rounded-circle g-brd-gray-light-v8 g-bg-gray-light-v8">
                                                <i class="g-font-size-0">
                                                    <img class="img-fluid" src="../assets/img/icons/set-1/01.png" alt="Image description">
                                                </i>
                                            </div>
                                            <h4 class="g-line-height-1 g-font-weight-300 g-font-size-default <%=cclr%> mb-0 g-font-weight-600 g-pt-15"><%=systext%></h4>
                                        </section>

                                        <footer class="text-center g-mb-20">
                                            <h4 class="g-line-height-1 g-font-weight-300 g-font-size-default g-color-black mb-0 g-font-weight-600"><%=sysText%></h4>
                                        </footer>
                                    </div>
                                </div>
                            </div>

                            <div class="col-8">
                                <!-- Statistic Card -->
                                <div class="g-bg-white g-brd-around g-brd-gray-light-v7 g-rounded-2 g-px-15 g-py-20 u-card-v1 <%=sysIniStyle%>">
                                    <h3 class="text-uppercase g-font-size-12 g-font-size-default--md g-color-black g-pb-10">INITIALIZE SYSTEM</h3>

                                    <form action="../cloud_initialize">
                                        <div class="row" style="padding-bottom: 11px !important">
                                            <div class="col-3">
                                                <!-- Basic v.3 -->
                                                <div>
                                                    <h4 class="h6 g-font-weight-400 g-color-black g-mb-20">No. of AAs ( <i>n</i>   )<br/><small>System Testing Limit: 10</small></h4>

                                                    <div class="form-group mb-0">
                                                        <div class="js-quantity input-group u-quantity--v2 g-width-110 g-brd-none">
                                                            <div class="js-minus input-group-addon d-flex align-items-center g-width-30 g-height-30 g-font-size-10 g-color-gray-light-v11 g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-5">
                                                                <i class="hs-admin-minus"></i>
                                                            </div>
                                                            <input class="js-result form-control g-color-gray-dark-v6 rounded-0 p-0" name="nAA" type="text" value="0" readonly>
                                                            <div class="js-plus input-group-addon d-flex align-items-center g-width-30 g-height-30 g-font-size-10 g-color-gray-light-v11 g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-5">
                                                                <i class="hs-admin-plus"></i>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- End Basic v.3 -->


                                            </div>
                                            <div class="col-3">
                                                <!-- Basic v.3 -->
                                                <div>
                                                    <h4 class="h6 g-font-weight-400 g-color-black g-mb-20">Threshold ( <i>t</i>   )<br/><small>Important: <i>t</i> &lt;= <i>n</i></small></h4>

                                                    <div class="form-group mb-0">
                                                        <div class="js-quantity input-group u-quantity--v2 g-width-110 g-brd-none">
                                                            <div class="js-minus input-group-addon d-flex align-items-center g-width-30 g-height-30 g-font-size-10 g-color-gray-light-v11 g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-5">
                                                                <i class="hs-admin-minus"></i>
                                                            </div>
                                                            <input class="js-result form-control g-color-gray-dark-v6 rounded-0 p-0" name="tAA" type="text" value="0" readonly>
                                                            <div class="js-plus input-group-addon d-flex align-items-center g-width-30 g-height-30 g-font-size-10 g-color-gray-light-v11 g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-5">
                                                                <i class="hs-admin-plus"></i>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- End Basic v.3 -->


                                            </div>

                                            <div class="col-3">
                                                <div class="form-group g-mb-0">
                                                    <label class="u-check g-pl-25">
                                                        <input class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" name="gen" checked="" value="1" type="radio">
                                                        <div class="u-check-icon-font g-absolute-centered--y g-left-0">
                                                            <i class="fa" data-check-icon="" data-uncheck-icon=""></i>
                                                        </div>
                                                        Generate <i>n</i> AAs
                                                    </label>
                                                </div>

                                                <div class="form-group g-mb-0">
                                                    <label class="u-check g-pl-25">
                                                        <input class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" name="gen" value="2" type="radio">
                                                        <div class="u-check-icon-font g-absolute-centered--y g-left-0">
                                                            <i class="fa" data-check-icon="" data-uncheck-icon=""></i>
                                                        </div>
                                                        Generate <i>t</i> AAs
                                                    </label>
                                                </div>
                                                <small>
                                                    The system will auto-generate AA details.
                                                </small>
                                            </div>

                                            <div class="col-3 align-self-center text-center">
                                                <button class="btn btn-md u-btn-primary rounded g-py-13 g-px-25" type="submit">Initialize</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>

                        </div>

                    </div>

                    <!-- Footer -->
                    <footer id="footer" class="u-footer--bottom-sticky g-bg-white g-color-gray-dark-v6 g-brd-top g-brd-gray-light-v7 g-pa-20">
                        <div class="row align-items-center">
                            <!-- Footer Nav -->
                            <div class="col-md-8 g-mb-10 g-mb-0--md">
                                <ul class="list-inline text-center text-md-left mb-0">
                                    <li class="list-inline-item">
                                        <a class="g-color-gray-dark-v6 g-color-lightblue-v3--hover" href="#!">FAQ</a>
                                    </li>
                                    <li class="list-inline-item">
                                        <span class="g-color-gray-dark-v6">|</span>
                                    </li>
                                    <li class="list-inline-item">
                                        <a class="g-color-gray-dark-v6 g-color-lightblue-v3--hover" href="#!">Support</a>
                                    </li>
                                    <li class="list-inline-item">
                                        <span class="g-color-gray-dark-v6">|</span>
                                    </li>
                                    <li class="list-inline-item">
                                        <a class="g-color-gray-dark-v6 g-color-lightblue-v3--hover" href="#!">Contact Us</a>
                                    </li>
                                </ul>
                            </div>
                            <!-- End Footer Nav -->

                            <!-- Footer Copyrights -->
                            <div class="col-md-4 text-center text-md-right">
                                <small class="d-block g-font-size-default">&copy; 2018 xACS. All Rights Reserved.</small>
                            </div>
                            <!-- End Footer Copyrights -->
                        </div>
                    </footer>
                    <!-- End Footer -->
                </div>
            </div>
        </main>

        <!-- JS Global Compulsory -->
        <script src="../assets/vendor/jquery/jquery.min.js"></script>
        <script src="../assets/vendor/jquery-migrate/jquery-migrate.min.js"></script>

        <script src="../assets/vendor/popper.min.js"></script>
        <script src="../assets/vendor/bootstrap/bootstrap.min.js"></script>

        <script src="../assets/vendor/cookiejs/jquery.cookie.js"></script>


        <!-- jQuery UI Core -->
        <script src="../assets/vendor/jquery-ui/ui/widget.js"></script>
        <script src="../assets/vendor/jquery-ui/ui/version.js"></script>
        <script src="../assets/vendor/jquery-ui/ui/keycode.js"></script>
        <script src="../assets/vendor/jquery-ui/ui/position.js"></script>
        <script src="../assets/vendor/jquery-ui/ui/unique-id.js"></script>
        <script src="../assets/vendor/jquery-ui/ui/safe-active-element.js"></script>

        <!-- jQuery UI Helpers -->
        <script src="../assets/vendor/jquery-ui/ui/widgets/menu.js"></script>
        <script src="../assets/vendor/jquery-ui/ui/widgets/mouse.js"></script>

        <!-- jQuery UI Widgets -->
        <script src="../assets/vendor/jquery-ui/ui/widgets/datepicker.js"></script>

        <!-- JS Plugins Init. -->
        <script src="../assets/vendor/appear.js"></script>
        <script src="../assets/vendor/bootstrap-select/js/bootstrap-select.min.js"></script>
        <script src="../assets/vendor/flatpickr/dist/js/flatpickr.min.js"></script>
        <script src="../assets/vendor/malihu-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="../assets/vendor/chartist-js/chartist.min.js"></script>
        <script src="../assets/vendor/chartist-js-tooltip/chartist-plugin-tooltip.js"></script>
        <script src="../assets/vendor/fancybox/jquery.fancybox.min.js"></script>


        <!-- jQuery UI Helpers -->
        <script src="../assets/vendor/jquery-ui/ui/widgets/menu.js"></script>
        <script src="../assets/vendor/jquery-ui/ui/widgets/mouse.js"></script>

        <!-- jQuery UI Widgets -->
        <script src="../assets/vendor/jquery-ui/ui/widgets/slider.js"></script>

        <!-- JS Unify -->
        <script src="../assets/js/hs.core.js"></script>
        <script src="../assets/js/components/hs.side-nav.js"></script>
        <script src="../assets/js/helpers/hs.hamburgers.js"></script>
        <script src="../assets/js/components/hs.range-datepicker.js"></script>
        <script src="../assets/js/components/hs.datepicker.js"></script>
        <script src="../assets/js/components/hs.dropdown.js"></script>
        <script src="../assets/js/components/hs.scrollbar.js"></script>
        <script src="../assets/js/components/hs.area-chart.js"></script>
        <script src="../assets/js/components/hs.donut-chart.js"></script>
        <script src="../assets/js/components/hs.bar-chart.js"></script>
        <script src="../assets/js/helpers/hs.focus-state.js"></script>
        <script src="../assets/js/components/hs.popup.js"></script>
        <script src="../assets/js/components/hs.area-chart.js"></script>
        <script src="../assets/js/components/hs.range-datepicker.js"></script>
        <script src="../assets/js/components/hs.count-qty.js"></script>
        <script src="../assets/js/helpers/hs.focus-state.js"></script>
        <script src="../assets/js/components/hs.slider.js"></script>

        <!-- JS Custom -->
        <script src="../assets/js/custom.js"></script>
        <script src="../assets/js/Chart.js"></script>

        <!-- JS Plugins Init. -->
        <script>
            $(document).on('ready', function () {

                // initialization of sidebar navigation component
                $.HSCore.components.HSSideNav.init('.js-side-nav');

                // initialization of hamburger
                $.HSCore.helpers.HSHamburgers.init('.hamburger');

                // initialization of HSDropdown component
                $.HSCore.components.HSDropdown.init($('[data-dropdown-target]'), {dropdownHideOnScroll: false});


                // initialization of range datepicker
                //                $.HSCore.components.HSRangeDatepicker.init('#rangeDatepicker, #rangeDatepicker2, #rangeDatepicker3');

                // initialization of custom scrollbar
                $.HSCore.components.HSScrollBar.init($('.js-custom-scroll'));



                // initialization of forms
                $.HSCore.components.HSCountQty.init('.js-quantity');


                // initialization of charts
                $.HSCore.components.HSAreaChart.init('.js-area-chart');
            });
        </script>

        <script>
            $(document).ready(function () {

                if (window.location.href.indexOf('#sqlFail') !== -1 || !(<%=sqls.stat%>)) {
                    $('#sqlFailAlert').css({
                        'visibility': 'visible',
                        'display': 'block'
                    });
                    $('#sqlFailAlert').show();
                } else {
                    $('#sqlFailAlert').hide();
                }

                if (window.location.href.indexOf('#ntFail') !== -1) {
                    $('#ntFailAlert').css({
                        'visibility': 'visible',
                        'display': 'block'
                    });
                    $('#ntFailAlert').show();
                } else {
                    $('#ntFailAlert').hide();
                }

                if (window.location.href.indexOf('#acsInfoFail') !== -1) {
                    $('#acsInfoFailAlert').css({
                        'visibility': 'visible',
                        'display': 'block'
                    });
                    $('#acsInfoFailAlert').show();
                } else {
                    $('#acsInfoFailAlert').hide();
                }

                if (window.location.href.indexOf('#AAIniFail') !== -1) {
                    $('#AAIniFailAlert').css({
                        'visibility': 'visible',
                        'display': 'block'
                    });
                    $('#AAIniFailAlert').show();
                } else {
                    $('#AAIniFailAlert').hide();
                }

            });

        </script>

        <script>

            new Chart(document.getElementById("line-chart"), {
                type: 'line',
                options: {
                    legend: {
                        display: false
                    },
                    maintainAspectRatio: false
                }
            });
        </script>
    </body>

</html>

