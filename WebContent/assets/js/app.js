$(function() {
	// 读取body data-type 判断是哪个页面然后执行相应页面方法，方法在下面。
	var dataType = $('body').attr('data-type');
	// console.log(dataType);
	// for (key in pageData) {
	// if (key == dataType) {
	// pageData[key]();
	// }
	// }
	 //// 判断用户是否已有自己选择的模板风格
	 //if(storageLoad('SelcetColor')){
	 //$('body').attr('class',storageLoad('SelcetColor').Color)
	 //}else{
	 //storageSave(saveSelectColor);
	 //$('body').attr('class','theme-black')
	 //}

	autoLeftNav();
	$(window).resize(function() {
		autoLeftNav();
		// console.log($(window).width())
	});

	// if(storageLoad('SelcetColor')){

	// }else{
	// storageSave(saveSelectColor);
	// }
})

// 页面数据
var pageData = {
	// ===============================================
	// 首页
	// ===============================================
	'index' : function indexData() {
		$('#example-r').DataTable({

			bInfo : false, // 页脚信息
			dom : 'ti'
		});

		// ==========================
		// 百度图表A http://echarts.baidu.com/
		// ==========================

		var echartsA = echarts.init(document.getElementById('tpl-echarts'));
		option = {
			tooltip : {
				trigger : 'axis'
			},
			grid : {
				top : '3%',
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			textStyle : {
				color : '#838FA1'
			},
			series : [ {
				name : '邮件营销',
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : [ 120, 132, 101, 134, 90 ],
				itemStyle : {
					normal : {
						color : '#1cabdb',
						borderColor : '#1cabdb',
						borderWidth : '2',
						borderType : 'solid',
						opacity : '1'
					},
					emphasis : {

					}
				}
			} ]
		};

		echartsA.setOption(option);
	},
	// ===============================================
	// 图表页
	// ===============================================
	'chart' : function chartData() {
		// ==========================
		// 百度图表A http://echarts.baidu.com/
		// ==========================

		var echartsC = echarts.init(document.getElementById('tpl-echarts-C'));

		optionC = {
			tooltip : {
				trigger : 'axis'
			},

			legend : {
				data : [ '蒸发量', '降水量', '平均温度' ]
			},
			xAxis : [ {
				type : 'category',
				data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月',
						'10月', '11月', '12月' ]
			} ],
			yAxis : [ {
				type : 'value',
				name : '水量',
				min : 0,
				max : 250,
				interval : 50,
				axisLabel : {
					formatter : '{value} ml'
				}
			}, {
				type : 'value',
				name : '温度',
				min : 0,
				max : 25,
				interval : 5,
				axisLabel : {
					formatter : '{value} °C'
				}
			} ],
			series : [
					{
						name : '蒸发量',
						type : 'bar',
						data : [ 2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2,
								32.6, 20.0, 6.4, 3.3 ]
					},
					{
						name : '降水量',
						type : 'bar',
						data : [ 2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2,
								48.7, 18.8, 6.0, 2.3 ]
					},
					{
						name : '平均温度',
						type : 'line',
						yAxisIndex : 1,
						data : [ 2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4,
								23.0, 16.5, 12.0, 6.2 ]
					} ]
		};

		echartsC.setOption(optionC);

		var echartsB = echarts.init(document.getElementById('tpl-echarts-B'));
		optionB = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				x : 'center',
				data : [ '某软件', '某主食手机', '某水果手机', '降水量', '蒸发量' ]
			},
			radar : [ {
				indicator : [ {
					text : '品牌',
					max : 100
				}, {
					text : '内容',
					max : 100
				}, {
					text : '可用性',
					max : 100
				}, {
					text : '功能',
					max : 100
				} ],
				center : [ '25%', '40%' ],
				radius : 80
			}, {
				indicator : [ {
					text : '外观',
					max : 100
				}, {
					text : '拍照',
					max : 100
				}, {
					text : '系统',
					max : 100
				}, {
					text : '性能',
					max : 100
				}, {
					text : '屏幕',
					max : 100
				} ],
				radius : 80,
				center : [ '50%', '60%' ],
			}, {
				indicator : (function() {
					var res = [];
					for (var i = 1; i <= 12; i++) {
						res.push({
							text : i + '月',
							max : 100
						});
					}
					return res;
				})(),
				center : [ '75%', '40%' ],
				radius : 80
			} ],
			series : [
					{
						type : 'radar',
						tooltip : {
							trigger : 'item'
						},
						itemStyle : {
							normal : {
								areaStyle : {
									type : 'default'
								}
							}
						},
						data : [ {
							value : [ 60, 73, 85, 40 ],
							name : '某软件'
						} ]
					},
					{
						type : 'radar',
						radarIndex : 1,
						data : [ {
							value : [ 85, 90, 90, 95, 95 ],
							name : '某主食手机'
						}, {
							value : [ 95, 80, 95, 90, 93 ],
							name : '某水果手机'
						} ]
					},
					{
						type : 'radar',
						radarIndex : 2,
						itemStyle : {
							normal : {
								areaStyle : {
									type : 'default'
								}
							}
						},
						data : [
								{
									name : '降水量',
									value : [ 2.6, 5.9, 9.0, 26.4, 28.7, 70.7,
											75.6, 82.2, 48.7, 18.8, 6.0, 2.3 ],
								},
								{
									name : '蒸发量',
									value : [ 2.0, 4.9, 7.0, 23.2, 25.6, 76.7,
											35.6, 62.2, 32.6, 20.0, 6.4, 3.3 ]
								} ]
					} ]
		};
		echartsB.setOption(optionB);
		var echartsA = echarts.init(document.getElementById('tpl-echarts-A'));
		option = {

			tooltip : {
				trigger : 'axis',
			},
			legend : {
				data : [ '邮件', '媒体', '资源' ]
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : [ {
				type : 'category',
				boundaryGap : true,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			} ],

			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : '邮件',
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : [ 120, 132, 101, 134, 90, 230, 210 ],
				itemStyle : {
					normal : {
						color : '#59aea2'
					},
					emphasis : {

					}
				}
			}, {
				name : '媒体',
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : [ 220, 182, 191, 234, 290, 330, 310 ],
				itemStyle : {
					normal : {
						color : '#e7505a'
					}
				}
			}, {
				name : '资源',
				type : 'line',
				stack : '总量',
				areaStyle : {
					normal : {}
				},
				data : [ 150, 232, 201, 154, 190, 330, 410 ],
				itemStyle : {
					normal : {
						color : '#32c5d2'
					}
				}
			} ]
		};
		echartsA.setOption(option);
	}
}

