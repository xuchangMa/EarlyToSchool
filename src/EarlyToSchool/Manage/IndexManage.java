package EarlyToSchool.Manage;

import javax.servlet.http.HttpServletRequest;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
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
		String UserCode = request.getParameter("UserCode").toString();
		// ��ȡ�û�����Ӧ�ü�
		BasicDBObject userId = new BasicDBObject();
		userId.append(QueryOperators.AND,
				new BasicDBObject[] { new BasicDBObject("UserCode", UserCode), new BasicDBObject("DeleteFlag", "0") });
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
		table = MongoDataBase.ConditionQuery("SystemRole", userId);
		// �жϵ�ǰ��ɫ��Ч
		if (table.count() > 0) {
			userId = new BasicDBObject();
			table = null;
			MongoDataBase.drop();// �ر����ݿ�����
			// ��ȡ����Ӧ��
			DBCollection SystemApplicationDataSet = MongoDataBase.ConditionQuery("SystemApplicationSet");
			// ��ȡ��ǰ��ɫ�Ĳ˵���������
			userId.append(QueryOperators.AND,
					new BasicDBObject[] { new BasicDBObject("RoleCode", RoleCode), new BasicDBObject("View", "0") });
			BasicDBObject sortObject = new BasicDBObject();					
			//mongodb�а�age�ֶε����ѯ��-1�ǵ���1������
			sortObject.put("ApplicationPosition",1);
			DBCollection SystemRoleConfiguration = MongoDataBase.ConditionQuery("SystemRoleConfiguration");
			table = SystemRoleConfiguration.find(userId).sort(sortObject);
			if(table.count() > 0){
				while (table.hasNext()) {
					DBObject dbObj = table.next();
					BasicDBObject App = new BasicDBObject();
					App.append(QueryOperators.AND,
							new BasicDBObject[] {
									new BasicDBObject("ApplicationCode", dbObj.get("ApplicationCode").toString()),
									new BasicDBObject("ApplicationGrade", "1"), new BasicDBObject("DeleteFlag", "0") });										
					Data += BangDingMenuJson(SystemApplicationDataSet, App, SystemRoleConfiguration);														
					dbObj = null;
					App = null;
				}	
				SystemApplicationDataSet = null;
				SystemRoleConfiguration = null;
				MongoDataBase.drop();// �ر����ݿ�����
			}
			else if(RoleCode.equals("1001")){
				//�жϹ���Ա���
				BasicDBObject App = new BasicDBObject();
				App.append(QueryOperators.AND,
						new BasicDBObject[] {
								new BasicDBObject("ApplicationCode", "A0007"),
								new BasicDBObject("ApplicationGrade", "1"), new BasicDBObject("DeleteFlag", "0") });								
				Data += BangDingMenuJson(SystemApplicationDataSet, App);
				App = null;
				MongoDataBase.drop();// �ر����ݿ�����
			}
			SystemApplicationDataSet = null;
			MongoDataBase.drop();// �ر����ݿ�����
			
		}		
		userId = null;
		table = null;
		if (Data == "[") {
			return "No";
		}
		Data = Data.substring(0, Data.length() - 1) + "]";
		return Data;
	}
	static String BangDingMenuJson(DBCollection SystemApplicationDataSet, BasicDBObject App, DBCollection SystemRoleConfiguration){
		String Data = "";
		DBCursor SystemApplication = SystemApplicationDataSet.find(App);
		while (SystemApplication.hasNext()) {
			DBObject AppObj = SystemApplication.next();
			String MountFlag = String.valueOf(AppObj.get("MountFlag"));
			if (MountFlag.equals("0")) {
				// ����Ӧ��
				String SubMenu = "";
				//dbObj.get("ApplicationURL")
				String[] SubCode = AppObj.get("ApplicationURL").toString().split(",");
				for(String item : SubCode){
					BasicDBObject App2 = new BasicDBObject();
					App2.append(QueryOperators.AND,
							new BasicDBObject[] {
									new BasicDBObject("ApplicationCode", item ),
									new BasicDBObject("View", "0") });
					DBCursor tableRoleConfiguration = SystemRoleConfiguration.find(App2);
					if(tableRoleConfiguration.count() > 0){
						App2 = new BasicDBObject();
						App2.append(QueryOperators.AND,
								new BasicDBObject[] {
										new BasicDBObject("ApplicationCode", item ),
										new BasicDBObject("ApplicationGrade", "2"), new BasicDBObject("DeleteFlag", "0") });
						DBCursor tableSubMenu = SystemApplicationDataSet.find(App2);
						while (tableSubMenu.hasNext()){
							DBObject AppObj2 = tableSubMenu.next();
							SubMenu += "{\"MountFlag\":'" + AppObj2.get("MountFlag").toString() + "',\"ApplicationName\":'"
									+ AppObj2.get("ApplicationName").toString() + "',\"ApplicationIcon\":'"
									+ AppObj2.get("ApplicationIcon").toString() + "'," + "\"ApplicationURL\":'"
									+ AppObj2.get("ApplicationURL").toString() + "'},";
							AppObj2 = null;
						}
						tableSubMenu = null;
					}
					tableRoleConfiguration = null;
					App2 = null;
				}
				SubMenu = SubMenu.substring(0, SubMenu.length() - 1);
				Data += "{\"MountFlag\":'" + AppObj.get("MountFlag").toString() + "',\"ApplicationName\":'"
						+ AppObj.get("ApplicationName").toString() + "',\"ApplicationIcon\":'"
						+ AppObj.get("ApplicationIcon").toString() + "'," + "\"ApplicationURL\":["
						+ SubMenu + "]},";
			} else {
				// ������Ӧ��
				Data += "{\"MountFlag\":'" + AppObj.get("MountFlag").toString() + "',\"ApplicationName\":'"
						+ AppObj.get("ApplicationName").toString() + "',\"ApplicationIcon\":'"
						+ AppObj.get("ApplicationIcon").toString() + "'," + "\"ApplicationURL\":'"
						+ AppObj.get("ApplicationURL").toString() + "'},";
			}
			AppObj = null;
			
		}
		SystemApplication = null;
		return Data;
	}
	//��ʼ��
	static String BangDingMenuJson(DBCollection SystemApplicationDataSet, BasicDBObject App){
		String Data = "";
		DBCursor SystemApplication = SystemApplicationDataSet.find(App);
		while (SystemApplication.hasNext()) {
			DBObject AppObj = SystemApplication.next();
			String MountFlag = String.valueOf(AppObj.get("MountFlag"));
			if (MountFlag.equals("0")) {
				// ����Ӧ��
				String SubMenu = "";
				//dbObj.get("ApplicationURL")
				String[] SubCode = AppObj.get("ApplicationURL").toString().split(",");
				for(String item : SubCode){
					BasicDBObject App2 = new BasicDBObject();
					App2.append(QueryOperators.AND,
							new BasicDBObject[] {
								new BasicDBObject("ApplicationCode", item ),
								new BasicDBObject("ApplicationGrade", "2"), new BasicDBObject("DeleteFlag", "0") });
					DBCursor tableSubMenu = SystemApplicationDataSet.find(App2);
					while (tableSubMenu.hasNext()){
						DBObject AppObj2 = tableSubMenu.next();
						SubMenu += "{\"MountFlag\":'" + AppObj2.get("MountFlag").toString() + "',\"ApplicationName\":'"
								+ AppObj2.get("ApplicationName").toString() + "',\"ApplicationIcon\":'"
								+ AppObj2.get("ApplicationIcon").toString() + "'," + "\"ApplicationURL\":'"
								+ AppObj2.get("ApplicationURL").toString() + "'},";
						AppObj2 = null;
					}
					tableSubMenu = null;					
				}
				SubMenu = SubMenu.substring(0, SubMenu.length() - 1);
				Data += "{\"MountFlag\":'" + AppObj.get("MountFlag").toString() + "',\"ApplicationName\":'"
						+ AppObj.get("ApplicationName").toString() + "',\"ApplicationIcon\":'"
						+ AppObj.get("ApplicationIcon").toString() + "'," + "\"ApplicationURL\":["
						+ SubMenu + "]},";
			} else {
				// ������Ӧ��
				Data += "{\"MountFlag\":'" + AppObj.get("MountFlag").toString() + "',\"ApplicationName\":'"
						+ AppObj.get("ApplicationName").toString() + "',\"ApplicationIcon\":'"
						+ AppObj.get("ApplicationIcon").toString() + "'," + "\"ApplicationURL\":'"
						+ AppObj.get("ApplicationURL").toString() + "'},";
			}
			AppObj = null;
			
		}
		SystemApplication = null;
		return Data;
	}
}
