package EarlyToSchool.Manage;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import Mongodb.MongoDataBase;

public class SystemApplicationSetManage {
	// 定义表名
	private static String TableName = "SystemApplicationSet";

	// 查询所有应用数据数据
	public static String GetSystemApplicationSetList() {
		BasicDBObject query = new BasicDBObject();
		query.append(QueryOperators.OR,
				new BasicDBObject[] { new BasicDBObject("DeleteFlag", "0"), new BasicDBObject("DeleteFlag", "1") });
		DBCursor table = MongoDataBase.ConditionQuery(TableName, query);
		String Data = "[";
		while (table.hasNext()) {
			DBObject dbObj = table.next();
			Data += "{\"Id\":'" + dbObj.get("_id") + "',\"ApplicationCode\":'" + dbObj.get("ApplicationCode")
					+ "',\"ApplicationName\":'" + dbObj.get("ApplicationName") + "'," + "\"ApplicationIcon\":'"
					+ dbObj.get("ApplicationIcon") + "',\"ApplicationGrade\":'" + dbObj.get("ApplicationGrade")
					+ "',\"MountFlag\":'" + dbObj.get("MountFlag") + "'," + "\"ApplicationURL\":'"
					+ dbObj.get("ApplicationURL") + "',\"ApplicationPosition\":'" + dbObj.get("ApplicationPosition")
					+ "',\"DeleteFlag\":'" + dbObj.get("DeleteFlag") + "'},";
		}
		query = null;
		table = null;
		if (Data == "[") {
			return "";
		}
		Data = Data.substring(0, Data.length() - 1) + "]";
		MongoDataBase.drop();//关闭数据库连接
		return Data;
	}

	// 插入应用数据
	public static String InsertSystemApplicationSetData(HttpServletRequest request) {
		String ApplicationCode = request.getParameter("ApplicationCode");
		String ApplicationName = request.getParameter("ApplicationName");
		String ApplicationIcon = request.getParameter("ApplicationIcon");
		String ApplicationGrade = request.getParameter("ApplicationGrade");
		String MountFlag = request.getParameter("MountFlag");
		String ApplicationURL = request.getParameter("ApplicationURL");
		String ApplicationPosition = request.getParameter("ApplicationPosition");
		String DeleteFlag = request.getParameter("DeleteFlag");

		BasicDBObject doc = new BasicDBObject();
		doc.append("ApplicationCode", ApplicationCode);
		doc.append("ApplicationName", ApplicationName);
		doc.append("ApplicationIcon", ApplicationIcon);
		doc.append("ApplicationGrade", ApplicationGrade);
		doc.append("MountFlag", MountFlag);
		doc.append("ApplicationURL", ApplicationURL);
		doc.append("ApplicationPosition", ApplicationPosition);
		doc.append("DeleteFlag", DeleteFlag);
		if (MongoDataBase.Insert(TableName, doc)) {
			return GetSystemApplicationSetList();
		} else {
			return "No";
		}
	}

	// 修改Role数据
	public static String UpdateSystemApplicationSetData(HttpServletRequest request) {
		String Id = request.getParameter("Id");
		String ApplicationCode = request.getParameter("ApplicationCode");
		String ApplicationName = request.getParameter("ApplicationName");
		String ApplicationIcon = request.getParameter("ApplicationIcon");
		String ApplicationGrade = request.getParameter("ApplicationGrade");
		String MountFlag = request.getParameter("MountFlag");
		String ApplicationURL = request.getParameter("ApplicationURL");
		String ApplicationPosition = request.getParameter("ApplicationPosition");
		String DeleteFlag = request.getParameter("DeleteFlag");

		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(Id));

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("ApplicationCode", ApplicationCode);
		newDocument.put("ApplicationName", ApplicationName);
		newDocument.put("ApplicationIcon", ApplicationIcon);
		newDocument.put("ApplicationGrade", ApplicationGrade);
		newDocument.put("MountFlag", MountFlag);
		newDocument.put("ApplicationURL", ApplicationURL);
		newDocument.put("ApplicationPosition", ApplicationPosition);
		newDocument.put("DeleteFlag", DeleteFlag);

		if (MongoDataBase.Update(TableName, query, newDocument)) {
			return GetSystemApplicationSetList();
		} else {
			return "No";
		}
	}

	// 删除Role数据
	public static String DeleteSystemApplicationSetData(HttpServletRequest request) {
		String Id = request.getParameter("Id");

		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(Id));

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("DeleteFlag", "2");

		if (MongoDataBase.Update(TableName, query, newDocument)) {
			return GetSystemApplicationSetList();
		} else {
			return "No";
		}
	}
}
