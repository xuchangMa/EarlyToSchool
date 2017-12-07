/**
 * 主页面相关js方法
 */
$(function (){
	//获取当前登录用户的编码
	var UserCode = $.cookie("UserCode");
	//获取用户基本信息
	// 定义当前页面访问的接口地址
	var url = "/getSystemApplicationSet";
	// 获取接口数据
	$.post(url, da, function(data, state) {
		if (state == "success") {
			if (data != "No") {
				// 把查询数据赋值给js变量
				SystemRoleData = eval('(' + data + ')');
				// 调用绑定数据的方法
				BandingMenu(SystemRoleIndexPage);
			}
		} else {
			alert("加载菜单失败！")
		}

	});
	//返回菜单列表
	
	
})
 
//绑定菜单方法
function BandingMenu(menuJson) {
    /*var menuJson = [{
        "type": "2", "Icon": "1", "Name": "系统设置2", "value": [
            { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" },
            { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" },
            { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" }]
    },
    { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" }
    ]*/
    var menuData = "";
    for (var i = 0; i < menuJson.length; i++) {
        menuData += "<li class=\"sidebar-nav-link\">";
       if (menuJson[i].ApplicationGrade == "1") {
            menuData += "<a href=\"javascript:;\" class=\"sidebar-nav-sub-title\">" +
                        "<i class=\""+menuJson[i].ApplicationIcon+" sidebar-nav-link-logo\"></i>" + menuJson[i].ApplicationName +
                        "<span class=\"am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico\"></span></a>";
            menuData += "<ul class=\"sidebar-nav sidebar-nav-sub\">";
            for (var j = 0; j < menuJson[i].ApplicationURL.length; j++) {
                menuData += "<li class=\"sidebar-nav-link\">" +
                            "<a href=\""+menuJson[j].ApplicationURL+"\">" +
                            "    <span class=\""+menuJson[j].ApplicationIcon+" sidebar-nav-link-logo\"></span> " + menuJson[i].value[j].ApplicationName +
                            "</a>" +
                            "</li>";
            }
            menuData += "</ul>";
        }
        else if (menuJson[i].ApplicationGrade == "2") {
            menuData += "<a href=\""+menuJson[i].ApplicationURL+"\">" +
                        "    <span class=\""+menuJson[i].ApplicationIcon+" sidebar-nav-link-logo\"></span> " + menuJson[i].ApplicationName +
                        "</a>";
        }
        menuData += "</li>";
    }

    $("#SystemMenu").html(menuData);
    $('.sidebar-nav-sub-title').on('click', function () {
        $(this).siblings('.sidebar-nav-sub').slideToggle(80)
            .end()
            .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
    })
}