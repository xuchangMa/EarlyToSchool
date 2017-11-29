/**
 * 角色权限配置
 */

/**
 * public class RoleConfiguration {
	//表ID
	public static String Id;	
	//角色编码
	public static String RoleCode;	
	//角色名称
	public static String RoleName;
	//查看
	public static String View;
	//新增&修改
	public static String AddUpdate;
	//删除
	public static String Delete;
	//导入&导出
	public static String ImportExport;
	//上传
	public static String Upload;
	//下载
	public static String Download;
}
 */
var tableName = "RoleConfiguration";
/*
 * //表id//角色编码//角色名称//是否生效
 * 共4项
 * */
var datastructure = new Array("Id","RoleCode","RoleAuthorization","DeleteFlag");