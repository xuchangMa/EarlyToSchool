package EarlyToSchool.Manage;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import Mongodb.MongoDataBase;

public class SystemRoleConfigurationManage {
	// ��ѯ����Role����
	public static String SystemRoleConfigurationList() {
		//��ѯ��ɫ��Ϣ����
		BasicDBObject rolequery = new BasicDBObject();
		rolequery.append(QueryOperators.OR, new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
		DBCursor roletable = MongoDataBase.ConditionQuery("SystemRole", rolequery);
		
		//�󶨽�ɫjson����
		String Data = "[{\"SystemRole\":[";
		String ConfigurationData = "";
		while (roletable.hasNext()) {
			DBObject dbObj = roletable.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"RoleCode\":'" + dbObj.get("RoleCode") + "',\"RoleName\":'"
					+ dbObj.get("RoleName") + "'," + "\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
		
			//��Ȩ������json����
			BasicDBObject RoleConfiguration = new BasicDBObject();
			RoleConfiguration.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("RoleCode", dbObj.get("RoleCode")) });
			DBCursor Configurationtable = MongoDataBase.ConditionQuery("SystemRoleConfiguration", RoleConfiguration);
			//��ѯ��ǰ��ɫ������
			if (Configurationtable.count() > 0) {
				//���������������
				while (Configurationtable.hasNext()) {
					DBObject RoleConfigurationdbObj = Configurationtable.next();
					ConfigurationData += "{\"Id\":'" + RoleConfigurationdbObj.get("_id") + 
							"',\"RoleCode\":'" + RoleConfigurationdbObj.get("RoleCode") + 
							"',\"RoleName\":'" + RoleConfigurationdbObj.get("RoleName") + "'," + 
							"\"ApplicationCode\":'" + RoleConfigurationdbObj.get("ApplicationCode") + "',"+
							"\"ApplicationName\":'" + RoleConfigurationdbObj.get("ApplicationName") + "',"+	
							"\"View\":'" + RoleConfigurationdbObj.get("View") + "',"+
							"\"AddUpdate\":'" + RoleConfigurationdbObj.get("AddUpdate") + "',"+
							"\"Delete\":'" + RoleConfigurationdbObj.get("Delete") + "',"+
							"\"ImportExport\":'" + RoleConfigurationdbObj.get("ImportExport") + "',"+
							"\"Upload\":'" + RoleConfigurationdbObj.get("Upload") + "',"+
							"\"Download\":'" + RoleConfigurationdbObj.get("Download") + "'},";
				}
			} else {
				//��û��������Ĭ������
				BasicDBObject ApplicationSetquery = new BasicDBObject();
				ApplicationSetquery.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
				DBCursor ApplicationSettable = MongoDataBase.ConditionQuery("SystemApplicationSet", ApplicationSetquery);
				while (ApplicationSettable.hasNext()) {
					DBObject ApplicationSetObjdbObj = ApplicationSettable.next();
					ConfigurationData += "{\"Id\":'" + java.util.UUID.randomUUID() + 
							"',\"RoleCode\":'" + dbObj.get("RoleCode") + 
							"',\"RoleName\":'" + dbObj.get("RoleName") + "'," + 
							"\"ApplicationCode\":'" + ApplicationSetObjdbObj.get("ApplicationCode") + "',"+
							"\"ApplicationName\":'" + ApplicationSetObjdbObj.get("ApplicationName") + "',"+							
							"\"View\":'1',"+
							"\"AddUpdate\":'1',"+
							"\"Delete\":'1',"+
							"\"ImportExport\":'1',"+
							"\"Upload\":'1',"+
							"\"Download\":'1'},";
				}

				ApplicationSettable = null;
			}

			Configurationtable = null;
		}
		Data = Data.substring(0, Data.length() - 1);		
		Data += "]},{\"SystemRoleConfiguration\":[";
		Data = Data + ConfigurationData.substring(0, ConfigurationData.length() - 1);
		Data += "]}]";
		roletable = null;
		return Data;
	}
}
