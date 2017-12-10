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

	// ��ѯ����Ӧ����������
	public static String GetSystemApplicationSetList(HttpServletRequest request) {
		String Data = "[";
		// ��ȡ���������û���
		String Id = request.getParameter("Id").toString();		
		// ��ȡ�û�����Ӧ�ü�
		BasicDBObject userId = new BasicDBObject();
		userId.append(QueryOperators.AND,
				new BasicDBObject[] { new BasicDBObject("_id", new ObjectId(Id)), new BasicDBObject("DeleteFlag", "0") });
		DBCursor table = MongoDataBase.ConditionQuery(TableName, userId);
		// ��ȡ�û���ɫ
		String RoleCode = "0000";
		while (table.hasNext()) {
			DBObject dbObj = table.next();
			RoleCode = dbObj.get("RoleCode").toString();
		}
		userId = new BasicDBObject();
		table = null;
		MongoDataBase.drop();// �ر����ݿ�����
		userId.append(QueryOperators.AND,
				new BasicDBObject[] { new BasicDBObject("RoleCode", RoleCode), new BasicDBObject("DeleteFlag", "0") });
		Integer RoleNumber = MongoDataBase.ConditionQuery("SystemRole", userId).count();
		MongoDataBase.drop();// �ر����ݿ�����

		// ��ȡ����Ӧ��
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
		MongoDataBase.drop();// �ر����ݿ�����

		// �жϵ�ǰ��ɫ��Ч
		if (RoleNumber > 0) {
			// ��ȡ��ǰ��ɫ��Ȩ�޵�Ӧ��
			userId = new BasicDBObject();
			table = null;
			// ��ȡ��ǰ��ɫ�Ĳ˵���������
			userId.append(QueryOperators.AND,
					new BasicDBObject[] { new BasicDBObject("RoleCode", RoleCode),
							new BasicDBObject("View", "0") });
			BasicDBObject sortObject = new BasicDBObject();
			// mongodb�а�age�ֶε����ѯ��-1�ǵ���1������
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
			MongoDataBase.drop();// �ر����ݿ�����
			if (RoleConfig.length > 0) {
				for(String ApplicationCode : RoleConfig){
					Data += BangDingMenuJson(ApplicationDataSetList, ApplicationCode);
				}
				
			} else if (RoleCode.equals("1001")) {
				// �жϹ���Ա���
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

	// ����������
	static String BangDingMenuJson(String[][] SystemApplicationDataSet, String ApplicationCode) {
		String Data = "";
		for (String[] itemApp : SystemApplicationDataSet) {
			// �жϵ�ǰӦ��
			if (itemApp[0].equals(ApplicationCode) && itemApp[4].equals("1")) {
				// �жϸ�Ӧ���Ƿ������Ӧ��
				if (itemApp[5].equals("0")) {
					// ����Ӧ��
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
						MongoDataBase.drop(); // �ر����ݿ�����
					}
					SubMenu = SubMenu.substring(0, SubMenu.length() - 1);
					Data += "{\"MountFlag\":'" + itemApp[5] + "',\"ApplicationName\":'" + itemApp[1]
							+ "',\"ApplicationIcon\":'" + itemApp[2] + "'," + "\"ApplicationURL\":[" + SubMenu + "]},";
				} else {
					// ������Ӧ��
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
