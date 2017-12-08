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
	// ��ѯ����Ȩ����������
	public static String SystemRoleConfigurationList() {
		// ��ѯ��ɫ��Ϣ����
		BasicDBObject rolequery = new BasicDBObject();
		rolequery.append(QueryOperators.OR, new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
		DBCursor roletable = MongoDataBase.ConditionQuery("SystemRole", rolequery);
		//��ȡ����Ӧ�ü�
		DBCollection ApplicationSetDataSet = MongoDataBase.ConditionQuery("SystemApplicationSet");
		//��ȡ����Ȩ�����ü�
		DBCollection ConfigurationDataSet = MongoDataBase.ConditionQuery("SystemRoleConfiguration");
		// �󶨽�ɫjson����
		String Data = "[{\"SystemRole\":[";
		String ConfigurationData = "";
		while (roletable.hasNext()) {
			DBObject dbObj = roletable.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"RoleCode\":'" + dbObj.get("RoleCode") + "',\"RoleName\":'"
					+ dbObj.get("RoleName") + "'," + "\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
			// ��ѯ����Ӧ��
			BasicDBObject ApplicationSetquery = new BasicDBObject();
			ApplicationSetquery.append(QueryOperators.AND,
					new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
			DBCursor ApplicationSettable = ApplicationSetDataSet.find(ApplicationSetquery);
			while (ApplicationSettable.hasNext()) {
				DBObject ApplicationSetObjdbObj = ApplicationSettable.next();
				// ��Ȩ������json����
				BasicDBObject RoleConfiguration = new BasicDBObject();
				RoleConfiguration
						.append(QueryOperators.AND,
								new BasicDBObject[] { new BasicDBObject("RoleCode",
										dbObj.get("RoleCode")),
								new BasicDBObject("ApplicationCode", ApplicationSetObjdbObj.get("ApplicationCode")) });
				DBCursor Configurationtable = ConfigurationDataSet.find(RoleConfiguration);
				// ��ѯ��ǰ��ɫ������
				if (Configurationtable.count() > 0) {
					// ���������������
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
					// ��û��������Ĭ������
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
		MongoDataBase.drop();//�ر����ݿ�����
		return Data;
	}

	// �޸�Ȩ����������
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
