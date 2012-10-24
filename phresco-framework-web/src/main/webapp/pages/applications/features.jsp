<!DOCTYPE html>
<html>
    
    <head>
        <style>
            .custom_features_wrapper {
                border-radius: 6px 6px 6px 6px;
                float: left;
                height: 75%;
                margin: 1%;
                width: 46.5%;
            }
            .custom_features_wrapper_right {
                border-radius: 6px 6px 6px 6px;
                float: right;
                height: 75%;
                margin: 1% 1% 1% 0;
                width: 46%;
            }
            .fea_add_but {
            	width: 3%;
            	height: 8%;
            }
            .external_features_wrapper {
                border-radius : 6px;
                float: left;
                height: auto;
                width:100%;
            }
            .features_ver_sel {
                border: none;
                margin-left: 70%;
                margin-bottom: 0px;
                height: 20px;
                padding: 0px;
                webkit-border-radius: 0px;
                -moz-border-radius: 0px;
                border-radius: 0px;
            }
            .features_cl {
                width: 11% !important;
            }
            /* .customer_name {
                float: right;
                left: 71%;
                margin-bottom: 0;
                position: absolute;
                top: 10px;
                width: auto;
            }
            .customer_listbox {
                float: right;
                left: 97%;
                margin-bottom: 0;
                position: absolute;
                top: 0px;
                width: auto;
            } */
            .selectfeature {
                margin: 1% 2% 0% 0%;
            }
            .accordianchange {
                width: 45%;
                margin: auto;
            }
            #result div{
                background: #470411;
                background: -webkit-gradient(linear, left top, left bottom, color-stop(1%, #470411), color-stop(100%, #2B0007));
                filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#470411', endColorstr='#2b0007', GradientType=0);
                color: #F4D7D7;
                line-height: 25px;
                border-bottom: 2px solid black;
                border-top: 2px solid black;
                padding: 0px 5px 0px 5px;
            }
            #result div a {
            	float: right;
            	margin-right: 10px;
            }
            #selecteddata {
                padding: 2px 5px 1px 10px;
                background-color: #3A090B;
                color: white;
                border-bottom: solid 1px black;
            }
        </style>
    </head>
    
    <body>
        <div style="width: 30%">
            <form class="form-horizontal customer_list">
                <div class="selectfeature">
                    <label for="myselect" class="control-label features_cl">Type&nbsp;</label>
                    <select id="featureselect" onchange="featuretype()">
                        <option>Module</option>
                        <option>JSLibraries</option>
                        <option>Components</option>
                    </select>
                </div>
            </form>
        </div>
        <div class="custom_features_wrapper">
            <div class="tblheader">
                <table class="zebra-striped">
                    <tr>
                        <th class="editFeatures_th1">&nbsp;
                        	<span id="featuresHeading"></span>
                            <br>&nbsp;</th>
                        <th class="editFeatures_th2"></th>
                    </tr>
                </table>
            </div>
            <div class="theme_accordion_container" id="coremodule_accordion_container">
                <div class="content_adder">
                    <div class="control-group">
                        <div id="accordianchange">
                            <div id="acc1">
                                <div id="checkboxes">
                                    <div class="external_features_wrapper">
                                        <div class="theme_accordion_container" id="coremodule_accordion_container">
                                            <section class="accordion_panel_wid">
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module11" id="checkAll1" onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module11</a>
															<select class="input-mini features_ver_sel" name="Module11" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module12" id="checkAll2"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module12</a>
															<select class="input-mini features_ver_sel" name="Module12" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module13" id="checkAll3"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module13</a>
															<select class="input-mini features_ver_sel" name="Module13" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module14" id="checkAll3"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module14</a>
															<select class="input-mini features_ver_sel" name="Module14" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module15" id="checkAll3"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module15</a>
															<select class="input-mini features_ver_sel" name="Module15" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="acc2" style="display: none">
                                <div id="checkboxes">
                                    <div class="external_features_wrapper">
                                        <div class="theme_accordion_container" id="coremodule_accordion_container">
                                            <section class="accordion_panel_wid">
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module21" id="checkAll1" onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
                                                            <a href="#">Module21</a>
                                                            <select class="input-mini features_ver_sel" name="Module21" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module22" id="checkAll2"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module22</a>
															<select class="input-mini features_ver_sel" name="Module22" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module23" id="checkAll3"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module23</a>
															<select class="input-mini features_ver_sel" name="Module23" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                               <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module24" id="checkAll3"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module24</a>
															<select class="input-mini features_ver_sel" name="Module24" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module25" id="checkAll3"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module25</a>
															<select class="input-mini features_ver_sel" name="Module25" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="acc3" style="display: none">
                                <div id="checkboxes">
                                    <div class="external_features_wrapper">
                                        <div class="theme_accordion_container" id="coremodule_accordion_container">
                                            <section class="accordion_panel_wid">
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module31" id="checkAll1" onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module31</a>
															<select class="input-mini features_ver_sel" name="Module31" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module32" id="checkAll2"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module32</a>
															<select class="input-mini features_ver_sel" name="Module32" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module33" id="checkAll3"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module33</a>
															<select class="input-mini features_ver_sel" name="Module33" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module34" id="checkAll3"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module34</a>
															<select class="input-mini features_ver_sel" name="Module34" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="accordion_panel_inner">
                                                    <section class="lft_menus_container">	<span class="siteaccordion">
														<span>
															<input type="checkbox" value="Module35" id="checkAll3"  onclick="enableDisableDelete(this,'#');" style="margin-right:5px;"/>
															<a href="#">Module35</a>
															<select class="input-mini features_ver_sel" name="Module35" onchange="versiontype()">
																<option>ver1.0</option>
																<option>ver2.0</option>
																<option>ver3.0</option>
															</select>
														</span>
