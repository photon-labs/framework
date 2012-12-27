<%--
  ###
  Framework Web Archive
  
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ###
  --%>
<!doctype html>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ page import="java.util.List"%>

<%@ page import="org.apache.commons.collections.CollectionUtils"%>

<%@ page import="com.photon.phresco.util.ServiceConstants"%>
<%@ page import="com.photon.phresco.commons.FrameworkConstants"%>
<%@ page import="com.photon.phresco.commons.model.User"%>
<%@ page import="com.photon.phresco.commons.model.Customer"%>

<html>
	<head>
		<meta name="viewport" content="width=device-width, height=device-height, minimum-scale=0.25, maximum-scale=1.6">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Phresco</title>
		<link rel="SHORTCUT ICON" href="images/favicon.ico">
		<link rel="stylesheet" href="css/bootstrap.css">

		<!-- <link type="text/css" rel="stylesheet" href="theme/red_blue/css/phresco.css" id="phresco"> -->
		<link type="text/css" rel="stylesheet" href="theme/photon/css/phresco_default.css" id="phresco">

		<link type="text/css" rel="stylesheet" class="changeme" title="phresco" href="theme/photon/css/photon_theme.css">
		<!-- media queries css -->
		<link type="text/css" rel="stylesheet" href="theme/photon/css/media-queries.css" id="media-query">
		
		<!-- jquery file tree css starts -->
		<link type="text/css" rel="stylesheet" href="css/jqueryFileTree.css" media="screen">
		<!-- jquery file tree css ends -->
		
		<!-- basic js -->
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
		
		<!-- Pop Up box -->
		<script type="text/javascript" src="js/bootstrap-modal.js"></script>
		<script type="text/javascript" src="js/bootstrap-transition.js"></script>
		<!--<script type="text/javascript" src="js/bootstrap.js"></script>-->

		<!-- right panel scroll bar -->
		<script type="text/javascript" src="js/home.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		
		<script type="text/javascript" src="js/home-header.js"></script>
		<script type="text/javascript" src="js/jquery.loadmask.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
		<script type="text/javascript" src="js/jquery.cookie.js"></script>
		<script type="text/javascript" src="js/selection-list.js"></script>
		<script type="text/javascript" src="js/jquery-jvert-tabs-1.1.4.js"></script>
		<script type="text/javascript" src="js/RGraph.common.core.js"></script>
		<script type="text/javascript" src="js/RGraph.common.tooltips.js"></script>
		<script type="text/javascript" src="js/RGraph.common.effects.js"></script>
		<script type="text/javascript" src="js/RGraph.pie.js"></script>
		<script type="text/javascript" src="js/RGraph.bar.js"></script>
		<script type="text/javascript" src="js/RGraph.line.js"></script>
		<script type="text/javascript" src="js/RGraph.common.key.js"></script>
		<script type="text/javascript" src="js/video.js"></script>
		<script type="text/javascript" src="js/confirm-dialog.js"></script>
		
		<!-- Scroll Bar -->
		<script type="text/javascript" src="js/scrollbars.js"></script>
		<script type="text/javascript" src="js/jquery.event.drag-2.0.min.js"></script>
		<script type="text/javascript" src="js/jquery.ba-resize.min.js"></script>
		<script type="text/javascript" src="js/jquery.mousewheel.js"></script>
		<script type="text/javascript" src="js/mousehold.js"></script>
		
		<!-- jquery file tree starts-->
		<script type="text/javascript" src="js/jqueryFileTree.js"></script>
		<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
		<!-- jquery file tree ends -->
		
		<script type="text/javascript" src="js/delete.js" ></script>
		<script type="text/javascript" src="js/loading.js"></script>
		<script type="text/javascript" src="js/reader.js" ></script>
		<script type="text/javascript" src="js/jquery-tojson.js" ></script>
		
		<script type="text/javascript" src="js/ddslick.js"></script>
		
		<!-- File upload -->
		<script type="text/javascript" src="js/fileuploader.js"></script>
		<link type="text/css" rel="stylesheet" href="css/fileuploader.css"/>
		
		<!-- Window Resizer -->
		<script type="text/javascript" src="js/windowResizer_default.js" id="windowResizer"></script>
		
		<!-- jquery editable combobox -->
		<script src="js/jquery.editable.combobox.js"></script>
		<script src="js/jss.min.js"></script>

		<script type="text/javascript">
		    $(document).ready(function() {
		        applyTheme();
		        
				$(".styles").click(function() {
	                 localStorage.clear();
	                 var value = $(this).attr("rel");
	                 localStorage["color"]= value;
	                 applyTheme();
	            });
                  
				// function to show user info in toggle 
				$('div aside.usersettings div').hide(0);
				$('div aside.usersettings').click(function() {
					$('div aside.usersettings div').slideToggle(0);
				});

				// to show user info on mouse over
				$('#signOut aside').mouseenter(function() {
					$("div aside.usersettings div").hide(0);
					$(this).children("div aside.usersettings div").show(0);
				}).mouseleave(function() {
					$("div aside.usersettings div").hide(0);
				});
				showLoadingIcon();
				clickMenu($("a[name='headerMenu']"), $("#container"), $('#formCustomers'));
				loadContent("home", '', $("#container"), '', '', true);
				activateMenu($("#home"));
			});
		</script>
	</head>
	<body>
        <%
            User userInfo = (User) session.getAttribute(FrameworkConstants.SESSION_USER_INFO);
            String displayName = "";
            if (userInfo != null) {
                displayName = userInfo.getDisplayName();
            }
        %>
		<div class="modal-backdrop fade in popupalign"></div>
	    
	    <!-- In Progress starts -->
		<div id="progressbar" class="progressPosition">
			<div id="indicatorInnerElem">
				<span id="progressnum"></span>
			</div>
			<div id="indicator"></div>
		</div>
		<!-- In Progress Ends -->
		
		<!-- Loding icon div starts -->
		<div id="loadingIconDiv" class="hideContent"> 
			<img class="loadingIcon" id="loadingIconImg" src="" />
		</div>
		<!-- Loding icon div ends -->
		
		<!-- Header Starts Here -->
		<header>
			<div class="header">
				<div class="Logo">
					 <a href="#" id="goToHome"><img class="headerlogoimg" id="logoImg" src="" alt="logo"></a>
				</div>
				
				<div id="signOut" class="signOut">
					<aside class="usersettings">
						<%= displayName %>
						<img src="images/downarrow.png" class="arrow">
                        <div class="userInfo" >
                            <ul>
                            	<li id="themeContainer" class="theme_change"><a href="#">Themes</a>
                                	<ul>
                                    	<li>Photon&nbsp;<a href="#" class="styles" href="#" rel="theme/photon/css/photon_theme.css"><img src="images/photon_theme.png"></a></li>
                                        <li>Red-Blue&nbsp;
                                            <a class="styles" href="#" rel="theme/red_blue/css/blue.css"><img src="images/blue_themer.jpg" class="skinImage"></a>
											<a class="styles" href="#" rel="theme/red_blue/css/red.css"><img src="images/red_themer.jpg"></a>
                                        </li>
                                    </ul>
                                </li>
                                <li><a href="#"><s:text name="lbl.hdr.help"/></a></li>
                                <li><a href="#"><s:text name="lbl.abt.phresco"/></a></li>
                                <li><a href="<s:url action='logout'/>"><s:text name="lbl.signout"/></a></li>
                            </ul>
                        </div>
					</aside>
				</div>
                
                <div class="headerInner">
					<div class="nav_slider">
						<nav class="headerInnerTop">
							<ul>
								<li class="wid_home"><a href="#" class="inactive" name="headerMenu" id="home">
								    <s:label key="lbl.hdr.home"  theme="simple"/></a>
                                </li>
								<li class="wid_app"><a href="#" class="inactive" name="headerMenu" id="applications">
								    <s:label key="lbl.hdr.projects" theme="simple"/></a>
								</li>
								<li class="wid_set"><a href="#" class="inactive" name="headerMenu" id="settings" additionalParam="fromPage=settings">
								    <s:label key="lbl.hdr.settings"  theme="simple"/></a>
								</li>
								<li class="wid_help"><a href="#" class="inactive" name="headerMenu" id="forum">
								    <s:label key="lbl.hdr.help"  theme="simple"/></a>
								</li>
							</ul>
							<div class="close_links" id="close_links">
								<a href="JavaScript:void(0);">
									<div class="headerInnerbottom">
										<img src="images/uparrow.png" alt="logo">
									</div>
								</a>
							</div>
						</nav>
					</div>
					<div class="quick_lnk" id="quick_lnk">
						<a href="JavaScript:void(0);">
							<div class="quick_links_outer">
								<s:label key="lbl.hdr.quicklinks" theme="simple"/>
							</div>
						</a>
					</div>
				</div>
			</div>
		</header>
		<!-- Header Ends Here -->
		
		
		<!-- Content Starts Here -->
		<section class="main_wrapper">
			<section class="wrapper">
			
				<!-- Shortcut Top Arrows Starts Here -->
				<aside class="shortcut_top">
					<div class="lefttopnav">
						<a href="JavaScript:void(0);" id="applications" name="headerMenu"
							class="arrow_links_top">
							<span class="shortcutRed" id=""></span>
							<span class="shortcutWh" id="">
							<s:text name="lbl.hdr.projects"/></span>
						</a>
					</div>
					
					<form id="formCustomers" class="form">
						<div id="customerList" class="control-group customer_name">
							<s:label key="lbl.customer" cssClass="control-label custom_label labelbold" theme="simple"/>
							<div class="controls customer_select_div">
								<select name="customerId" class="customer_listbox">
					                <%
					                	User user = (User) session.getAttribute(FrameworkConstants.SESSION_USER_INFO);
					                    if (user != null) {
					                    	List<Customer> customers = user.getCustomers();
					                    	for (Customer customer: customers) {
								    %>
					                       <option value="<%= customer.getId() %>"><%= customer.getName()%></option>
									<% 
								            }
								        } 
								    %>
								</select>
							</div>
						</div>
					</form>
					
					<div class="righttopnav">
						<a href="JavaScript:void(0);" class="abtPopUp" class="arrow_links_top"><span
							class="shortcutRed" id=""></span><span class="shortcutWh"
							id="">
							<s:text name="lbl.aboutus"/></span>
						</a>
					</div>
				</aside>
				<!-- Shortcut Top Arrows Ends Here -->
				
				<section id="container" class="container">
				
					<!-- Content Comes here-->
					
				</section>
				
				<!-- Shortcut Bottom Arrows Starts Here -->
				<aside class="shortcut_bottom">
				   <div class="leftbotnav">
						<a href="JavaScript:void(0);" id="forum" name="headerMenu"
							class="arrow_links_bottom"><span class="shortcutRed" id=""></span><span
							class="shortcutWh" id=""><s:text name="lbl.hdr.help"/></span>
						</a>
					</div>
					<div class="rightbotnav">
						<a href="JavaScript:void(0);" id="settings" name="headerMenu"
							class="arrow_links_bottom"><span class="shortcutRed" id="lf_tp1"></span><span
							class="shortcutWh" id="lf_tp2"><s:text name="lbl.hdr.settings"/></span>
						</a>
					</div>
				</aside>
				
				<!-- Shortcut Bottom Arrows Ends Here -->
			</section>
			
			<!-- Slide News Panel Starts Here -->
			<aside>
				<section>
					<div class="right">
						<div class="right_navbar active">
							<div class="barclose">
								<div class="lnclose">Latest&nbsp;News</div>
							</div>
						</div>
						<div class="right_barcont">
							<div class="searchsidebar">
								<div class="newstext">
									Latest<span>News</span>
								</div>
								<div class="topsearchinput">
									<input name="" type="text">
								</div>
								<div class="linetopsearch"></div>
							</div>
							<div id="tweets" class="sc7 scrollable dymanic paddedtop">
								<div class="tweeterContent"></div>
							</div>
						</div>
						<br clear="left">
					</div>
				</section>
			</aside>
			<!-- Slide News Panel Ends Here -->
		</section>
		<!-- Content Ends Here -->
		
		<!-- Footer Starts Here -->
		<footer>
			<address class="copyrit">&copy; 2012.Photon Infotech Pvt Ltd. |<a href="http://www.photon.in"> www.photon.in</a></address>
		</footer>
		<!-- Footer Ends Here -->
		
		<!-- Popup Starts-->
	    <div id="popupPage" class="modal hide fade">
			<div class="modal-header">
				<a class="close" data-dismiss="modal" >&times;</a>
				<h3 id="popupTitle"></h3>
	    </div>
			<div class="modal-body" id="popup_div">
			
			</div>
			<div class="modal-footer">
