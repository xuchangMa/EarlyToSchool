package EarlyToSchool.Manage;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import Mongodb.MongoDataBase;

public class IndexManage {
	static String TableName = "SystemUser";

	// 查询所有应用数据数据
	public static String GetSystemApplicationSetList(HttpServletRequest request) {
		String Data = "[";
		// 获取传进来的用户名
		String Id = request.getParameter("Id").toString();		
		// 获取用户所有应用集
		BasicDBObject userId = new BasicDBObject();
		userId.append(QueryOperators.AND,
				new BasicDBObject[] { new BasicDBObject("_id", new ObjectId(Id)), new BasicDBObject("DeleteFlag", "0") });
		DBCursor table = MongoDataBase.ConditionQuery(TableName, userId);
		// 获取用户角色
		String RoleCode = "0000";
		while (table.hasNext()) {
			DBObject dbObj = table.next();
			RoleCode = dbObj.get("RoleCode").toString();
		}
		userId = new BasicDBObject();
		table = null;
		MongoDataBase.drop();// 关闭数据库连接
		userId.append(QueryOperators.AND,
				new BasicDBObject[] { new BasicDBObject("RoleCode", RoleCode), new BasicDBObject("DeleteFlag", "0") });
		Integer RoleNumber = MongoDataBase.ConditionQuery("SystemRole", userId).count();
		MongoDataBase.drop();// 关闭数据库连接

		// 获取所有应用
		BasicDBObject App = new BasicDBObject();
		App.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
		DBCursor SystemApplicationDataSet = MongoDataBase.ConditionQuery("SystemApplicationSet", App);
		String[][] ApplicationDataSetList = new String[SystemApplicationDataSet.count()][7];
		int i = 0;
		while (SystemApplicationDataSet.hasNext()) {
			DBObject AppObj = SystemApplicationDataSet.next();
			ApplicationDataSetList[i][0] = AppObj.get("ApplicationCode").toString();
			ApplicationDataSetList[i][1] = AppObj.get("ApplicationName").toString();
			ApplicationDataSetList[i][2] = AppObj.get("ApplicationIcon").toString();
			ApplicationDataSetList[i][3] = AppObj.get("ApplicationURL").toString();
			ApplicationDataSetList[i][4] = AppObj.get("ApplicationGrade").toString();
			ApplicationDataSetList[i][5] = AppObj.get("MountFlag").toString();
			ApplicationDataSetList[i][6] = AppObj.get("DeleteFlag").toString();
			i++;
		}
		SystemApplicationDataSet = null;
		MongoDataBase.drop();// 关闭数据库连接

		// 判断当前角色生效
		if (RoleNumber > 0) {
			// 获取当前角色有权限的应用
			userId = new BasicDBObject();
			table = null;
			// 获取当前角色的菜单配置配置
			userId.append(QueryOperators.AND,
					new BasicDBObject[] { new BasicDBObject("RoleCode", RoleCode),
							new BasicDBObject("View", "0") });
			BasicDBObject sortObject = new BasicDBObject();
			// mongodb中按age字段倒序查询（-1是倒序，1是正序）
			sortObject.put("ApplicationPosition", 1);
			table = MongoDataBase.ConditionQuery("SystemRoleConfiguration", userId).sort(sortObject);
			String[] RoleConfig= new String[table.count()];
			i = 0;
			while (table.hasNext()) {
				DBObject dbObj = table.next();
				RoleConfig[i] = dbObj.get("ApplicationCode").toString();			
				dbObj = null;
				i++;
			}
			MongoDataBase.drop();// 关闭数据库连接
			if (RoleConfig.length > 0) {
				for(String ApplicationCode : RoleConfig){
					Data += BangDingMenuJson(ApplicationDataSetList, ApplicationCode);
				}
				
			} else if (RoleCode.equals("1001")) {
				// 判断管理员身份
				Data += BangDingMenuJson(ApplicationDataSetList, "A0007");
				App = null;
			}
		}
		userId = null;
		table = null;
		if (Data == "[") {
			return "No";
		}
		Data = Data.substring(0, Data.length() - 1) + "]";
		return Data;
	}

	// 绑定已有数据
	static String BangDingMenuJson(String[][] SystemApplicationDataSet, String ApplicationCode) {
		String Data = "";
		for (String[] itemApp : SystemApplicationDataSet) {
			// 判断当前应用
			if (itemApp[0].equals(ApplicationCode) && itemApp[4].equals("1")) {
				// 判断该应用是否挂载子应用
				if (itemApp[5].equals("0")) {
					// 挂载应用
					String SubMenu = "";
					// dbObj.get("ApplicationURL")
					String[] SubCode = itemApp[3].toString().split(",");
					for (String item : SubCode) {
						BasicDBObject App2 = new BasicDBObject();
						App2.append(QueryOperators.AND, new BasicDBObject[] {
								new BasicDBObject("ApplicationCode", item), new BasicDBObject("View", "0") });
						DBCursor tableRoleConfiguration = MongoDataBase.ConditionQuery("SystemRoleConfiguration", App2);
						if (tableRoleConfiguration.count() > 0) {
							for (String[] ItemSubMenu : SystemApplicationDataSet) {
								if (ItemSubMenu[0].equals(item)) {
									SubMenu += "{\"MountFlag\":'" + ItemSubMenu[5] + "',\"ApplicationName\":'"
											+ ItemSubMenu[1] + "',\"ApplicationIcon\":'" + ItemSubMenu[2] + "',"
											+ "\"ApplicationURL\":'" + ItemSubMenu[3] + "'},";
									continue;
								}
							}
						}
						tableRoleConfiguration = null;
						App2 = null;
						MongoDataBase.drop(); // 关闭数据库链接
					}
					SubMenu = SubMenu.substring(0, SubMenu.length() - 1);
					Data += "{\"MountFlag\":'" + itemApp[5] + "',\"ApplicationName\":'" + itemApp[1]
							+ "',\"ApplicationIcon\":'" + itemApp[2] + "'," + "\"ApplicationURL\":[" + SubMenu + "]},";
				} else {
					// 不挂载应用
					Data += "{\"MountFlag\":'" + itemApp[5] + "',\"ApplicationName\":'" + itemApp[1]
							+ "',\"ApplicationIcon\":'" + itemApp[2] + "'," + "\"ApplicationURL\":'" + itemApp[3]
							+ "'},";
				}
				continue;
			}
		}
		return Data;
	}

}
