package EarlyToSchool.Manage;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import Mongodb.MongoDataBase;

public class SystemUserManage {
	static String TableName = "SystemUser";

	// ��ѯ�����û�����
	public static String GetSystemUserList() {
		// ��ѯ��ɫ��Ϣ����
		BasicDBObject rolequery = new BasicDBObject();
		rolequery.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0") });
		String Data = "[{\"SystemRole\":[";
		// ��ȡ��ɫ����Ӧ�ü�
		DBCollection SystemRoleDataSet = MongoDataBase.ConditionQuery("SystemRole");
		DBCursor roletable = SystemRoleDataSet.find(rolequery);
		while (roletable.hasNext()) {
			DBObject dbObj = roletable.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"RoleCode\":'" + dbObj.get("RoleCode") + "',\"RoleName\":'"
					+ dbObj.get("RoleName") + "'," + "\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
		}
		Data = Data.substring(0, Data.length() - 1);
		Data += "]},{\"SystemRoleConfiguration\":[";
		BasicDBObject query = new BasicDBObject();
		query.append(QueryOperators.OR,
				new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0"), new BasicDBObject("DeleteFlag", "1") });
		DBCursor table = MongoDataBase.ConditionQuery(TableName, query);
		while (table.hasNext()) {
			DBObject dbObj = table.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"SchoolName\":'" + dbObj.get("SchoolName")
					+ "',\"SchoolCode\":'" + dbObj.get("SchoolCode") + "',\"ClassName\":'" + dbObj.get("ClassName")
					+ "'" + "',\"ClassCode\":'" + dbObj.get("ClassCode") + "',\"RoleCode\":'" + dbObj.get("RoleCode")
					+ "'" + "',\"RoleName\":'" + dbObj.get("RoleName") + "',\"UserCode\":'" + dbObj.get("UserCode")
					+ "'" + "',\"UserName\":'" + dbObj.get("UserName") + "',\"UserIcon\":'" + dbObj.get("UserIcon")
					+ "'" + "',\"UserIDNumber\":'" + dbObj.get("UserIDNumber") + "',\"UserSex\":'"
					+ dbObj.get("UserSex") + "'" + "',\"UserDateOfBirth\":'" + dbObj.get("UserDateOfBirth")
					+ "',\"UserAge\":'" + dbObj.get("UserAge") + "'" + "',\"UserBirthday\":'"
					+ dbObj.get("UserBirthday") + "',\"UserBelonging\":'" + dbObj.get("UserBelonging") + "'"
					+ "',\"UserPassword\":'" + dbObj.get("UserPassword") + "',\"DeleteFlag\":'"
					+ dbObj.get("DeleteFlag") + "'},";
		}
		Data = Data.substring(0, Data.length() - 1);
		Data += "]}]";
		rolequery = null;
		roletable = null;
		table = null;
		MongoDataBase.drop();// �ر����ݿ�����
		return Data;
	}

	// �����û�����
	public static String InsertSystemUserData(HttpServletRequest request) {
		String UserCode = "000000";
		DBCursor roletable = null;
		String State = "Yes";
		int userIndex = 0;
		try {
			// ��ȡ�û�����Ӧ�ü�
			DBCollection SystemUserDataSet = MongoDataBase.ConditionQuery(TableName);
			// ��ѯ��ǰ���֤���Ƿ����
			BasicDBObject userId = new BasicDBObject();
			userId.append(QueryOperators.AND,
					new BasicDBObject[] { new BasicDBObject("UserIDNumber", request.getParameter("UserIDNumber")) });
			if (SystemUserDataSet.find(userId).count() != 0) {
				return "�����֤����ע�ᣡ";
			}

			// ��ѯ����Ƿ����
			BasicDBObject userquery = new BasicDBObject();
			userquery.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("UserCode", UserCode) });
			do {
				// ��̬���ɲ��ظ�UserCode
				UserCode = Integer.toString(new Random().nextInt(999999));
				roletable = SystemUserDataSet.find(userquery);
				userIndex = roletable.count();
			} while (userIndex != 0);
			BasicDBObject doc = new BasicDBObject();
			doc.append("SchoolName", request.getParameter("SchoolName"));
			doc.append("SchoolCode", request.getParameter("SchoolCode"));
			doc.append("ClassName", request.getParameter("ClassName"));
			doc.append("ClassCode", request.getParameter("ClassCode"));
			doc.append("RoleCode", request.getParameter("RoleCode"));
			doc.append("RoleName", request.getParameter("RoleName"));
			doc.append("UserCode", UserCode);
			doc.append("UserName", request.getParameter("UserName"));
			doc.append("UserIcon", request.getParameter("UserIcon"));
			doc.append("UserIDNumber", request.getParameter("UserIDNumber"));
			doc.append("UserSex", request.getParameter("UserSex"));
			doc.append("UserDateOfBirth", request.getParameter("UserDateOfBirth"));
			doc.append("UserAge", request.getParameter("UserAge"));
			doc.append("UserBirthday", request.getParameter("UserBirthday"));
			doc.append("UserBelonging", request.getParameter("UserBelonging"));
			doc.append("UserPassword", request.getParameter("UserPassword"));
			doc.append("DeleteFlag", request.getParameter("DeleteFlag"));
			if (MongoDataBase.Insert(TableName, doc)) {
				State = "Yes";
			} else {
				State = "No";
			}
			SystemUserDataSet = null;
			userId = null;
			userquery = null;
			roletable = null;
			doc = null;
			
		} catch (Exception e) {
			State = e.getMessage();
		}
		