<%-- 				<a href="#" class="btn btn-primary" data-dismiss="modal" id="popupCancel"><s:text name='lbl.btn.cancel'/></a> --%>
				<input type="button" class="btn btn-primary" id="popupCancel" value="<s:text name='lbl.btn.cancel'/>" data-dismiss="modal" href="#"/>
<%-- 				<a href="#" class="btn btn-primary popupOk" id="" onClick="popupOnOk(this);" ><s:text name='lbl.btn.ok'/></a> --%>
				<input type="button" class="btn btn-primary popupOk" id="" onClick="popupOnOk(this);" value="<s:text name='lbl.btn.ok'/>" href="#"/>
<%-- 				<a href="#" class="btn btn-primary popupClose" data-dismiss="modal" id="" onClick="popupOnClose(this);"><s:text name='lbl.btn.close'/></a> --%>
				<input type="button" class="btn btn-primary popupClose" id=""  onClick="popupOnClose(this);" value="<s:text name='lbl.btn.close'/>" data-dismiss="modal" href="#"/>
				<img class="popuploadingIcon" id="popuploadingIcon" src="" />
				<div id="errMsg" class="envErrMsg"></div>
			</div>
		</div>
	    <!-- Popup Ends -->
	    
	    <!-- Progress popup Starts-->
	    <div id="progressPopup" class="modal hide fade">
			<div class="modal-header">
				<a class="close" data-dismiss="modal" >&times;</a>
				<h3 id="popupTitle"><s:text name='lbl.progress'/></h3>
			</div>
			<div class="modal-body" id="popup_progress_div">
			
			</div>
			<div class="modal-footer">
				<a href="#" class="btn btn-primary progressPopupClose" data-dismiss="modal" id="" onClick="popupOnClose(this);"><s:text name='lbl.btn.close'/></a>
				<a href="#" class="btn btn-primary popupStop hideContent" data-dismiss="modal" id="" onClick="popupOnStop(this);"><s:text name='lbl.btn.stop'/></a>
				<img class="popuploadingIcon" id="popuploadingIcon" src="" />
			</div>
		</div>
	    <!-- Progress Popup Ends-->
	    
	    <!-- Additional popup Starts-->
	    <div id="additionalPopup" class="modal hide fade">
			<div class="modal-header">
				<a class="close" data-dismiss="modal" onclick="add_popupCancel();">&times;</a>
				<h3 id="additional_popupTitle"></h3>
			</div>
			<div class="modal-body" id="additional_popup_body">
			
			</div>
			<div class="modal-footer">
				<input type="text" class="xlarge javastd hideContent" id="browseSelectedLocation" name="browseLocation"/>
				<label for="xlInput" class="labelbold compressNameLbl" id="compressNameLbl">
					<span class="red">*</span>&nbsp;<s:text name="lbl.compress.name"/>
				</label>
				<input type="text" class="hideContent compressNameTextBox" id="compressName" name="compressName"/>
				<a href="#" class="btn btn-primary" data-dismiss="modal" id="add_popupCancel" onclick="add_popupCancel();"><s:text name='lbl.btn.cancel'/></a>
				<a href="#" class="btn btn-primary add_popupOk" id="" onClick="add_popupOnOk(this);" ><s:text name='lbl.btn.ok'/></a>
				<div id="errMsg" class="envErrMsg add_errorMsg"></div>
				<img class="popuploadingIcon" id="popuploadingIcon" src="" />
			</div>
		</div>
	    <!-- Additional Popup Ends-->
	</body>
	
	<script type="text/javascript">
	 $(document).ready(function() {
		applyTheme();
		getLogoImgUrl();
		showHideTheme();
	        
		$(".styles").click(function() {
			localStorage.clear();
			var value = $(this).attr("rel");
			localStorage["color"]= value;
			applyTheme();
		});
           
		// function to show user info in toggle 
		$('div aside.usersettings div').hide(0);
		$('div aside.usersettings').click(function() {
			$('div aside.usersettings div').slideToggle(0);
		});

		// to show user info on mouse over
		$('#signOut aside').mouseenter(function() {
			$("div aside.usersettings div").hide(0);
			$(this).children("div aside.usersettings div").show(0);
		}).mouseleave(function() {
			$("div aside.usersettings div").hide(0);
		});
		
		showLoadingIcon();
		clickMenu($("a[name='headerMenu']"), $("#container"), $('#formCustomers'));
		loadContent("home", '', $("#container"), '', '', true);
		activateMenu($("#home"));
				
		//To get the list of projects based on the selected customer
    	$('select[name=customerId]').change(function() {
    		getLogoImgUrl();
    		showLoadingIcon();
    		showHideTheme();
    		loadContent("applications", $('#formCustomers'), $("#container"), '', '', true);
    	});
		
	});
	
	if ($.browser.safari && $.browser.version == 530.17) {
		$(".shortcut_bottom").show().css("float","left");
	}
	
	function getLogoImgUrl() {
		var currentCustomerId = $('select[name=customerId]').val();
		if (currentCustomerId === "<%= ServiceConstants.DEFAULT_CUSTOMER_NAME %>") {
			applyTheme();
		} else {
			localStorage["color"] = "theme/photon/css/photon_theme.css";
			applyTheme();
			loadContent("fetchLogoImgUrl", $('#formCustomers'), '', '', true, true, 'changeLogo');
		}
	}

	function changeLogo(data) {
		$('#logoImg').attr("src",  "data:image/png;base64," + data.logoImgUrl);
		$("#brandingColor").val(data.brandingColor);
		changeColorScheme(data.brandingColor);
	}
	
	function changeColorScheme(brandingColor) {
		JSS.css({
			'.openreg': {
				'background': brandingColor
			},
			
			'.closereg': {
				'background': brandingColor
			},
			
			'.siteinnertooltiptxt': {
				'border-color': brandingColor
			},
						
			'.headerInnerTop li a.active label': {
				'color': brandingColor + " !important"
			},
			
			'.tabs li a.active': {
				'background': brandingColor + " !important"
			},
			
			'.tabs > li > a:hover': {
				'background': "none repeat scroll 0 0" + brandingColor
			},
			
			'.modal-header': {
				'background': "none repeat scroll 0 0" + brandingColor
			},

			'.video-title': {
				'background': "none repeat scroll 0 0" + brandingColor
			},
			
			'.listindex-active': {
				'background': "none repeat scroll 0 0" + brandingColor
			},
			
			'.listindex:hover': {
				'background': "none repeat scroll 0 0" + brandingColor
			},
			
			'.listindex': {
				'color': brandingColor
			},
			
			'#testmenu li a.active, #testmenu li a:hover': {
				'background': brandingColor
			},
			
			'#indicator': {
				'background': brandingColor
			},
			
			'#progressbar': {
				'border-color': brandingColor
			},
			
			'.userInfo ul li a': {
				'color': brandingColor
			}
		});
	}
	
	//To hide themes for customers other than photon
	function showHideTheme() {
		var customerId = $('select[name=customerId]').val();
		if (customerId != "<%= ServiceConstants.DEFAULT_CUSTOMER_NAME %>") {
			$('#themeContainer').hide();
		} else if (customerId === "<%= ServiceConstants.DEFAULT_CUSTOMER_NAME %>"){
			$('#themeContainer').show();
		}
	}
	
	/** To include the js based on the device **/
	/* var body = document.getElementsByTagName('body')[0];
	var script = document.createElement('script');
	if (isiPad()) {
		script.src = 'js/windowResizer-ipad.js';
	} else {
		script.src = 'js/windowResizer.js';
	}
	body.appendChild(script); */
</script>
</html>
