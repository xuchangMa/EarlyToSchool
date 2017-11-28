/**
 * 学校信息
 */

/*定义数据结构
 * public class SystemSchool{
	//表id
	private String Id;	
    //学校名称
	private String SchoolName;
	//学校编码
	private String SchoolCode;
	//学校区域
	private String SchoolArea;
	//学校负责人
	private String SchoolSuperintendent;
	//负责人身份证
	private String SuperintendentId;
	//负责人编码
	private String SuperintendentCode;
	//是否生效
	private boolean DeleteFlag;	
}
 * 
 */
var url = "../System/GetZh";
$(function () {
	alert(url);
    $.post(url, { "username": "中文", "password": "psw001" }, function (data, state) {
        if(state=="success"){
        	alert(datas);
        }else{alert("获取数据失败！")}
        
    });
});