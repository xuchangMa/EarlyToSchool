package Mongodb;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoDataBase {
	static Mongo mongoClient;
	//private static com.mongodb.DB mongoDatabase;
	static String host = "localhost";
	static int port = 27017;
	static String databaseName = "EarlyToSchoolDB";

	// 获取数据库服务
	static DB GetDataBaseServer() {
		DB mongoDatabase = null;
		try {
			// 连接到 mongodb 服务
			//MongoClient mongoClient = new MongoClient(host, port);
			mongoClient = new MongoClient(host, port);
			// 连接到数据库
			
			mongoDatabase = mongoClient.getDB(databaseName);
			//mongoDatabase = mongoClient.getDB(databaseName);
			System.out.println("Connect to database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return mongoDatabase;
	}
	//关闭当前数据库连接
	public static void drop(){
		mongoClient.close();
	}
	// 查询表所有数据
	// 表名：TableName
	// 修改条件：query/query=null,查询所有
	public static DBCursor ConditionQuery(String TableName, BasicDBObject searchQuery) {
		// 获取数据库服务
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return null;
		}
		// 获取数据表服务
		DBCollection collection = mongoDatabase.getCollection(TableName);
		DBCursor cursor = collection.find(searchQuery);
		// 清空对象
		mongoDatabase = null;
		collection = null;
		// 返回数据集
		return cursor;
	}

	// 查询表所有数据
	// 表名：TableName
	public static DBCollection ConditionQuery(String TableName) {
		// 获取数据库服务
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return null;
		}
		// 获取数据表服务
		DBCollection collection = mongoDatabase.getCollection(TableName);
		// 清空对象
		mongoDatabase = null;
		// 返回数据集
		return collection;
	}

	// 插入数据集
	// 表名：TableName
	// 插入的数据实体：documents
	public static boolean Insert(String TableName, List<BasicDBObject> documents) {
		// 获取数据库服务
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return false;
		}
		// 获取数据表链接
		try {
			// 获取数据表服务
			DBCollection collection = mongoDatabase.getCollection(TableName);
			mongoDatabase = null;
			// 插入数据集
			collection.insert(documents);
			// 清空对象
			collection = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// 插入一条数据
	// 表名：TableName
	// 插入的数据实体：documents
	public static boolean Insert(String TableName, BasicDBObject documents) {
		// 获取数据库服务
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return false;
		}
		// 获取数据表链接
		try {
			// 获取数据表服务
			DBCollection collection = mongoDatabase.getCollection(TableName);
			mongoDatabase = null;
			// 插入数据集
			collection.insert(documents);
			// 清空对象
			collection = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// 修改一条数据
	// 表名：TableName
	// 修改条件：query
	// 修改数据为：newDocument
	public static boolean Update(String TableName, BasicDBObject query, BasicDBObject newDocument) {
		// 获取数据库服务
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return false;
		}
		// 获取数据表链接
		try {
			// 获取数据表服务
			DBCollection collection = mongoDatabase.getCollection(TableName);
			mongoDatabase = null;
			// 修改数据集
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);
			collection.update(query, updateObj);
			// 清空对象
			collection = null;
			query = null;
			newDocument = null;
			updateObj = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// 删除数据
	// 表名：TableName
	// 删除条件：query
	public static boolean Delete(String TableName, BasicDBObject query) {
		// 获取数据库服务
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return false;
		}
		// 获取数据表链接
		try {
			// 获取数据表服务
			DBCollection collection = mongoDatabase.getCollection(TableName);
			mongoDatabase = null;
			// 删除数据集
			collection.remove(query);
			// 清空对象
			collection = null;
			query = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
