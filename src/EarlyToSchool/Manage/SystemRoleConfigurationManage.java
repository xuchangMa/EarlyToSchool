package EarlyToSchool.Manage;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import Mongodb.MongoDataBase;

public class SystemRoleConfigurationManage {
	// 查询所有权限配置数据
	public static String SystemRoleConfigurationList() {
		// 查询角色信息数据
		BasicDBObject rolequery = new BasicDBObject();
		rolequery.append(QueryOperators.OR, new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
		DBCursor roletable = MongoDataBase.ConditionQuery("SystemRole", rolequery);
		//获取所有应用集
		DBCollection ApplicationSetDataSet = MongoDataBase.ConditionQuery("SystemApplicationSet");
		//获取所有权限配置集
		DBCollection ConfigurationDataSet = MongoDataBase.ConditionQuery("SystemRoleConfiguration");
		// 绑定角色json数据
		String Data = "[{\"SystemRole\":[";
		String ConfigurationData = "";
		while (roletable.hasNext()) {
			DBObject dbObj = roletable.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"RoleCode\":'" + dbObj.get("RoleCode") + "',\"RoleName\":'"
					+ dbObj.get("RoleName") + "'," + "\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
			// 查询所有应用
			BasicDBObject ApplicationSetquery = new BasicDBObject();
			ApplicationSetquery.append(QueryOperators.AND,
					new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
			DBCursor ApplicationSettable = ApplicationSetDataSet.find(ApplicationSetquery);
			while (ApplicationSettable.hasNext()) {
				DBObject ApplicationSetObjdbObj = ApplicationSettable.next();
				// 绑定权限配置json数据
				BasicDBObject RoleConfiguration = new BasicDBObject();
				RoleConfiguration
						.append(QueryOperators.AND,
								new BasicDBObject[] { new BasicDBObject("RoleCode",
										dbObj.get("RoleCode")),
								new BasicDBObject("ApplicationCode", ApplicationSetObjdbObj.get("ApplicationCode")) });
				DBCursor Configurationtable = ConfigurationDataSet.find(RoleConfiguration);
				// 查询当前角色的配置
				if (Configurationtable.count() > 0) {
					// 若有配置则绑定配置
					while (Configurationtable.hasNext()) {
						DBObject RoleConfigurationdbObj = Configurationtable.next();
						ConfigurationData += "{\"Id\":'" + RoleConfigurationdbObj.get("_id") + "',\"RoleCode\":'"
								+ RoleConfigurationdbObj.get("RoleCode") + "',\"RoleName\":'"
								+ RoleConfigurationdbObj.get("RoleName") + "'," + "\"ApplicationCode\":'"
								+ RoleConfigurationdbObj.get("ApplicationCode") + "'," + "\"ApplicationName\":'"
								+ RoleConfigurationdbObj.get("ApplicationName") + "'," + "\"ApplicationPosition\":'"
								+ RoleConfigurationdbObj.get("ApplicationPosition") + "'," + "\"View\":'"
								+ RoleConfigurationdbObj.get("View") + "'," + "\"AddUpdate\":'"
								+ RoleConfigurationdbObj.get("AddUpdate") + "'," + "\"Delete\":'"
								+ RoleConfigurationdbObj.get("Delete") + "'," + "\"ImportExport\":'"
								+ RoleConfigurationdbObj.get("ImportExport") + "'," + "\"Upload\":'"
								+ RoleConfigurationdbObj.get("Upload") + "'," + "\"Download\":'"
								+ RoleConfigurationdbObj.get("Download") + "'},";
					}
				} else {
					// 若没有配置则默认配置
					ConfigurationData += "{\"Id\":'" + java.util.UUID.randomUUID() + "',\"RoleCode\":'"
							+ dbObj.get("RoleCode") + "',\"RoleName\":'" + dbObj.get("RoleName") + "',"
							+ "\"ApplicationCode\":'" + ApplicationSetObjdbObj.get("ApplicationCode") + "',"
							+ "\"ApplicationName\":'" + ApplicationSetObjdbObj.get("ApplicationName") + "',"
							+ "\"ApplicationPosition\":'" + ApplicationSetObjdbObj.get("ApplicationPosition") + "',"
							+ "\"View\":'1'," + "\"AddUpdate\":'1'," + "\"Delete\":'1'," + "\"ImportExport\":'1',"
							+ "\"Upload\":'1'," + "\"Download\":'1'},";
				}
				Configurationtable = null;
			}
			ApplicationSettable = null;
		}
		Data = Data.substring(0, Data.length() - 1);
		Data += "]},{\"SystemRoleConfiguration\":[";
		Data = Data + ConfigurationData.substring(0, ConfigurationData.length() - 1);
		Data += "]}]";
		roletable = null;
		MongoDataBase.drop();//关闭数据库连接
		return Data;
	}

	// 修改权限配置数据
	public static String UpdateRoleConfigurationData(HttpServletRequest request) {
		String Id = request.getParameter("Id");
		String RoleCode = request.getParameter("RoleCode");
		String RoleName = request.getParameter("RoleName");
		String ApplicationCode = request.getParameter("ApplicationCode");
		String ApplicationName = request.getParameter("ApplicationName");
		String ApplicationPosition = request.getParameter("ApplicationPosition");
		String View = request.getParameter("View");
		String AddUpdate = request.getParameter("AddUpdate");
		String ImportExport = request.getParameter("ImportExport");
		String Upload = request.getParameter("Upload");
		String Download = request.getParameter("Download");				
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("RoleCode", RoleCode);
		newDocument.put("RoleName", RoleName);
		newDocument.put("ApplicationCode", ApplicationCode);
		newDocument.put("ApplicationName", ApplicationName);
		newDocument.put("ApplicationPosition", ApplicationPosition);
		newDocument.put("View", View);
		newDocument.put("AddUpdate", AddUpdate);
		newDocument.put("ImportExport", ImportExport);
		newDocument.put("Upload", Upload);
		newDocument.put("Download", Download);
		
		BasicDBObject RoleConfiguration = new BasicDBObject();
		RoleConfiguration.append(QueryOperators.AND,
						new BasicDBObject[] { 
						new BasicDBObject("RoleCode", RoleCode),
						new BasicDBObject("ApplicationCode", ApplicationCode) });
		DBCursor Configurationtable = MongoDataBase.ConditionQuery("SystemRoleConfiguration",
				RoleConfiguration);
		if (Configurationtable.count() > 0){
			if (MongoDataBase.Update("SystemRoleConfiguration", RoleConfiguration, newDocument)) {
				return SystemRoleConfigurationList();
			} else {
				return "No";
			}
		}
		else{
			if (MongoDataBase.Insert("SystemRoleConfiguration", newDocument)) {
				return SystemRoleConfigurationList();
			} else {
				return "No";
			}
		}
		
	}

}