</span>
                                                        <div class="mfbox siteinnertooltiptxt">
                                                            <div class="scrollpanel" style="height:120px;">
                                                                <section class="scrollpanel_inner">
                                                                    <img style="float: left;" class="headerlogoimg" src="images/right1.png" alt="logo">
                                                                    <p style="margin-left:12%; color:white;">To create a framework for development of Social Media apps
                                                                    To create a framework for development of Mobile apps
																	To create a framework for development of Mobile websites
																	To assist in creating high quality code
																	To ensure scalable and consistent architectural patterns</p>
                                                                </section>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
           
            <div style="clear:both"></div>
        </div>
         <button style="margin-top: 15%" class="btn btn-primary fea_add_but" onclick="clickToAdd()"><img class="featureok" src="images/icons/right_arrow.png" alt=""></button>
        <div class="custom_features_wrapper_right">
            <div class="tblheader">
                <table class="zebra-striped">
                    <tr>
                        <th class="editFeatures_th1"> &nbsp; Selected&nbsp;Features &nbsp;</th>
                        <th class="editFeatures_th2"></th>
                    </tr>
                </table>
            </div>
            <div class="theme_accordion_container" id="coremodule_accordion_container">
                <div id="result"></div>
            </div>
        </div>
    </body>

</html>
<script type="text/javascript">
    $(document).ready(function () {
        hideLoadingIcon();
        fillHeading();
    });
    
    /** Accordian starts **/
    var showContent = 0;
    $('.siteaccordion').removeClass('openreg').addClass('closereg');
    $('.mfbox').css('display', 'none')

    $('.siteaccordion').bind('click', function (e) {
        var _tempIndex = $('.siteaccordion').index(this);
        $('.siteaccordion').removeClass('openreg').addClass('closereg');
        $('.mfbox').each(function (e) {
            if ($(this).css('display') == 'block') {
                $(this).slideUp('300');
            }
        })
        if ($('.mfbox').eq(_tempIndex).css('display') == 'none') {
            $(this).removeClass('closereg').addClass('openreg');
            $('.mfbox').eq(_tempIndex).slideDown(300, function () {

            });
        }
    }); 
    // Function for the feature list selection
    function featuretype() {
    	fillHeading();
        var str = "";
        $("#featureselect option:selected").each(function () {
            str += $(this).text();
        });
        if (str == "Module") {
            $("#accordianchange #acc1").attr("style", "display:block");
            $("#accordianchange #acc2").attr("style", "display:none");
            $("#accordianchange #acc3").attr("style", "display:none");
        } else if (str == "JSLibraries") {
            $("#accordianchange #acc1").attr("style", "display:none");
            $("#accordianchange #acc2").attr("style", "display:block");
            $("#accordianchange #acc3").attr("style", "display:none");
        } else {
            $("#accordianchange #acc1").attr("style", "display:none");
            $("#accordianchange #acc2").attr("style", "display:none");
            $("#accordianchange #acc3").attr("style", "display:block");
        }
    }

    // Function to add the features to the right tab
    function clickToAdd() {
        $('#checkboxes input:checked').each(function () {
        	var name = ($(this)).val();
        	var version = $('select[name='+name+']').val();
			$("#" + name).remove();
			$("#result").append('<div id="'+name+'">'+name+' - '+version+'<a href="#" id="'+name+'" onclick="remove(this);">&times;</a></div>');
        });
    }
    // Function to remove the final features in right tab  
    function remove(thisObj) {
    	var id = $(thisObj).attr("id");
    	$("#" + id).remove();
    }
    // Function to fill the heading of the left tab
    function fillHeading() {
    	var val = $("#featureselect").val();
    	$("#featuresHeading").text(val)
    }
</script>