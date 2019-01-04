package com.tcl.shbc.common.mongo;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.util.JSON;
import com.mongodb.ServerAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2015/4/26.
 */
@Component
public class MongoConn {
	private static Logger logger = LoggerFactory.getLogger(MongoConn.class);

	@Autowired
	private MongoConfig mongoConfig;
	
	private MongoClient mongoClient;
	/**
	 * 初始化
	 * 
	 * @throws UnknownHostException
	 */
	@PostConstruct
	public void init() throws UnknownHostException {
		// 构建Seed列表
		ServerAddress seed1 = new ServerAddress(mongoConfig.host,mongoConfig.port);
		List<ServerAddress> seedList = new ArrayList<ServerAddress>();
		seedList.add(seed1);
		
		// 构建鉴权信息
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(MongoCredential.createScramSha1Credential(
				mongoConfig.username, mongoConfig.defaultDatebase,
				mongoConfig.password.toCharArray()));
		
		// 构建操作选项
		MongoClientOptions options = new MongoClientOptions.Builder()
							.socketTimeout(mongoConfig.socketTimeOut)
							.serverSelectionTimeout(1000)
							.connectionsPerHost(mongoConfig.connectionsPerHost)
							.threadsAllowedToBlockForConnectionMultiplier(
									mongoConfig.threadsAllowedToBlockForConnectionMultiplier)
							.socketKeepAlive(true)
							.writeConcern(WriteConcern.NORMAL).build();
		
		mongoClient = new MongoClient(seedList,credentials,options);
		//authDb(mongoConfig.defaultDatebase);
	}

	/**
	 * 获得db
	 * 
	 * @param database
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private DB getDb(String database) {
		/*if (database == null)*/
			database = mongoConfig.defaultDatebase;
		DB db = mongoClient.getDB(database);

