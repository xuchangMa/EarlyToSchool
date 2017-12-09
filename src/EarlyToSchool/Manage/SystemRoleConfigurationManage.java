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
		// 绑定角色json数据
		String Data = "[{\"SystemRole\":[";
		String[][] RoleList = new String[roletable.count()][2];
		int i = 0;
		while (roletable.hasNext()) {
			DBObject dbObj = roletable.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"RoleCode\":'" + dbObj.get("RoleCode") + "',\"RoleName\":'"
					+ dbObj.get("RoleName") + "'," + "\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
			// 把角色编码放进数组
			RoleList[i][0] = dbObj.get("RoleCode").toString();
			RoleList[i][1] = dbObj.get("RoleName").toString();
			i++;
		}
		Data = Data.substring(0, Data.length() - 1);
		Data += "]},{\"SystemRoleConfiguration\":[";
		MongoDataBase.drop();// 关闭数据库连接

		// 查询所有应用
		BasicDBObject ApplicationSetquery = new BasicDBObject();
		ApplicationSetquery.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
		// 获取所有应用集
		DBCursor ApplicationSetDataSet = MongoDataBase.ConditionQuery("SystemApplicationSet", ApplicationSetquery);
		// 定义应用集数组
		String[][] ApplicationSetDataSetList = new String[ApplicationSetDataSet.count()][3];
		i = 0;
		while (ApplicationSetDataSet.hasNext()) {
			DBObject ApplicationSetObjdbObj = ApplicationSetDataSet.next();
			ApplicationSetDataSetList[i][0] = ApplicationSetObjdbObj.get("ApplicationCode").toString();
			ApplicationSetDataSetList[i][1] = ApplicationSetObjdbObj.get("ApplicationName").toString();
			ApplicationSetDataSetList[i][2] = ApplicationSetObjdbObj.get("ApplicationPosition").toString();
			i++;
		}
		MongoDataBase.drop();// 关闭数据库连接

		String ConfigurationData = "";
		for (String[] RoleCode : RoleList) {
			for (String[] Application : ApplicationSetDataSetList) {
				// 绑定权限配置json数据
				BasicDBObject RoleConfiguration = new BasicDBObject();
				RoleConfiguration.append(QueryOperators.AND,
						new BasicDBObject[] { new BasicDBObject("RoleCode", RoleCode[0]),
								new BasicDBObject("ApplicationCode", Application[0]) });
				// 获取所有权限配置集
				DBCursor Configurationtable = MongoDataBase.ConditionQuery("SystemRoleConfiguration",
						RoleConfiguration);
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
					MongoDataBase.drop();// 关闭数据库连接
				} else {
					// 若没有配置则默认配置
					ConfigurationData += "{\"Id\":'" + java.util.UUID.randomUUID() + "',\"RoleCode\":'" + RoleCode[0]
							+ "',\"RoleName\":'" + RoleCode[1] + "'," + "\"ApplicationCode\":'" + Application[0] + "',"
							+ "\"ApplicationName\":'" + Application[1] + "'," + "\"ApplicationPosition\":'"
							+ Application[2] + "'," + "\"View\":'1'," + "\"AddUpdate\":'1'," + "\"Delete\":'1',"
							+ "\"ImportExport\":'1'," + "\"Upload\":'1'," + "\"Download\":'1'},";
				}
				Configurationtable = null;
				MongoDataBase.drop();// 关闭数据库连接
			}
		}

		Data = Data + ConfigurationData.substring(0, ConfigurationData.length() - 1);
		Data += "]}]";
		roletable = null;
		RoleList = null;
		ApplicationSetDataSetList = null;

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
		RoleConfiguration.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("RoleCode", RoleCode),
				new BasicDBObject("ApplicationCode", ApplicationCode) });
		DBCursor Configurationtable = MongoDataBase.ConditionQuery("SystemRoleConfiguration", RoleConfiguration);
		if (Configurationtable.count() > 0) {
			if (MongoDataBase.Update("SystemRoleConfiguration", RoleConfiguration, newDocument)) {
				//return SystemRoleConfigurationList();
				return "Yes";
			} else {
				return "No";
			}
		} else {
			if (MongoDataBase.Insert("SystemRoleConfiguration", newDocument)) {
				//return SystemRoleConfigurationList();
				return "Yes";
			} else {
				return "No";
			}
		}

	}

}