		return State;
	}

	// �޸��û�����
	public static String UpdateSystemUserData(HttpServletRequest request) {
		String State = "Yes";
		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(request.getParameter("Id")));
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("SchoolName", request.getParameter("SchoolName"));
		newDocument.put("SchoolCode", request.getParameter("SchoolCode"));
		newDocument.put("ClassName", request.getParameter("ClassName"));
		newDocument.put("ClassCode", request.getParameter("ClassCode"));
		newDocument.put("RoleCode", request.getParameter("RoleCode"));
		newDocument.put("RoleName", request.getParameter("RoleName"));
		newDocument.put("UserName", request.getParameter("UserName"));
		newDocument.put("UserIcon", request.getParameter("UserIcon"));
		newDocument.put("UserIDNumber", request.getParameter("UserIDNumber"));
		newDocument.put("UserSex", request.getParameter("UserSex"));
		newDocument.put("UserDateOfBirth", request.getParameter("UserDateOfBirth"));
		newDocument.put("UserAge", request.getParameter("UserAge"));
		newDocument.put("UserBirthday", request.getParameter("UserBirthday"));
		newDocument.put("UserBelonging", request.getParameter("UserBelonging"));
		newDocument.put("UserPassword", request.getParameter("UserPassword"));
		newDocument.put("DeleteFlag", request.getParameter("DeleteFlag"));

		if (MongoDataBase.Update(TableName, query, newDocument)) {
			State = GetSystemUserList();
		} else {
			State = "No";
		}
		query = null;
		newDocument = null;
		return State;
	}

	// ɾ���û�����
	public static String DeleteSystemUserData(HttpServletRequest request) {
		String Id = request.getParameter("Id");
		String State = "No";
		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(Id));

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("DeleteFlag", "2");

		if (MongoDataBase.Update(TableName, query, newDocument)) {
			State = GetSystemUserList();
		} else {
			State = "No";
		}
		query = null;
		Id = null;
		newDocument = null;
		return State;
	}

	// �û���½
	public static String LoginSystemUserIs(HttpServletRequest request) {
		String State = "No";
		// ��ȡ���������û���������
		String UserCode = request.getParameter("UserCode");
		String UserPassword = request.getParameter("UserPassword");
		// ��ȡ�û�����Ӧ�ü�
		DBCollection SystemUserDataSet = MongoDataBase.ConditionQuery(TableName);
		// ֧�����֤�Ż��û���ŵ�½
		BasicDBObject userId = new BasicDBObject();
		userId.append(QueryOperators.OR, new BasicDBObject[] { new BasicDBObject("UserIDNumber", UserCode),
				new BasicDBObject("UserCode", UserCode) });

		if (SystemUserDataSet.find(userId).count() > 0) {
			// ���ڵ�ǰ�û�
			userId.append(QueryOperators.AND, new BasicDBObject[] { new BasicDBObject("UserPassword", UserPassword) });
			DBCursor table = SystemUserDataSet.find(userId);
			if (table.count() > 0) {
				// ��½�ɹ�
				while (table.hasNext()) {
					DBObject dbObj = table.next();
					UserCode = dbObj.get("UserCode").toString();
				}
				State = "Yes:" + UserCode;
			} else {
				// ���벻��ȷ
				State = "���벻��ȷ";
			}
			table = null;
		} else {
			// �û���������
			State = "�û���������";
		}
		UserCode = null;
		UserPassword = null;
		SystemUserDataSet = null;
		userId = null;
		
		return State;
	}
}
