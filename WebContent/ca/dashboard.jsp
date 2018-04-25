<%@ page language="java" pageEncoding="UTF-8"%>

<%@page import="DBcon.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="DBcon.sqlStatus"%>

<%
	sqlStatus sqls = new sqlStatus();
	sqls.stat = true;

	String name = "", sysText = "";
	int nC = 0, tC = 0;

	if (null == session.getAttribute("logdIn")
			|| Integer.parseInt(session.getAttribute("logdIn").toString()) != 1) {
		response.sendRedirect("../index.jsp");

	} else {
		int status = Integer.parseInt(session.getAttribute("usr_status").toString());

		switch (status) {
			case 1 :
				response.sendRedirect("../owner/dashboard.jsp");
				break;
			case 2 :
				response.sendRedirect("../user/dashboard.jsp");
				break;
			case 3 :
				break;
			case 4 :
				response.sendRedirect("../aa/dashboard.jsp");
				break;
			case 5 :
				response.sendRedirect("../cloud/dashboard.jsp");
				break;
			default :
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
<title>CA Dashboard | xACS</title>

<!-- Required Meta Tags Always Come First -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="x-ua-compatible" content="ie=edge">

<!-- Favicon -->
<link rel="shortcut icon" href="../assets/favicon/favicon.ico">
<!-- Google Fonts -->
<link rel="stylesheet"
	href="//fonts.googleapis.com/css?family=Open+Sans%3A400%2C300%2C500%2C600%2C700%7CPlayfair+Display%7CRoboto%7CRaleway%7CSpectral%7CRubik">
<!-- CSS Global Compulsory -->
<link rel="stylesheet"
	href="../assets/vendor/bootstrap/bootstrap.min.css">
<!-- CSS Global Icons -->
<link rel="stylesheet"
	href="../assets/vendor/icon-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="../assets/vendor/icon-line/css/simple-line-icons.css">
<link rel="stylesheet" href="../assets/vendor/icon-etlinefont/style.css">
<link rel="stylesheet" href="../assets/vendor/icon-line-pro/style.css">
<link rel="stylesheet" href="../assets/vendor/icon-hs/style.css">

<link rel="stylesheet"
	href="../assets/vendor/hs-admin-icons/hs-admin-icons.css">

<link rel="stylesheet" href="../assets/vendor/animate.css">
<link rel="stylesheet"
	href="../assets/vendor/malihu-scrollbar/jquery.mCustomScrollbar.min.css">

<link rel="stylesheet"
	href="../assets/vendor/flatpickr/dist/css/flatpickr.min.css">
<link rel="stylesheet"
	href="../assets/vendor/bootstrap-select/css/bootstrap-select.min.css">

<link rel="stylesheet"
	href="../assets/vendor/chartist-js/chartist.min.css">
<link rel="stylesheet"
	href="../assets/vendor/chartist-js-tooltip/chartist-plugin-tooltip.css">
<link rel="stylesheet"
	href="../assets/vendor/fancybox/jquery.fancybox.min.css">

<link rel="stylesheet"
	href="../assets/vendor/hamburgers/hamburgers.min.css">

<!-- CSS Unify -->
<link rel="stylesheet" href="../assets/css/unify-admin.css">

<!-- CSS Customization -->
<link rel="stylesheet" href="../assets/css/custom.css">

<style type='text/css'>
a.unclickable {
	text-decoration: none;
}

a.unclickable:hover {
	cursor: not-allowed;
}

div.disabled {
	pointer-events: none;
	/* for "disabled" effect */
	opacity: 0.5;
	background: #CCC;
}

div.disabled:hover {
	cursor: not-allowed;
}
</style>
</head>

<body>
	<!-- Header -->
	<header id="js-header" class="u-header u-header--sticky-top">
		<div
			class="u-header__section u-header__section--admin-dark g-min-height-65">
			<nav class="navbar no-gutters g-pa-0">
				<div
					class="col-auto d-flex flex-nowrap u-header-logo-toggler g-py-12">
					<!-- Logo -->
					<a href="/index.html"
						class="navbar-brand d-flex align-self-center g-hidden-xs-down g-line-height-1 py-0 g-mt-5">

						<img class="img-fluid" src="../assets/img/logo/logo.png"
						height="32" alt="Image Description"
						style="height: 32px !important;">

					</a>
					<!-- End Logo -->

					<!-- Sidebar Toggler -->
					<a
						class="js-side-nav u-header__nav-toggler d-flex align-self-center ml-auto"
						href="#!"
						data-hssm-class="u-side-nav--mini u-sidebar-navigation-v1--mini"
						data-hssm-body-class="u-side-nav-mini"
						data-hssm-is-close-all-except-this="true"
						data-hssm-target="#sideNav"> <i class="hs-admin-align-left"></i>
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
							<a id="profileMenuInvoker" class="d-block" href="#!"
								aria-controls="profileMenu" aria-haspopup="true"
								aria-expanded="false" data-dropdown-event="click"
								data-dropdown-target="#profileMenu"
								data-dropdown-type="css-animation" data-dropdown-duration="300"
								data-dropdown-animation-in="fadeIn"
								data-dropdown-animation-out="fadeOut"> <span
								class="g-pos-rel"> <span
									class="u-badge-v2--xs u-badge--top-right g-hidden-sm-up g-bg-lightblue-v5 g-mr-5"></span>
									<img
									class="g-width-30 g-width-40--md g-height-30 g-height-40--md rounded-circle g-mr-10--sm"
									src="../assets/img/user.png" alt="Image description">
							</span> <span class="g-pos-rel g-top-2"> <span
									class="g-hidden-sm-down"><%=session.getAttribute("usr_name")%></span>
									<i class="hs-admin-angle-down g-pos-rel g-top-2 g-ml-10"></i>
							</span>
							</a>

							<!-- Top User Menu -->
							<ul id="profileMenu"
								class="g-pos-abs g-left-0 g-width-100x--lg g-nowrap g-font-size-14 g-py-20 g-mt-17 rounded"
								aria-labelledby="profileMenuInvoker">
								<li class="g-mb-10"><a
									class="media g-color-lightred-v2--hover g-py-5 g-px-20"
									href="#!"> <span class="d-flex align-self-center g-mr-12">
											<i class="hs-admin-layout-accordion-list"></i>
									</span> <span class="media-body align-self-center">My Profile</span>
								</a></li>
								<li class="mb-0"><a
									class="media g-color-lightred-v2--hover g-py-5 g-px-20"
									href="../signoutScript"> <span
										class="d-flex align-self-center g-mr-12"> <i
											class="hs-admin-shift-right"></i>
									</span> <span class="media-body align-self-center">Sign Out</span>
								</a></li>
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
		<div id="sideNav"
			class="col-auto u-sidebar-navigation-v1 u-sidebar-navigation--dark">
			<ul id="sideNavMenu"
				class="u-sidebar-navigation-v1-menu u-side-nav--top-level-menu g-min-height-100vh mb-0">
				<!-- Dashboards -->
				<li
					class="u-sidebar-navigation-v1-menu-item u-side-nav--top-level-menu-item has-active">
					<a
					class="media u-side-nav--top-level-menu-link u-side-nav--hide-on-hidden g-px-15 g-py-12"
					href="dashboard.jsp"> <span
						class="d-flex align-self-center g-pos-rel g-font-size-18 g-mr-18">
							<i class="hs-admin-server"></i>
					</span> <span class="media-body align-self-center">Dashboard</span>
				</a>
				</li>
				<li
					class="u-sidebar-navigation-v1-menu-item u-side-nav--top-level-menu-item">
					<a
					class="media u-side-nav--top-level-menu-link u-side-nav--hide-on-hidden g-px-15 g-py-12"
					href="auth_details.jsp"> <span
						class="d-flex align-self-center g-pos-rel g-font-size-18 g-mr-18">
							<i class="hs-admin-agenda"></i>
					</span> <span class="media-body align-self-center">Authorized
							Details</span>
				</a>
				</li>
				<li
					class="u-sidebar-navigation-v1-menu-item u-side-nav--top-level-menu-item">
					<a
					class="media u-side-nav--top-level-menu-link u-side-nav--hide-on-hidden g-px-15 g-py-12"
					href="auth_pending.jsp"> <span
						class="d-flex align-self-center g-pos-rel g-font-size-18 g-mr-18">
							<i class="hs-admin-pencil-alt"></i>
					</span> <span class="media-body align-self-center">Authorization
							Pending</span>
				</a>
				</li>
			</ul>
		</div>
		<!-- End Sidebar Nav -->


		<div
			class="col g-bg-lightblue-v10-opacity-0_5 g-ml-45 g-ml-0--lg g-pb-65--md">
			<div class="g-pa-20">

				<div class="alert alert-danger alert-dismissible fade show"
					role="alert" id="sqlFailAlert"
					style="visibility: hidden; display: none;">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="h5">
						<i class="fa fa-minus-circle"></i> Unable to connect to SQL
						Server!
					</h4>
					<!--Change a few things up and try submitting again.-->
				</div>

				<div class="alert alert-danger alert-dismissible fade show"
					role="alert" id="ntFailAlert"
					style="visibility: hidden; display: none;">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="h5">
						<i class="fa fa-minus-circle"></i> Please make sure 0 &lt; <i>t</i>
						&lt; <i>n</i> &lt; 11! <small>(system testing constraints)</small>
					</h4>
					<!--Change a few things up and try submitting again.-->
				</div>

				<div class="alert alert-danger alert-dismissible fade show"
					role="alert" id="acsInfoFailAlert"
					style="visibility: hidden; display: none;">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="h5">
						<i class="fa fa-minus-circle"></i> Unable to initialize ACS!
					</h4>
					<!--Change a few things up and try submitting again.-->
				</div>

				<div class="alert alert-danger alert-dismissible fade show"
					role="alert" id="AAIniFailAlert"
					style="visibility: hidden; display: none;">
					<button type="button" class="close" data-dismiss="alert"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="h5">
						<i class="fa fa-minus-circle"></i> Unable to retrive mock user
						data!
					</h4>
					<!--Change a few things up and try submitting again.-->
				</div>

				<div class="row">

					<div class="col-xl-3">
						<!-- User Card -->
						<div
							class="card g-brd-gray-light-v7 u-card-v1 u-card-v1 text-center g-pt-90 g-pt-110--md g-pb-50 g-mb-30">
							<header class="g-mb-30">
								<img
									class="img-fluid rounded-circle g-width-125 g-height-125 g-mb-14"
									src="../assets/img/user.png" alt="Image description">
								<h3
									class="g-font-weight-300 g-font-size-22 g-color-black g-mb-2"><%=session.getAttribute("usr_name")%></h3>
								<em
									class="g-font-style-normal g-font-weight-300 g-color-gray-dark-v6"><%=session.getAttribute("usr_role")%></em>
							</header>

						</div>
						<!-- End User Card -->
					</div>



					<!-- Statistic Card -->
					<div class="col-xl-9">
						<!-- Statistic Card -->
						<div
							class="card g-brd-gray-light-v7 u-card-v1 g-pa-15 g-pa-25-30--md g-mb-30">
							<header class="media g-mb-30">
								<h3
									class="d-flex align-self-center text-uppercase g-font-size-12 g-font-size-default--md g-color-black mb-0">Account
									statistics</h3>

								<ul class="list-unstyled d-flex align-self-center pull-right"
									style="margin-bottom: 0px !important; margin-left: 20px !important;"
									id="">
									<li class="media g-ml-5 g-ml-35--md">
										<div class="d-flex align-self-center g-mr-8">
											<span
												class="u-badge-v2--md g-pos-stc g-transform-origin--top-left g-bg-darkblue-v2"></span>
										</div>

										<div
											class="media-body align-self-center g-font-size-12 g-font-size-default--md">Monthly
											Users Registered</div>
									</li>
								</ul>
							</header>

							<%
								con = null;
								st = null;
								rs = null;

								java.util.Calendar calendar = java.util.Calendar.getInstance();

								int[] month_visits_aa = new int[12];
								int[] month_visits_user = new int[12];
								int[] indexArray = new int[12];

								int curr_month = calendar.get(java.util.Calendar.MONTH);
								int month = curr_month;
								int t = 0;
								int t2 = 0;

								String month_list = "";

								for (int i = 0; i <= 11; i++) {
									month_visits_aa[i] = 0;
								}

								try {
									con = DbConnection.getConnection();
									st = con.createStatement();

									//rs = st.executeQuery("SELECT COUNT(*) as Visits FROM reg WHERE joinDate >= ( CURDATE() - INTERVAL 7 DAY )");
									rs = st.executeQuery(
											"SELECT YEAR(joinDate) AS 'Year', MONTH(joinDate) AS 'Month', COUNT(*) AS 'Visits1' FROM reg WHERE role = 'AA' GROUP BY MONTH(joinDate), YEAR(joinDate) ORDER BY  'Year', 'Month'");
									while (rs.next()) {
										month = Integer.parseInt(rs.getString("Month")) - 1;
										month_visits_aa[month] = Integer.parseInt(rs.getString("Visits1"));
										System.out.println(month_visits_aa[month] + "[aa]" + month);

									}

									curr_month = calendar.get(java.util.Calendar.MONTH);
									month = curr_month;

									for (int i = 0; i <= 11; i++) {
										month_visits_user[i] = 0;
									}
									
									con.close();

									con = DbConnection.getConnection();
									st = con.createStatement();

									rs = st.executeQuery(
											"SELECT YEAR(joinDate) AS 'Year', MONTH(joinDate) AS 'Month', COUNT(*) AS 'Visits2' FROM reg WHERE role = 'User' GROUP BY MONTH(joinDate), YEAR(joinDate) ORDER BY  'Year', 'Month'");
									while (rs.next()) {
										month = Integer.parseInt(rs.getString("Month")) - 1;
										month_visits_user[month] = Integer.parseInt(rs.getString("Visits2"));
										System.out.println(month_visits_user[month] + "[us]" + month);
									}

									//                                            
									curr_month = calendar.get(java.util.Calendar.MONTH);
									int temp_month = curr_month + 1;
									for (int i = 0; i < 12; i++) {
										indexArray[i] = temp_month;
										if (temp_month < 11) {
											temp_month++;
										} else if (temp_month == 11) {
											temp_month = 0;
										}
									}

									curr_month = calendar.get(java.util.Calendar.MONTH);

									switch (curr_month) {
										case 0 :
											month_list = "\"Feb\", \"Mar\", \"Apr\", \"May\", \"Jun\", \"Jul\", \"Aug\", \"Sep\", \"Oct\", \"Nov\", \"Dec 17\", \"Jan\"";
											break;
										case 1 :
											month_list = "\"Mar\", \"Apr\", \"May\", \"Jun\", \"Jul\", \"Aug\", \"Sep\", \"Oct\", \"Nov\", \"Dec 17\", \"Jan\", \"Feb\"";
											break;
										case 2 :
											month_list = "\"Apr\", \"May\", \"Jun\", \"Jul\", \"Aug\", \"Sep\", \"Oct\", \"Nov\", \"Dec 17\", \"Jan\", \"Feb\", \"Mar\"";
											break;
										case 3 :
											month_list = "\"May\", \"Jun\", \"Jul\", \"Aug\", \"Sep\", \"Oct\", \"Nov\", \"Dec 17\", \"Jan\", \"Feb\", \"Mar\", \"Apr\"";
											break;
										case 4 :
											month_list = "\"Jun\", \"Jul\", \"Aug\", \"Sep\", \"Oct\", \"Nov\", \"Dec 17\", \"Jan\", \"Feb\", \"Mar\", \"Apr\", \"May\"";
											break;
										case 5 :
											month_list = "\"Jul\", \"Aug\", \"Sep\", \"Oct\", \"Nov\", \"Dec 17\", \"Jan\", \"Feb\", \"Mar\", \"Apr\", \"May\", \"Jun\"";
											break;
										case 6 :
											month_list = "\"Aug\", \"Sep\", \"Oct\", \"Nov\", \"Dec 17\", \"Jan\", \"Feb\", \"Mar\", \"Apr\", \"May\", \"Jun\", \"Jul\"";
											break;
										case 7 :
											month_list = "\"Sep\", \"Oct\", \"Nov\", \"Dec 17\", \"Jan\", \"Feb\", \"Mar\", \"Apr\", \"May\", \"Jun\", \"Jul\", \"Aug\"";
											break;
										case 8 :
											month_list = "\"Oct\", \"Nov\", \"Dec 17\", \"Jan\", \"Feb\", \"Mar\", \"Apr\", \"May\", \"Jun\", \"Jul\", \"Aug\", \"Sep\"";
											break;
										case 9 :
											month_list = "\"Nov\", \"Dec 17\", \"Jan\", \"Feb\", \"Mar\", \"Apr\", \"May\", \"Jun\", \"Jul\", \"Aug\", \"Sep\", \"Oct\"";
											break;
										case 10 :
											month_list = "\"Dec 17\", \"Jan\", \"Feb\", \"Mar\", \"Apr\", \"May\", \"Jun\", \"Jul\", \"Aug\", \"Sep\", \"Oct\", \"Nov\"";
											break;
										case 11 :
											month_list = "\"Jan\", \"Feb\", \"Mar\", \"Apr\", \"May\", \"Jun\", \"Jul\", \"Aug\", \"Sep\", \"Oct\", \"Nov\", \"Dec\"";
											break;
										default :
											month_list = "\"err\", \"err\", \"err\", \"err\", \"err\", \"err\", \"err\", \"err\", \"err\", \"err\", \"err\", \"err\"";
											break;
									}
							%>

							<section>
								<div class="chart-container">
									<canvas id="line-chart" width="100%" height="281px"></canvas>
								</div>
							</section>

							<%
								} catch (Exception ex) {
									sqls.stat = false;
									ex.printStackTrace();
								} finally {
									con.close();
								}
							%>
						</div>
						<!-- End Statistic Card -->
					</div>
					<!-- End Statistic Card -->

				</div>

				<%
					boolean reset = false;

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
					} finally {
						con.close();
					}
					
					String pxPaddin = "0px";

					if (reset == false) {
						pxPaddin = "14px";
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
						} finally {
							con.close();
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
						<div
							class="d-flex align-items-center g-bg-white h-100 g-brd-around g-brd-gray-light-v7 g-rounded-2 g-px-15 g-py-20 u-card-v1">
							<div class="w-100">
								<header class="d-flex justify-content-end g-pos-rel g-mb-3">
									<div class="g-pos-rel">
										<a id="dropDown1Invoker"
											class="u-link-v5 g-line-height-0 g-font-size-24 g-color-gray-light-v6 g-color-lightblue-v3--hover"
											href="#!" aria-controls="dropDown1" aria-haspopup="true"
											aria-expanded="false" data-dropdown-event="click"
											data-dropdown-target="#dropDown1"
											data-dropdown-type="jquery-slide"> <i
											class="hs-admin-more-alt"></i>
										</a>

										<div id="dropDown1"
											class="u-shadow-v31 g-pos-abs g-right-0 g-z-index-3 g-bg-white u-dropdown--jquery-slide u-dropdown--hidden"
											aria-labelledby="dropDown2Invoker" style="display: none;">
											<ul class="list-unstyled g-nowrap mb-0">
												<li><a
													class="d-flex align-items-center u-link-v5 g-bg-gray-light-v8--hover g-font-size-12 g-font-size-default--md g-color-gray-dark-v6 g-px-25 g-py-14"
													href="../ca_reset"> <i
														class="hs-admin-package g-font-size-18 g-color-gray-light-v6 g-mr-10 g-mr-15--md"></i>
														Reset
												</a></li>
											</ul>
										</div>
									</div>
								</header>

								<section class="text-center g-mb-15">
									<div
										class="u-icon-v2 rounded-circle g-brd-gray-light-v8 g-bg-gray-light-v8">
										<i class="g-font-size-0"> <img class="img-fluid"
											src="../assets/img/icons/set-1/01.png"
											alt="Image description">
										</i>
									</div>
									<h4
										class="g-line-height-1 g-font-weight-300 g-font-size-default <%=cclr%> mb-0 g-font-weight-600 g-pt-15"><%=systext%></h4>
								</section>

								<footer class="text-center g-mb-20">
									<h4
										class="g-line-height-1 g-font-weight-300 g-font-size-default g-color-black mb-0 g-font-weight-600"><%=sysText%></h4>
								</footer>
							</div>
						</div>
					</div>

					<div class="col-8">
						<!-- Statistic Card -->
						<div
							class="g-bg-white g-brd-around g-brd-gray-light-v7 g-rounded-2 g-px-15 g-py-20 u-card-v1 <%=sysIniStyle%>">
							<h3
								class="text-uppercase g-font-size-12 g-font-size-default--md g-color-black g-pb-10">INITIALIZE
								SYSTEM</h3>

							<form action="../ca_initialize">
								<div class="row" style="padding-bottom: <%=pxPaddin%> !important">
									<div class="col-3">
										<!-- Basic v.3 -->
										<div>
											<h4 class="h6 g-font-weight-400 g-color-black g-mb-20">
												No. of AAs ( <i>n</i> )<br /> <small>System Testing
													Limit: 10</small>
											</h4>

											<div class="form-group mb-0">
												<div
													class="js-quantity input-group u-quantity--v2 g-width-110 g-brd-none">
													<div
														class="js-minus input-group-addon d-flex align-items-center g-width-30 g-height-30 g-font-size-10 g-color-gray-light-v11 g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-5">
														<i class="hs-admin-minus"></i>
													</div>
													<input
														class="js-result form-control g-color-gray-dark-v6 rounded-0 p-0"
														name="nAA" type="text" value="1" readonly>
													<div
														class="js-plus input-group-addon d-flex align-items-center g-width-30 g-height-30 g-font-size-10 g-color-gray-light-v11 g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-5">
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
											<h4 class="h6 g-font-weight-400 g-color-black g-mb-20">
												Threshold ( <i>t</i> )<br /> <small>Important: <i>t</i>
													&lt;= <i>n</i></small>
											</h4>

											<div class="form-group mb-0">
												<div
													class="js-quantity input-group u-quantity--v2 g-width-110 g-brd-none">
													<div
														class="js-minus input-group-addon d-flex align-items-center g-width-30 g-height-30 g-font-size-10 g-color-gray-light-v11 g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-5">
														<i class="hs-admin-minus"></i>
													</div>
													<input
														class="js-result form-control g-color-gray-dark-v6 rounded-0 p-0"
														name="tAA" type="text" value="1" readonly>
													<div
														class="js-plus input-group-addon d-flex align-items-center g-width-30 g-height-30 g-font-size-10 g-color-gray-light-v11 g-brd-around g-brd-gray-light-v7 g-rounded-4 g-pa-5">
														<i class="hs-admin-plus"></i>
													</div>
												</div>
											</div>
										</div>
										<!-- End Basic v.3 -->


									</div>

									<div class="col-3">
										<div class="form-group g-mb-0">
											<label class="u-check g-pl-25"> <input
												class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" name="gen"
												checked="" value="1" type="radio">
												<div
													class="u-check-icon-font g-absolute-centered--y g-left-0">
													<i class="fa" data-check-icon="" data-uncheck-icon=""></i>
												</div> Generate <i>n</i> AAs
											</label>
										</div>

										<div class="form-group g-mb-0">
											<label class="u-check g-pl-25"> <input
												class="g-hidden-xs-up g-pos-abs g-top-0 g-left-0" name="gen"
												value="2" type="radio">
												<div
													class="u-check-icon-font g-absolute-centered--y g-left-0">
													<i class="fa" data-check-icon="" data-uncheck-icon=""></i>
												</div> Generate <i>t</i> AAs
											</label>
										</div>
										<small> The system will auto-generate AA details. </small>
									</div>

									<div class="col-3 align-self-center text-center">
										<button
											class="btn btn-md u-btn-primary rounded g-py-13 g-px-25"
											type="submit">Initialize</button>
									</div>
								</div>
							</form>
							
							<small class="g-pt-5"> The system will generate PK - public key &amp; MK - master key. </small>
						</div>
					</div>

				</div>
				
				<% if(!reset) { %>

				<div class="row">
					<div class="col-sm-4 col-lg-4 col-xl-4 g-mb-30">

						<%
							con = null;
							st = null;
							rs = null;

							try {
								con = DbConnection.getConnection();
								st = con.createStatement();
								rs = st.executeQuery("select count(*) AS num from reg where role='AA' AND status='Yes'");
								rs.next();
								int aa_count = Integer.parseInt(rs.getString("num"));

								rs = st.executeQuery("select count(*) AS num2 from reg where role='AA' AND status='No'");
								rs.next();
								int aa_pending_count = Integer.parseInt(rs.getString("num2"));

								rs = st.executeQuery("select count(*) AS num3 from reg where role='User' AND status='Yes'");
								rs.next();
								int user_count = Integer.parseInt(rs.getString("num3"));

								rs = st.executeQuery("select count(*) AS num4 from reg where role='User' AND status='No'");
								rs.next();
								int user_pending_count = Integer.parseInt(rs.getString("num4"));

								rs = st.executeQuery(
										"select count(*) AS num5 from reg where role='AA' AND DATE(`joinDate`) = CURDATE()");
								rs.next();
								int aa_today_count = Integer.parseInt(rs.getString("num5"));
								String aa_arrow_class = "g-fill-gray-dark-v9";
								String aa_num_class = "d-block g-color-gray-dark-v9";
								String aa_plus = "";
								if (aa_today_count > 0) {
									aa_arrow_class = "g-fill-lightblue-v3";
									aa_num_class = "d-block g-color-lightblue-v3";
									aa_plus = "+";
								}

								rs = st.executeQuery(
										"select count(*) AS num6 from reg where role='User' AND DATE(`joinDate`) = CURDATE()");
								rs.next();
								int user_today_count = Integer.parseInt(rs.getString("num6"));
								String user_arrow_class = "g-fill-gray-dark-v9";
								String user_num_class = "d-block g-color-gray-dark-v9";
								String user_plus = "";
								if (user_today_count > 0) {
									user_arrow_class = "g-fill-lightblue-v3";
									user_num_class = "d-block g-color-lightblue-v3";
									user_plus = "+";
								}
						%>


						<div class="row">
							<div class="col-sm-12 col-lg-12 col-xl-12 g-mb-20">
								<!-- Panel -->
								<div
									class="card h-100 g-brd-gray-light-v7 u-card-v1 g-rounded-3">
									<div class="card-block g-font-weight-300 g-pa-20">
										<div class="media">
											<div class="d-flex g-mr-15">
												<div
													class="u-header-dropdown-icon-v1 g-pos-rel g-width-60 g-height-60 g-bg-lightblue-v4 g-font-size-18 g-font-size-24--md g-color-white rounded-circle">
													<i class="hs-admin-write g-absolute-centered"></i>
												</div>
											</div>

											<div class="media-body align-self-center">
												<div class="d-flex align-items-center g-mb-0">
													<div class="g-font-weight-300 g-color-gray-dark-v6 g-mb-0">
														<span class="g-font-size-28 g-color-black"><%=aa_count%></span>
														<span class="g-font-size-18 g-mr-6">| <%=aa_pending_count%></span>pending
													</div>
													<span
														class="d-flex align-self-center g-font-size-0 g-ml-5 g-ml-10--md">

													</span>
												</div>

												<h6
													class="g-font-size-16 g-font-weight-600 g-color-gray-dark-v6 mb-0">AAs
													Authorized</h6>

											</div>

											<div class="d-flex align-self-end ml-auto">
												<div class="d-block text-right g-font-size-16">
													<span class="<%=aa_num_class%>"><%=aa_plus%> <%=aa_today_count%></span>
													<span class="d-block g-color-gray-dark-v9">today</span>
												</div>
											</div>
											<div class="d-flex align-self-center g-ml-12 g-mb-7">
												<svg class="g-fill-gray-dark-v9" width="12px" height="20px"
													viewBox="0 0 12 20" version="1.1"
													xmlns="http://www.w3.org/2000/svg"
													xmlns:xlink="http://www.w3.org/1999/xlink">
                                                        <g
														transform="translate(-21.000000, -751.000000)">
                                                        <g
														transform="translate(0.000000, 64.000000)">
                                                        <g
														transform="translate(20.000000, 619.000000)">
                                                        <g
														transform="translate(1.000000, 68.000000)">
                                                        <polygon
														points="6 20 0 13.9709049 0.576828937 13.3911999 5.59205874 18.430615 5.59205874 0 6.40794126 0 6.40794126 18.430615 11.4223552 13.3911999 12 13.9709049"></polygon>
                                                        </g>
                                                        </g>
                                                        </g>
                                                        </g>
                                                        </svg>
												<svg class="<%=aa_arrow_class%>" width="12px" height="20px"
													viewBox="0 0 12 20" version="1.1"
													xmlns="http://www.w3.org/2000/svg"
													xmlns:xlink="http://www.w3.org/1999/xlink">
                                                        <g
														transform="translate(-33.000000, -751.000000)">
                                                        <g
														transform="translate(0.000000, 64.000000)">
                                                        <g
														transform="translate(20.000000, 619.000000)">
                                                        <g
														transform="translate(1.000000, 68.000000)">
                                                        <polygon
														transform="translate(18.000000, 10.000000) scale(1, -1) translate(-18.000000, -10.000000)"
														points="18 20 12 13.9709049 12.5768289 13.3911999 17.5920587 18.430615 17.5920587 0 18.4079413 0 18.4079413 18.430615 23.4223552 13.3911999 24 13.9709049"></polygon>
                                                        </g>
                                                        </g>
                                                        </g>
                                                        </g>
                                                        </svg>
											</div>
										</div>
									</div>
								</div>
								<!-- End Panel -->
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 col-lg-12 col-xl-12 g-mb-30">
								<!-- Panel -->
								<div
									class="card h-100 g-brd-gray-light-v7 u-card-v1 g-rounded-3">
									<div class="card-block g-font-weight-300 g-pa-20">
										<div class="media">
											<div class="d-flex g-mr-15">
												<div
													class="u-header-dropdown-icon-v1 g-pos-rel g-width-60 g-height-60 g-bg-darkblue-v2 g-font-size-18 g-font-size-24--md g-color-white rounded-circle">
													<i class="hs-admin-user g-absolute-centered"></i>
												</div>
											</div>

											<div class="media-body align-self-center">
												<div class="d-flex align-items-center g-mb-0">
													<div class="g-font-weight-300 g-color-gray-dark-v6 g-mb-0">
														<span class="g-font-size-28 g-color-black"><%=user_count%></span>
														<span class="g-font-size-18 g-mr-6">| <%=user_pending_count%></span>pending
													</div>
													<span
														class="d-flex align-self-center g-font-size-0 g-ml-5 g-ml-10--md">

													</span>
												</div>

												<h6
													class="g-font-size-16 g-font-weight-600 g-color-gray-dark-v6 mb-0">Users
													Authorized</h6>
											</div>

											<div class="d-flex align-self-end ml-auto">
												<div class="d-block text-right g-font-size-16">
													<span class="<%=user_num_class%>"><%=user_plus%> <%=user_today_count%></span>
													<span class="d-block g-color-gray-dark-v9">today</span>
												</div>
											</div>
											<div class="d-flex align-self-center g-ml-12 g-mb-7">
												<svg class="g-fill-gray-dark-v9" width="12px" height="20px"
													viewBox="0 0 12 20" version="1.1"
													xmlns="http://www.w3.org/2000/svg"
													xmlns:xlink="http://www.w3.org/1999/xlink">
                                                        <g
														transform="translate(-21.000000, -751.000000)">
                                                        <g
														transform="translate(0.000000, 64.000000)">
                                                        <g
														transform="translate(20.000000, 619.000000)">
                                                        <g
														transform="translate(1.000000, 68.000000)">
                                                        <polygon
														points="6 20 0 13.9709049 0.576828937 13.3911999 5.59205874 18.430615 5.59205874 0 6.40794126 0 6.40794126 18.430615 11.4223552 13.3911999 12 13.9709049"></polygon>
                                                        </g>
                                                        </g>
                                                        </g>
                                                        </g>
                                                        </svg>
												<svg class="<%=user_arrow_class%>" width="12px"
													height="20px" viewBox="0 0 12 20" version="1.1"
													xmlns="http://www.w3.org/2000/svg"
													xmlns:xlink="http://www.w3.org/1999/xlink">
                                                        <g
														transform="translate(-33.000000, -751.000000)">
                                                        <g
														transform="translate(0.000000, 64.000000)">
                                                        <g
														transform="translate(20.000000, 619.000000)">
                                                        <g
														transform="translate(1.000000, 68.000000)">
                                                        <polygon
														transform="translate(18.000000, 10.000000) scale(1, -1) translate(-18.000000, -10.000000)"
														points="18 20 12 13.9709049 12.5768289 13.3911999 17.5920587 18.430615 17.5920587 0 18.4079413 0 18.4079413 18.430615 23.4223552 13.3911999 24 13.9709049"></polygon>
                                                        </g>
                                                        </g>
                                                        </g>
                                                        </g>
                                                        </svg>
											</div>
										</div>
									</div>
								</div>
								<!-- End Panel -->
							</div>
						</div>

						<%
							} catch (Exception ex) {
								sqls.stat = false;
								ex.printStackTrace();
							} finally {
								con.close();
							}
						%>

					</div>

					<div class="col-xl-8">
						<!-- Statistic Card -->
						<div
							class="card g-brd-gray-light-v7 u-card-v1 g-pa-15 g-pa-25-30--md g-mb-30">
							<header class="media g-mb-30">
								<h3
									class="d-flex align-self-center text-uppercase g-font-size-12 g-font-size-default--md g-color-black mb-0">Latest
									Signup(s)</h3>
							</header>

							<section>
								<div class="row">

									<%
										con = null;
										st = null;
										rs = null;

										String top = "";
										String bottom = "";
										int i = 0, day = 0, hour = 0, min = 0, time_num = 0;
										String time_text = "";

										try {
											con = DbConnection.getConnection();
											st = con.createStatement();
											rs = st.executeQuery(
													"select name, id, joinDate, role, status from reg where role='User' or role='AA' ORDER BY joinDate DESC LIMIT 6");

											while (rs.next()) {

												if (i == 0 || i == 2 || i == 4) {
													top = "<div class=\"col-4\">";
													bottom = "";
												} else if (i == 1 || i == 3 || i == 5) {
													top = "<hr class=\"d-flex g-brd-gray-light-v4 g-my-25\">";
													bottom = "</div>";
												}

												try {
													Date past = rs.getTimestamp("joinDate");
													Date now = new Date();

													min = (int) TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
													hour = (int) TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
													day = (int) TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
												} catch (Exception j) {
													j.printStackTrace();
												}

												if (((min < hour) && (min < day)) || (hour == 0 && day == 0)) {
													time_num = min;
													time_text = " min";
												} else if (day != 0) {
													if (hour < day) {
														time_num = hour;
														time_text = " hour";
													} else {
														time_num = day;
														time_text = " day";
													}
												} else {
													time_num = hour;
													time_text = " hour";
												}

												if (time_num > 1) {
													time_text = time_text + "s";
												}

												String span_class = "";
												String span_tag = "";
												String span_text = "";

												if (rs.getString("status").equals("No")) {
													span_class = " g-bg-lightblue-v3 g-brd-lightblue-v3";
													span_tag = "href=\"../ca_activate?id=" + rs.getString("id") + "&type=" + rs.getString("role")
															+ "\"";
													span_text = "Activate";
												} else {
													span_class = " unclickable g-bg-teal-v2 g-brd-teal-v2";
													span_tag = "onclick=\'return false;\'";
													span_text = "Activated";
												}

												i++;
									%>


									<%=top%>

									<section>
										<div class="media">
											<div class="media-body g-mb-15">
												<div class="g-font-weight-400 g-color-gray-dark-v6">
													<span class="g-font-size-18 g-mr-6"><%=rs.getString("name")%></span>
												</div>
											</div>

											<div class="d-flex g-font-size-16 g-color-gray-dark-v6">
												<%=rs.getString("role")%><a
													class="u-link-v5 g-font-size-16 g-color-lightblue-v3"
													href="#!">&nbsp;#<%=rs.getString("id")%></a>
											</div>
										</div>

										<div class="media">
											<div class="media-body align-self-center" href="#!">
												<a
													class="u-tags-v1 text-center<%=span_class%> g-width-140 g-color-white g-brd-around g-rounded-50 g-py-4 g-px-15"
													<%=span_tag%> style="font-weight: bolder;"><%=span_text%></a>
											</div>

											<em
												class="d-flex align-self-center align-items-center g-font-style-normal">
												<i
												class="hs-admin-time g-font-size-default g-font-size-18--md g-color-gray-light-v3"></i>
												<span
												class="g-font-weight-300 g-font-size-12 g-font-size-default--md g-color-gray-dark-v6 g-ml-4 g-ml-8--md"><%=time_num%><%=time_text%>
													<span class="g-hidden-md-down">ago</span></span>
											</em>
										</div>
									</section>

									<%=bottom%>

									<%
										}
										} catch (Exception ex) {
											sqls.stat = false;
											ex.printStackTrace();
										}
									%>
								</div>
							</section>
						</div>
					</div>
				</div>
				
				<% } %>
			</div>

			<!-- Footer -->
			<footer id="footer"
				class="u-footer--bottom-sticky g-bg-white g-color-gray-dark-v6 g-brd-top g-brd-gray-light-v7 g-pa-20">
				<div class="row align-items-center">
					<!-- Footer Nav -->
					<div class="col-md-8 g-mb-10 g-mb-0--md">
						<ul class="list-inline text-center text-md-left mb-0">
							<li class="list-inline-item"><a
								class="g-color-gray-dark-v6 g-color-lightblue-v3--hover"
								href="#!">FAQ</a></li>
							<li class="list-inline-item"><span
								class="g-color-gray-dark-v6">|</span></li>
							<li class="list-inline-item"><a
								class="g-color-gray-dark-v6 g-color-lightblue-v3--hover"
								href="#!">Support</a></li>
							<li class="list-inline-item"><span
								class="g-color-gray-dark-v6">|</span></li>
							<li class="list-inline-item"><a
								class="g-color-gray-dark-v6 g-color-lightblue-v3--hover"
								href="#!">Contact Us</a></li>
						</ul>
					</div>
					<!-- End Footer Nav -->

					<!-- Footer Copyrights -->
					<div class="col-md-4 text-center text-md-right">
						<small class="d-block g-font-size-default">&copy; 2018
							xACS. All Rights Reserved.</small>
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
	<script
		src="../assets/vendor/bootstrap-select/js/bootstrap-select.min.js"></script>
	<script src="../assets/vendor/flatpickr/dist/js/flatpickr.min.js"></script>
	<script
		src="../assets/vendor/malihu-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	<script src="../assets/vendor/chartist-js/chartist.min.js"></script>
	<script
		src="../assets/vendor/chartist-js-tooltip/chartist-plugin-tooltip.js"></script>
	<script src="../assets/vendor/fancybox/jquery.fancybox.min.js"></script>

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

	<!-- JS Custom -->
	<script src="../assets/js/custom.js"></script>
	<script src="../assets/js/Chart.js"></script>
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
		$(document).on('ready', function() {

			// initialization of sidebar navigation component
			$.HSCore.components.HSSideNav.init('.js-side-nav');

			// initialization of hamburger
			$.HSCore.helpers.HSHamburgers.init('.hamburger');

			// initialization of HSDropdown component
			$.HSCore.components.HSDropdown.init($('[data-dropdown-target]'), {
				dropdownHideOnScroll : false
			});

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
		$(document).ready(
				function() {

					if (window.location.href.indexOf('#sqlFail') !== -1
							|| !(
	<%=sqls.stat%>
		)) {
						$('#sqlFailAlert').css({
							'visibility' : 'visible',
							'display' : 'block'
						});
						$('#sqlFailAlert').show();
					} else {
						$('#sqlFailAlert').hide();
					}

					if (window.location.href.indexOf('#ntFail') !== -1) {
						$('#ntFailAlert').css({
							'visibility' : 'visible',
							'display' : 'block'
						});
						$('#ntFailAlert').show();
					} else {
						$('#ntFailAlert').hide();
					}

					if (window.location.href.indexOf('#acsInfoFail') !== -1) {
						$('#acsInfoFailAlert').css({
							'visibility' : 'visible',
							'display' : 'block'
						});
						$('#acsInfoFailAlert').show();
					} else {
						$('#acsInfoFailAlert').hide();
					}

					if (window.location.href.indexOf('#AAIniFail') !== -1) {
						$('#AAIniFailAlert').css({
							'visibility' : 'visible',
							'display' : 'block'
						});
						$('#AAIniFailAlert').show();
					} else {
						$('#AAIniFailAlert').hide();
					}

				});
	</script>

	<script>
		new Chart(document.getElementById("line-chart"), {
			type : 'line',
			data : {
				labels : [
	<%=month_list%>
		],
				datasets : [ {
					data : [
	<%=month_visits_user[indexArray[t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		,
	<%=month_visits_user[indexArray[++t2]]%>
		],
					label : "User",
					borderColor : "#1d75e5",
					fill : true
				} ]
			},
			options : {
				legend : {
					display : false
				},
				maintainAspectRatio : false
			}
		});
	</script>
</body>

</html>
