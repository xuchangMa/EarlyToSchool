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

	// ��ȡ���ݿ����
	static DB GetDataBaseServer() {
		DB mongoDatabase = null;
		try {
			// ���ӵ� mongodb ����
			//MongoClient mongoClient = new MongoClient(host, port);
			mongoClient = new MongoClient(host, port);
			// ���ӵ����ݿ�
			
			mongoDatabase = mongoClient.getDB(databaseName);
			//mongoDatabase = mongoClient.getDB(databaseName);
			System.out.println("Connect to database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return mongoDatabase;
	}
	//�رյ�ǰ���ݿ�����
	public static void drop(){
		mongoClient.close();
	}
	// ��ѯ����������
	// ������TableName
	// �޸�������query/query=null,��ѯ����
	public static DBCursor ConditionQuery(String TableName, BasicDBObject searchQuery) {
		// ��ȡ���ݿ����
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return null;
		}
		// ��ȡ���ݱ����
		DBCollection collection = mongoDatabase.getCollection(TableName);
		DBCursor cursor = collection.find(searchQuery);
		// ��ն���
		mongoDatabase = null;
		collection = null;
		// �������ݼ�
		return cursor;
	}

	// ��ѯ����������
	// ������TableName
	public static DBCollection ConditionQuery(String TableName) {
		// ��ȡ���ݿ����
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return null;
		}
		// ��ȡ���ݱ����
		DBCollection collection = mongoDatabase.getCollection(TableName);
		// ��ն���
		mongoDatabase = null;
		// �������ݼ�
		return collection;
	}

	// �������ݼ�
	// ������TableName
	// ���������ʵ�壺documents
	public static boolean Insert(String TableName, List<BasicDBObject> documents) {
		// ��ȡ���ݿ����
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return false;
		}
		// ��ȡ���ݱ�����
		try {
			// ��ȡ���ݱ����
			DBCollection collection = mongoDatabase.getCollection(TableName);
			mongoDatabase = null;
			// �������ݼ�
			collection.insert(documents);
			// ��ն���
			collection = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// ����һ������
	// ������TableName
	// ���������ʵ�壺documents
	public static boolean Insert(String TableName, BasicDBObject documents) {
		// ��ȡ���ݿ����
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return false;
		}
		// ��ȡ���ݱ�����
		try {
			// ��ȡ���ݱ����
			DBCollection collection = mongoDatabase.getCollection(TableName);
			mongoDatabase = null;
			// �������ݼ�
			collection.insert(documents);
			// ��ն���
			collection = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// �޸�һ������
	// ������TableName
	// �޸�������query
	// �޸�����Ϊ��newDocument
	public static boolean Update(String TableName, BasicDBObject query, BasicDBObject newDocument) {
		// ��ȡ���ݿ����
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return false;
		}
		// ��ȡ���ݱ�����
		try {
			// ��ȡ���ݱ����
			DBCollection collection = mongoDatabase.getCollection(TableName);
			mongoDatabase = null;
			// �޸����ݼ�
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);
			collection.update(query, updateObj);
			// ��ն���
			collection = null;
			query = null;
			newDocument = null;
			updateObj = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// ɾ������
	// ������TableName
	// ɾ��������query
	public static boolean Delete(String TableName, BasicDBObject query) {
		// ��ȡ���ݿ����
		DB mongoDatabase = GetDataBaseServer();
		if (mongoDatabase == null) {
			return false;
		}
		// ��ȡ���ݱ�����
		try {
			// ��ȡ���ݱ����
			DBCollection collection = mongoDatabase.getCollection(TableName);
			mongoDatabase = null;
			// ɾ�����ݼ�
			collection.remove(query);
			// ��ն���
			collection = null;
			query = null;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
