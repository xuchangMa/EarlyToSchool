package EarlyToSchool.Manage;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import Mongodb.MongoDataBase;

public class SystemUserManage {
	static String TableName = "SystemUser";
	// ��ѯ����Role����
	public static String GetSystemUserList() {
		BasicDBObject query = new BasicDBObject();
		query.append(QueryOperators.OR,
				new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0"), new BasicDBObject("DeleteFlag", "1") });
		DBCursor table = MongoDataBase.ConditionQuery(TableName, query);
		String Data = "[";
		while (table.hasNext()) {
			DBObject dbObj = table.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"SchoolName\":'" + dbObj.get("SchoolName") + 
					"',\"SchoolCode\":'" + dbObj.get("SchoolCode") + "',\"ClassName\":'" + dbObj.get("ClassName") + "'"+
					"',\"ClassCode\":'" + dbObj.get("ClassCode") + "',\"RoleCode\":'" + dbObj.get("RoleCode") + "'"+
					"',\"RoleName\":'" + dbObj.get("RoleName") + "',\"UserCode\":'" + dbObj.get("UserCode") + "'"+
					"',\"UserName\":'" + dbObj.get("UserName") + "',\"UserIcon\":'" + dbObj.get("UserIcon") + "'"+
					"',\"UserIDNumber\":'" + dbObj.get("UserIDNumber") + "',\"UserSex\":'" + dbObj.get("UserSex") + "'"+
					"',\"UserDateOfBirth\":'" + dbObj.get("UserDateOfBirth") + "',\"UserAge\":'" + dbObj.get("UserAge") + "'"+
					"',\"UserBirthday\":'" + dbObj.get("UserBirthday") + "',\"UserBelonging\":'" + dbObj.get("UserBelonging") + "'"+
					"',\"UserPassword\":'" + dbObj.get("UserPassword") + "',\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
		}
		table = null;
		if (Data == "[") {
			return "";
		}
		Data = Data.substring(0, Data.length() - 1) + "]";
		MongoDataBase.drop();//�ر����ݿ�����
		return Data;
	}

	// ����Role����
	public static String InsertSystemUserData(HttpServletRequest request) {
		String RoleCode = request.getParameter("RoleCode");
		String RoleName = request.getParameter("RoleName");
		String DeleteFlag = request.getParameter("DeleteFlag");

		BasicDBObject doc = new BasicDBObject();
		doc.append("RoleCode", RoleCode);
		doc.append("RoleName", RoleName);
		doc.append("DeleteFlag", DeleteFlag);
		if (MongoDataBase.Insert(TableName, doc)) {
			MongoDataBase.drop();//�ر����ݿ�����
			return GetSystemUserList();
		} else {
			return "No";
		}
	}

	// �޸�Role����
	public static String UpdateSystemUserData(HttpServletRequest request) {
		String Id = request.getParameter("Id");
		String RoleCode = request.getParameter("RoleCode");
		String RoleName = request.getParameter("RoleName");
		String DeleteFlag = request.getParameter("DeleteFlag");

		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(Id));

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("RoleCode", RoleCode);
		newDocument.put("RoleName", RoleName);
		newDocument.put("DeleteFlag", DeleteFlag);

		if (MongoDataBase.Update(TableName, query, newDocument)) {
			MongoDataBase.drop();//�ر����ݿ�����
			return GetSystemUserList();
		} else {
			return "No";
		}
	}

	// ɾ��Role����
	public static String DeleteSystemUserData(HttpServletRequest request) {
		String Id = request.getParameter("Id");

		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(Id));

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("DeleteFlag", "2");

		if (MongoDataBase.Update(TableName, query, newDocument)) {
			MongoDataBase.drop();//�ر����ݿ�����
			return GetSystemUserList();
		} else {
			return "No";
		}
	}

}