		return db;
	}

	/*@SuppressWarnings("deprecation")
	private void authDb(String database) {

		try {
			mongoClient.getDB(database).command("ping");
		} catch (Exception e) {
			throw new MongoException("User Login Failed");
		}

	}*/

	public void save(String database, String collection, DBObject dbObject) {
		if (logger.isDebugEnabled()) {
			logger.debug("save into database [" + database + "] collection ["
					+ collection + "] object [" + dbObject.toString() + "]");
		}
		getDb(database).getCollection(collection).save(dbObject);
	}

	public void delete(String database, String collection, DBObject query) {
		if (logger.isDebugEnabled()) {
			logger.debug("delete database [" + database + "] collection ["
					+ collection + "] query [" + query.toString() + "]");
		}
		getDb(database).getCollection(collection).remove(query);
	}

	// 此处不使用toArray()方法直接转换为List,是因为toArray()会把结果集直接存放在内存中，
	// 如果查询的结果集很大，并且在查询过程中某一条记录被修改了，就不能够反应到结果集中，从而造成"不可重复读"
	// 而游标是惰性获取数据
	public List<DBObject> find(String database, String collection,
			DBObject query, DBObject fields, int limit) {
		if (logger.isDebugEnabled()) {
			logger.debug("find database [" + database + "] collection ["
					+ collection + "] query [" + query.toString()
					+ "] fields [" + fields.toString() + "] limit [" + limit
					+ "]");
		}

		List<DBObject> list = new LinkedList<DBObject>();
		Cursor cursor = getDb(database).getCollection(collection)
				.find(query, fields).limit(limit);
		while (cursor.hasNext()) {
			list.add(cursor.next());
		}
		return list.size() > 0 ? list : null;
	}

	public List<DBObject> find(String database, String collection,
			DBObject query) {
		if (logger.isDebugEnabled()) {
			logger.debug("find database [" + database + "] collection ["
					+ collection + "] query [" + query.toString() + "]");
		}

		List<DBObject> list = new LinkedList<DBObject>();
		Cursor cursor = getDb(database).getCollection(collection).find(query);
		while (cursor.hasNext()) {
			list.add(cursor.next());
		}
		return list.size() > 0 ? list : null;
	}

	public List<DBObject> find(String database, String collection,
			DBObject query, DBObject fields, DBObject orderBy, int pageNum,
			int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("find database [" + database + "] collection ["
					+ collection + "] query [" + query.toString()
					+ "] fields [" + fields.toString() + "] orderBy ["
					+ orderBy.toString() + "] pageNum [" + pageNum
					+ "] pageSize [" + pageSize + "]");
		}

		List<DBObject> list = new ArrayList<DBObject>();
		Cursor cursor = getDb(database).getCollection(collection)
				.find(query, fields).skip((pageNum - 1) * pageSize)
				.limit(pageSize).sort(orderBy);
		while (cursor.hasNext()) {
			list.add(cursor.next());
		}
		return list.size() > 0 ? list : null;
	}

	public DBObject findOne(String database, String collection, DBObject query,
			DBObject fields) {
		if (logger.isDebugEnabled()) {
			logger.debug("findOne database [" + database + "] collection ["
					+ collection + "] query [" + query.toString()
					+ "] fields [" + fields.toString() + "]");
		}
		return getDb(database).getCollection(collection).findOne(query, fields);
	}

	/**
	 * @param database
	 * @param collection
	 * @param query
	 * @param update
	 * @param upsert
	 *            若所更新的数据没有，则插入
	 * @param multi
	 *            同时更新多个符合条件的文档(collection)
	 */
	public void update(String database, String collection, DBObject query,
			DBObject update, boolean upsert, boolean multi) {
		if (logger.isDebugEnabled()) {
			logger.debug("update database [" + database + "] collection ["
					+ collection + "] query [" + query.toString()
					+ "] fields [" + update.toString() + "] upsert [" + upsert
					+ "] multi [" + multi + "]");
		}
		getDb(database).getCollection(collection).update(query, update, upsert,
				multi);
	}

	public long count(String database, String collection, DBObject query) {
		if (logger.isDebugEnabled()) {
			logger.debug("count database [" + database + "] collection ["
					+ collection + "] query [" + query.toString() + "]");
		}

		return getDb(database).getCollection(collection).count(query);
	}

	// 查询出key字段,去除重复，返回值是{_id:value}形式的list
	@SuppressWarnings("rawtypes")
	public List distinct(String database, String collection, String key,
			DBObject query) {
		if (logger.isDebugEnabled()) {
			logger.debug("distinct database [" + database + "] collection ["
					+ collection + "] key [" + key + "] query ["
					+ query.toString() + "]");
		}

		return getDb(database).getCollection(collection).distinct(key, query);
	}

	// 适用于小数据量查询
	public void distinctWithHandle(String database, String collection,
			String key, DBObject query, CursorHandle cursorHandle) {
		if (logger.isDebugEnabled()) {
			logger.debug("distinctWithHandle database [" + database
					+ "] collection [" + collection + "] key [" + key
					+ "] query [" + query.toString() + "]");
		}

		List<DBObject> pipeLine = new ArrayList<DBObject>();
		pipeLine.add(new BasicDBObject("$match", query));
		String groupStr = String.format("{$group:{_id:'$%s'}}", key);
		pipeLine.add((DBObject) JSON.parse(groupStr));
		Cursor cursor = getDb(database).getCollection(collection).aggregate(
				pipeLine, AggregationOptions.builder().build());
		cursorHandle.handle(cursor);
	}

	// 适用于大数据量查询
	public void distinctWithHandle(String database, String collection,
			String key, DBObject query, int pageNo, int pageSize,
			CursorHandle cursorHandle) {
		if (logger.isDebugEnabled()) {
			logger.debug("distinctWithHandle database [" + database
					+ "] collection [" + collection + "] key [" + key
					+ "] query [" + query.toString() + "] pageNo [" + pageNo
					+ "] pageSize [" + pageSize + "]");
		}

		List<DBObject> pipeLine = new ArrayList<DBObject>();
		pipeLine.add(new BasicDBObject("$match", query));
		String groupStr = String.format("{$group:{_id:'$%s'}}", key);
		pipeLine.add((DBObject) JSON.parse(groupStr));
		pipeLine.add(new BasicDBObject("$skip", (pageNo - 1) * pageSize));
		pipeLine.add(new BasicDBObject("$limit", pageSize));
		Cursor cursor = getDb(database).getCollection(collection).aggregate(
				pipeLine, AggregationOptions.builder().build());
		cursorHandle.handle(cursor);
	}
}