// 风格切换
$('.tpl-skiner-toggle').on('click', function() {
	$('.tpl-skiner').toggleClass('active');
})

$('.tpl-skiner-content-bar').find('span').on('click', function() {
	$('body').attr('class', $(this).attr('data-color'))
	saveSelectColor.Color = $(this).attr('data-color');
	// 保存选择项
	storageSave(saveSelectColor);

})

// 绑定菜单选项

// 侧边菜单开关
function autoLeftNav() {

	$('.tpl-header-switch-button').on('click', function() {
		if ($('.left-sidebar').is('.active')) {
			if ($(window).width() > 1024) {
				$('.tpl-content-wrapper').removeClass('active');
			}
			$('.left-sidebar').removeClass('active');
		} else {

			$('.left-sidebar').addClass('active');
			if ($(window).width() > 1024) {
				$('.tpl-content-wrapper').addClass('active');
			}
		}
	})

	if ($(window).width() < 1024) {
		$('.left-sidebar').addClass('active');
	} else {
		$('.left-sidebar').removeClass('active');
	}
}

// 侧边菜单
$('.sidebar-nav-sub-title').on(
		'click',
		function() {
			$(this).siblings('.sidebar-nav-sub').slideToggle(80).end().find(
					'.sidebar-nav-sub-ico').toggleClass(
					'sidebar-nav-sub-ico-rotate');
		})

// 选项页部分
var tabCounter = 0;
var $tab = $('#doc-tab-demo-1');
var $nav = $tab.find('.am-tabs-nav');
var $bd = $tab.find('.am-tabs-bd');
var $tabName = "";
function addTab(names, url) {
    if ($tabName.indexOf(names) >= 0) { return; }
    $tabName += names + ",";
    var nav = '<li><span class="am-icon-close am-close am-close-spin"></span>'
			+ '<a href="javascript: void(0)">' + names + '</a></li>';
	var content = '<div class="am-tab-panel am-fr">' + GetTabs(url) + '</div>';	
	$nav.append(nav);
	$bd.append(content);	
	tabCounter++;
	$tab.tabs('refresh');	
}

// 移除标签页
$nav.on('click', '.am-icon-close', function() {
	var $item = $(this).closest('li');
	var index = $nav.children('li').index($item);
	var itemname = $item[0].textContent + ",";
	$tabName = $tabName.replace(itemname, "");
	$item.remove();
	$bd.find('.am-tab-panel').eq(index).remove();

	$tab.tabs('open', index > 0 ? index - 1 : index + 1);
	$tab.tabs('refresh');
});

function GetTabs(url){
	var datas='';
	$.ajax({
        type : 'get',
        url : url,
        //设置contentType类型为json
        dataType:"html",
        async:false,//同步请求
	    //请求成功后的回调函数
        success : function(data) {
                 //alert(data);
        	datas=data;
        }
	});
	return datas;
}
//定义全局的页面数据展示行数
var PageNumber = 5;

//打开所有页码
function OpenPages(Id) {
	// alert($("#" + Id).css('display'));
	if ($("#" + Id).css('display') == "none") {
		$("#" + Id).css('display', 'block');
	} else {
		$("#" + Id).css('display', 'none');
	}
}

function Menu() {
    var menuJson = [{
        "type": "2", "Icon": "1", "Name": "系统设置2", "value": [
            { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" },
            { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" },
            { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" }]
    },
    { "type": "3", "Icon": "1", "Name": "系统设置3", "url": "" }
    ]
    var menuData = "";
    for (var i = 0; i < menuJson.length; i++) {
        menuData += "<li class=\"sidebar-nav-link\">";
       if (menuJson[i].type == "2") {
            menuData += "<a href=\"javascript:;\" class=\"sidebar-nav-sub-title\">" +
                        "<i class=\"am-icon-table sidebar-nav-link-logo\"></i>" + menuJson[i].Name +
                        "<span class=\"am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico\"></span></a>";
            menuData += "<ul class=\"sidebar-nav sidebar-nav-sub\">";
            for (var j = 0; j < menuJson[i].value.length; j++) {
                menuData += "<li class=\"sidebar-nav-link\">" +
                            "<a href=\"table-list.html\">" +
                            "    <span class=\"am-icon-angle-right sidebar-nav-link-logo\"></span> " + menuJson[i].value[j].Name +
                            "</a>" +
                            "</li>";
            }
            menuData += "</ul>";
        }
        else if (menuJson[i].type == "3") {
            menuData += "<a href=\"table-list.html\">" +
                        "    <span class=\"am-icon-angle-right sidebar-nav-link-logo\"></span> " + menuJson[i].Name +
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