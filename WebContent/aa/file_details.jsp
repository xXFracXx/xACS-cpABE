<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<%@page import="java.math.BigInteger"%>
<%@page import="java.security.MessageDigest"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="DBcon.sqlStatus"%>
<%@page import="DBcon.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>

<%
    sqlStatus sqls = new sqlStatus();
    sqls.stat = true;

    if (null == session.getAttribute("logdIn") || Integer.parseInt(session.getAttribute("logdIn").toString()) != 1) {
        response.sendRedirect("../index.jsp");

    } else {
        int status = Integer.parseInt(session.getAttribute("usr_status").toString());

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
                break;
            case 5:
                response.sendRedirect("../cloud/dashboard.jsp");
                break;
            default:
                response.sendRedirect("../index.jsp");
                break;
        }
    }

%>
    
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Title -->
        <title>File Details - AA | xACS </title>

        <!-- Required Meta Tags Always Come First -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">

        <!-- Favicon -->
        <link rel="shortcut icon" href="../assets/favicon/favicon.ico">
        <!-- Google Fonts -->
        <link rel="stylesheet" href="//fonts.googleapis.com/css?family=Open+Sans%3A400%2C300%2C500%2C600%2C700%7CPlayfair+Display%7CRoboto%7CRaleway%7CSpectral%7CRubik">
        <!--<link rel="stylesheet" href="../assets/css/gFonts.css">-->
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
                                        <span class="g-hidden-sm-down"><%= session.getAttribute("usr_name")%></span>
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
                        <li class="u-sidebar-navigation-v1-menu-item u-side-nav--top-level-menu-item">
                            <a class="media u-side-nav--top-level-menu-link u-side-nav--hide-on-hidden g-px-15 g-py-12" href="dashboard.jsp">
                                <span class="d-flex align-self-center g-pos-rel g-font-size-18 g-mr-18">
                                    <i class="hs-admin-server"></i>
                                </span>
                                <span class="media-body align-self-center">Dashboard</span>
                            </a>
                        </li>
                        <li class="u-sidebar-navigation-v1-menu-item u-side-nav--top-level-menu-item has-active">
                            <a class="media u-side-nav--top-level-menu-link u-side-nav--hide-on-hidden g-px-15 g-py-12" href="file_details.jsp">
                                <span class="d-flex align-self-center g-pos-rel g-font-size-18 g-mr-18">
                                    <i class="hs-admin-agenda"></i>
                                </span>
                                <span class="media-body align-self-center">File Details</span>
                            </a>
                        </li>
                        <li class="u-sidebar-navigation-v1-menu-item u-side-nav--top-level-menu-item">
                            <a class="media u-side-nav--top-level-menu-link u-side-nav--hide-on-hidden g-px-15 g-py-12" href="user_requests.jsp">
                                <span class="d-flex align-self-center g-pos-rel g-font-size-18 g-mr-18">
                                    <i class="hs-admin-receipt"></i>
                                </span>
                                <span class="media-body align-self-center">User Requests</span>
                            </a>
                        </li>
                    </ul>
                </div>
                <!-- End Sidebar Nav -->


                <div class="col g-bg-lightblue-v10-opacity-0_5 g-ml-45 g-ml-0--lg g-pb-65--md">
                    <!-- Breadcrumb-v1 -->
                    <div class="g-hidden-sm-down g-bg-gray-light-v8 g-pa-20">
                        <ul class="u-list-inline g-color-gray-dark-v6">

                            <li class="list-inline-item g-mr-10">
                                <a class="u-link-v5 g-color-gray-dark-v6 g-color-lightblue-v3--hover g-valign-middle" href="dashboard.jsp">Attribute Authority</a>
                                <i class="hs-admin-angle-right g-font-size-12 g-color-gray-light-v6 g-valign-middle g-ml-10"></i>
                            </li>

                            <li class="list-inline-item">
                                <span class="g-valign-middle">File Details</span>
                            </li>
                        </ul>
                    </div>
                    <!-- End Breadcrumb-v1 -->
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

                        
                        <div class="row">
                            <div class="col-auto g-width-280">
                                <h1 class="g-font-weight-500 g-font-size-28 g-color-black g-mb-20">File Details</h1>
                            </div>

                            <div class="col">
                                <div class="media g-py-3">
                                    <div class="d-flex align-self-center align-items-center">
                                        <span class="g-hidden-sm-down g-color-gray-dark-v6 g-mr-12 g-pt-10" id="showing">Showing # files</span>

                                        
                                    </div>

                                    <div class="d-flex align-self-center align-items-center ml-auto">
                                        <ul class="list-inline mb-0">
                                            <li class="list-inline-item g-valign-middle">
                                                <a class="d-flex align-items-center u-link-v5 g-font-size-18 g-color-gray-light-v6 g-color-lightblue-v3--active active" href="#!">
                                                    <i class="hs-admin-layout-grid-3 g-mr-10"></i>
                                                    <span class="g-hidden-sm-down g-font-size-default g-color-gray-dark-v6 g-color-black--active active">Grid View</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-auto g-width-280--md">
                                <ul class="list-unstyled mb-0">
                                    <li class="g-mb-10">
                                        <a class="media u-link-v5 g-bg-gray-light-v8--active g-bg-gray-light-v8--hover g-font-size-16 g-color-gray-dark-v6 g-rounded-2 g-px-20 g-py-12 active" href="#!">
                                            <span class="d-flex align-self-center g-font-size-18 g-color-gray-light-v6 g-mr-20">
                                                <i class="hs-admin-folder"></i>
                                            </span>
                                            <span class="media-body align-self-center">All Files</span>
                                        </a>
                                    </li>
                                    <li class="g-mb-10">
                                        <a class="media u-link-v5 g-bg-gray-light-v8--active g-bg-gray-light-v8--hover g-font-size-16 g-color-gray-dark-v6 g-rounded-2 g-px-20 g-py-12" href="#!">
                                            <span class="d-flex align-self-center g-font-size-18 g-color-gray-light-v6 g-mr-20">
                                                <i class="hs-admin-files"></i>
                                            </span>
                                            <span class="media-body align-self-center">Recent</span>
                                        </a>
                                    </li>
                                    <li class="g-mb-10">
                                        <a class="media u-link-v5 g-bg-gray-light-v8--active g-bg-gray-light-v8--hover g-font-size-16 g-color-gray-dark-v6 g-rounded-2 g-px-20 g-py-12" href="#!">
                                            <span class="d-flex align-self-center g-font-size-18 g-color-gray-light-v6 g-mr-20">
                                                <i class="hs-admin-heart"></i>
                                            </span>
                                            <span class="media-body align-self-center">Favourites</span>
                                        </a>
                                    </li>
                                    <li class="g-mb-10">
                                        <a class="media u-link-v5 g-bg-gray-light-v8--active g-bg-gray-light-v8--hover g-font-size-16 g-color-gray-dark-v6 g-rounded-2 g-px-20 g-py-12" href="#!">
                                            <span class="d-flex align-self-center g-font-size-18 g-color-gray-light-v6 g-mr-20">
                                                <i class="hs-admin-trash"></i>
                                            </span>
                                            <span class="media-body align-self-center">Trash</span>
                                        </a>
                                    </li>
                                </ul>

                                <hr class="d-flex g-brd-gray-light-v7 g-my-30">

                                <!--                                <ul class="list-unstyled mb-0">
                                                                    <li class="g-mb-10">
                                                                        <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--active g-bg-gray-light-v8--hover g-font-size-default g-color-gray-dark-v6 g-rounded-2 g-px-20 g-py-12" href="#!">
                                                                            <span class="u-badge-v2--md g-pos-stc g-transform-origin--top-left g-bg-darkblue-v2 g-mr-25"></span>
                                                                            <span class="g-line-height-1_2 g-color-gray-dark-v6">Employees</span>
                                                                        </a>
                                                                    </li>
                                                                    <li class="g-mb-10">
                                                                        <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--active g-bg-gray-light-v8--hover g-font-size-default g-color-gray-dark-v6 g-rounded-2 g-px-20 g-py-12" href="#!">
                                                                            <span class="u-badge-v2--md g-pos-stc g-transform-origin--top-left g-bg-teal-v2 g-mr-25"></span>
                                                                            <span class="g-line-height-1_2 g-color-gray-dark-v6">Family</span>
                                                                        </a>
                                                                    </li>
                                                                    <li class="g-mb-10">
                                                                        <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--active g-bg-gray-light-v8--hover g-font-size-default g-color-gray-dark-v6 g-rounded-2 g-px-20 g-py-12" href="#!">
                                                                            <span class="u-badge-v2--md g-pos-stc g-transform-origin--top-left g-bg-lightred-v2 g-mr-25"></span>
                                                                            <span class="g-line-height-1_2 g-color-gray-dark-v6">Work</span>
                                                                        </a>
                                                                    </li>
                                                                    <li class="g-mb-10">
                                                                        <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--active g-bg-gray-light-v8--hover g-font-size-default g-color-gray-dark-v6 g-rounded-2 g-px-20 g-py-12" href="#!">
                                                                            <span class="u-badge-v2--xl g-pos-rel g-transform-origin--top-left g-bg-lightblue-v3 g-font-size-10 g-color-white g-mr-20">
                                                                                <i class="hs-admin-plus g-absolute-centered"></i>
                                                                            </span>
                                                                            <span class="g-line-height-1_2 g-color-gray-dark-v6">Add New</span>
                                                                        </a>
                                                                    </li>
                                                                </ul>-->
                            </div>

                            <div class="col-md">
                                <div class="row">

                                    <%
                                        Connection con = null;
                                        Statement st = null;
                                        ResultSet rs = null;
                                        
                                        int rsCount = 0;

                                        try {
                                            con = DbConnection.getConnection();
                                            st = con.createStatement();
                                            rs = st.executeQuery("select *, OCTET_LENGTH(encFile) as size from file_upload");
                                            
                                            while (rs.next()) {
                                                String fileName = rs.getString("filename");
                                                String basename = FilenameUtils.getBaseName(fileName);
                                                String extension = FilenameUtils.getExtension(fileName);
                                                //extension = "";
                                                MessageDigest m = MessageDigest.getInstance("MD5");
                                                m.update(extension.getBytes(), 0, extension.length());
                                                String md5 = new BigInteger(1, m.digest()).toString(16);
                                                String hexColor = "#" + md5.substring(0, 6);

                                                long size = rs.getLong("size");
                                                String size_stirng = "";
                                                if (size <= 0) {
                                                    size_stirng = "0";
                                                }
                                                
                                                final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
                                                int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
                                                size_stirng = new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
                                                
                                                rsCount++;

                                    %>


                                    <div class="col-md-3 g-mb-30">
                                        <div class="d-flex flex-wrap h-100 g-brd-around g-brd-gray-light-v7 g-rounded-2 u-card-v1 g-pa-25">
                                            <header class="w-100 align-self-start text-right justify-content-end g-pos-rel g-mb-10">
                                                <span class="pull-left g-font-size-20 align-self-end g-line-height-1 g-font-weight-400 g-color-primary g-pt-10">#</span>
                                                <span class="pull-left g-font-size-32 g-line-height-1 g-font-weight-500 g-color-primary g-mb-10"><%=rs.getString("id")%></span>
                                                <!--<span class="pull-left u-tags-v1 text-center g-bg-white g-color-primary g-brd-around g-brd-primary g-rounded-50 g-py-2 g-px-10"></span>-->

                                                <!--                                                    <a id="dropDown1Invoker" class="u-link-v5 g-line-height-0 g-font-size-24 g-color-gray-light-v6 g-color-lightblue-v3--hover g-pr-10" href="#!" aria-controls="dropDown1" aria-haspopup="true" aria-expanded="false" data-dropdown-event="click" data-dropdown-target="#dropDown1" data-dropdown-type="jquery-slide">
                                                                                                        <i class="hs-admin-more-alt"></i>
                                                                                                    </a>
                                                
                                                                                                    <div id="dropDown1" class="u-shadow-v31 g-pos-abs g-right-0 g-bg-white u-dropdown--jquery-slide u-dropdown--hidden" aria-labelledby="dropDown1Invoker" style="display: none; right: 0px;">
                                                                                                        <ul class="list-unstyled g-nowrap mb-0">
                                                                                                            <li>
                                                                                                                <a class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--hover g-font-size-12 g-font-size-default--md g-color-gray-dark-v6 g-px-25 g-py-14" href="#!">
                                                                                                                    <i class="hs-admin-pencil g-font-size-18 g-color-gray-light-v6 g-mr-10 g-mr-15--md"></i>
                                                                                                                    Edit
                                                                                                                </a>
                                                                                                            </li>
                                                                                                        </ul>
                                                                                                    </div>-->

                                                <span class="pull-right text-center g-bg-white g-color-primary g-brd-around g-brd-primary g-rounded-50 g-py-2 g-px-10"><%=rs.getString("owner")%></span>


                                            </header>

                                            <section class="w-100 align-self-center text-center g-color-darkblue-v2 g-mb-30">
                                                <svg width="50px" height="64px" viewBox="0 0 58 74" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                                                <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                                <g transform="translate(-359.000000, -868.000000)" fill="<%=hexColor%>">
                                                <g transform="translate(284.000000, 730.000000)">
                                                <g transform="translate(0.000000, 76.000000)">
                                                <g transform="translate(75.000000, 62.000000)">
                                                <rect opacity="0.15" x="0" y="36" width="58" height="27"></rect>
                                                <path d="M40.0002553,0 L0,0 L0,74 L58,74 L58,17.9894 L40.0002553,0 Z M40.7234043,4.2106 L53.7869787,17.2666667 L40.7234043,17.2666667 L40.7234043,4.2106 Z M2.46808511,71.5333333 L2.46808511,2.46666667 L38.2553191,2.46666667 L38.2553191,19.7333333 L55.5319149,19.7333333 L55.5319149,71.5333333 L2.46808511,71.5333333 Z" fill-rule="nonzero"></path>
                                                <text font-size="14" font-weight="normal" letter-spacing="0.209999993">
                                                <tspan x="15.7045117" y="52">.<%=extension%></tspan>
                                                </text>
                                                </g>
                                                </g>
                                                </g>
                                                </g>
                                                </g>
                                                </svg>
                                            </section>

                                            <footer class="w-100 align-self-end text-center">
                                                <div class="d-flex align-items-center justify-content-center g-mb-10">
                                                    <span class="g-line-height-1_2 g-font-size-default g-font-weight-600 g-color-black"><%=basename%></span>
                                                </div>

                                                    <p class="g-font-weight-300 g-font-size-default g-color-gray-dark-v6 mb-0"><%=rs.getString("time")%> <strong>•</strong> <%=size_stirng%></p>
                                            </footer>
                                        </div>
                                    </div>

                                    <%
                                            }
                                        } catch (Exception ex) {
                                            sqls.stat = false;
                                            ex.printStackTrace();
                                        }

                                    %>

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
        <script src="../assets/vendor/datatables/media/js/jquery.dataTables.min.js"></script>
        <script src="../assets/vendor/datatables/media/js/dataTables.select.js"></script>

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
        <script src="../assets/js/components/hs.datatables.js"></script>

        <!-- JS Custom -->
        <script src="../assets/js/custom.js"></script>

        <!-- JS Plugins Init. -->
        <script>
            $(document).on('ready', function () {
                // initialization of custom select
                $('.js-select').selectpicker();

                // initialization of sidebar navigation component
                $.HSCore.components.HSSideNav.init('.js-side-nav');

                // initialization of hamburger
                $.HSCore.helpers.HSHamburgers.init('.hamburger');

                // initialization of HSDropdown component
                $.HSCore.components.HSDropdown.init($('[data-dropdown-target]'), {
                    dropdownHideOnScroll: false,
                    dropdownType: 'css-animation',
                    dropdownAnimationIn: 'fadeIn',
                    dropdownAnimationOut: 'fadeOut'
                });

                // initialization of custom scrollbar
                $.HSCore.components.HSScrollBar.init($('.js-custom-scroll'));

                // initialization of datatables
                $.HSCore.components.HSDatatables.init('.js-datatable');
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
                
                document.getElementById('showing').innerHTML = "Showing <%=rsCount%> files";

            });

        </script>

    </body>

</html>
