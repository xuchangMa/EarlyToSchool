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
		// �󶨽�ɫjson����
		String Data = "[{\"SystemRole\":[";
		String[][] RoleList = new String[roletable.count()][2];
		int i = 0;
		while (roletable.hasNext()) {
			DBObject dbObj = roletable.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"RoleCode\":'" + dbObj.get("RoleCode") + "',\"RoleName\":'"
					+ dbObj.get("RoleName") + "'," + "\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
			// �ѽ�ɫ����Ž�����
			RoleList[i][0] = dbObj.get("RoleCode").toString();
			RoleList[i][1] = dbObj.get("RoleName").toString();
			i++;
		}
		Data = Data.substring(0, Data.length() - 1);
		Data += "]},{\"SystemRoleConfiguration\":[";
		MongoDataBase.drop();// �ر����ݿ�����

		// ��ѯ����Ӧ��
		BasicDBObject ApplicationSetquery = new BasicDBObject();
		ApplicationSetquery.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
		// ��ȡ����Ӧ�ü�
		DBCursor ApplicationSetDataSet = MongoDataBase.ConditionQuery("SystemApplicationSet", ApplicationSetquery);
		// ����Ӧ�ü�����
		String[][] ApplicationSetDataSetList = new String[ApplicationSetDataSet.count()][3];
		i = 0;
		while (ApplicationSetDataSet.hasNext()) {
			DBObject ApplicationSetObjdbObj = ApplicationSetDataSet.next();
			ApplicationSetDataSetList[i][0] = ApplicationSetObjdbObj.get("ApplicationCode").toString();
			ApplicationSetDataSetList[i][1] = ApplicationSetObjdbObj.get("ApplicationName").toString();
			ApplicationSetDataSetList[i][2] = ApplicationSetObjdbObj.get("ApplicationPosition").toString();
			i++;
		}
		MongoDataBase.drop();// �ر����ݿ�����

		String ConfigurationData = "";
		for (String[] RoleCode : RoleList) {
			for (String[] Application : ApplicationSetDataSetList) {
				// ��Ȩ������json����
				BasicDBObject RoleConfiguration = new BasicDBObject();
				RoleConfiguration.append(QueryOperators.AND,
						new BasicDBObject[] { new BasicDBObject("RoleCode", RoleCode[0]),
								new BasicDBObject("ApplicationCode", Application[0]) });
				// ��ȡ����Ȩ�����ü�
				DBCursor Configurationtable = MongoDataBase.ConditionQuery("SystemRoleConfiguration",
						RoleConfiguration);
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
					MongoDataBase.drop();// �ر����ݿ�����
				} else {
					// ��û��������Ĭ������
					ConfigurationData += "{\"Id\":'" + java.util.UUID.randomUUID() + "',\"RoleCode\":'" + RoleCode[0]
							+ "',\"RoleName\":'" + RoleCode[1] + "'," + "\"ApplicationCode\":'" + Application[0] + "',"
							+ "\"ApplicationName\":'" + Application[1] + "'," + "\"ApplicationPosition\":'"
							+ Application[2] + "'," + "\"View\":'1'," + "\"AddUpdate\":'1'," + "\"Delete\":'1',"
							+ "\"ImportExport\":'1'," + "\"Upload\":'1'," + "\"Download\":'1'},";
				}
				Configurationtable = null;
				MongoDataBase.drop();// �ر����ݿ�����
			}
		}

		Data = Data + ConfigurationData.substring(0, ConfigurationData.length() - 1);
		Data += "]}]";
		roletable = null;
		RoleList = null;
		ApplicationSetDataSetList = null;

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
