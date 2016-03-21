package com.ai.runner.center.dshm.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ai.runner.center.dshm.constants.BillingConstants;
import com.ai.runner.center.dshm.dao.interfaces.IDao;
import com.ai.runner.center.dshm.dto.EbillingShmTableDb;
import com.ai.runner.center.dshm.dto.EbillingShmTableInfo;
import com.ai.runner.center.dshm.dto.EbillingShmTableRecord;
import com.ai.runner.center.dshm.dto.ShmTableLoad;
import com.ai.runner.center.dshm.service.interfaces.ITransportService;
import com.ai.runner.center.dshm.util.ApplicationContextUtil;
import com.ai.runner.center.dshm.util.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Repository("dataBaseDao")
public class DataBaseDao implements IDao{
	@Autowired
	private ITransportService transportService;
	private JdbcTemplate jdbcTemplate;
	private Log logger = Logger.getLogger(DataBaseDao.class);
	ApplicationContextUtil appContextUtil = ApplicationContextUtil.getInstance();
	public DataBaseDao() {
//		ApplicationContextUtil appContextUtil = ApplicationContextUtil.getInstance();
//		System.out.println("the databasedao init2222222.......");
//		jdbcTemplate =(JdbcTemplate) appContextUtil.getBean("mysqlJdbcTemplate");
//		System.out.println("the databasedao init33333333.......");
//		if(jdbcTemplate==null){
//			System.out.println("the jdbcTemplate is null");
//		}
	}
	@Override
	public Map<String, ShmTableLoad> LoadShmTableInfo() {
		//将info、record表加载进缓存
		jdbcTemplate =(JdbcTemplate) appContextUtil.getBean("mysqlJdbcTemplate");
		String tableInfoSql=" select * from ebilling_shm_table_info ";
		List<EbillingShmTableInfo> tableInfos=jdbcTemplate.query(tableInfoSql, new BeanPropertyRowMapper<EbillingShmTableInfo>(EbillingShmTableInfo.class));
		String tableRecordSql=" select * from ebilling_shm_table_record where table_id=? ";
		Map<String, ShmTableLoad> tableLoad= new HashMap<String, ShmTableLoad>();
		for(EbillingShmTableInfo tableInfo:tableInfos){
			StringBuilder loadKey= new StringBuilder();
			StringBuilder loadRecord= new StringBuilder();
			StringBuilder loadPrimary= new StringBuilder();
			loadKey.append(tableInfo.getTableName().toLowerCase()).append(BillingConstants.KEY_JOINER).append(tableInfo.getTableId());
			System.out.println("the loadKey is "+loadKey.toString());
			ShmTableLoad shmTable= new ShmTableLoad();
			shmTable.setDbConnect(tableInfo.getDbConnect());
			shmTable.setIndexKey(tableInfo.getIndexKey().toLowerCase());
			shmTable.setTenantId(tableInfo.getTenantId());
			Object[] params = new Object[1];
			params[0]=tableInfo.getTableId();
			Gson gson = new Gson();
			List<EbillingShmTableRecord> tableRecords=jdbcTemplate.query(tableRecordSql, params, new BeanPropertyRowMapper<EbillingShmTableRecord>(EbillingShmTableRecord.class));
			for(EbillingShmTableRecord tableRecord: tableRecords){
				System.out.println("the tablerecord is "+gson.toJson(tableRecord));
				//record 的存储形式为 subs_id:cust_id:active_time
				loadRecord.append(BillingConstants.KEY_JOINER).append(tableRecord.getFieldName().toLowerCase());
				if(tableRecord.getIsPrimary()==0){
				 	//0 代表该字段是用来判重的
					loadPrimary.append(BillingConstants.KEY_JOINER).append(tableRecord.getFieldName().toLowerCase());
				}			
			}
			shmTable.setRecord(loadRecord.substring(1));
			//存在不含有primary的情况
			if(loadPrimary.toString().length()!=0)
				shmTable.setPrimaryKey(loadPrimary.substring(1));
			else {
				shmTable.setPrimaryKey("NULL");
			}
			tableLoad.put(loadKey.toString(),shmTable);
		}
		return tableLoad;
	}

	@Override
	public Map<String, EbillingShmTableDb> LoadShmTableDb() {
		//加载db表进缓存
		jdbcTemplate =(JdbcTemplate) appContextUtil.getBean("mysqlJdbcTemplate");
		String tableDbSql=" select * from ebilling_shm_table_db ";
		List<EbillingShmTableDb> tableDbs=jdbcTemplate.query(tableDbSql, new BeanPropertyRowMapper<EbillingShmTableDb>(EbillingShmTableDb.class));
		Map<String, EbillingShmTableDb> tableDbMap = new HashMap<String,EbillingShmTableDb>();
		for(EbillingShmTableDb tableDb:tableDbs){
			StringBuilder dbKey= new StringBuilder();
			//dbKey.append(tableDb.getDbConnect()).append(BillingConstants.KEY_JOINER);//-- 此处是否需要加上tenant_id
			dbKey.append(tableDb.getDbConnect());
			tableDbMap.put(dbKey.toString(),tableDb);	
		}
		return tableDbMap;
	}

	
	public static void main(String[] args){
		String a="123,234,287,";
		String[] abs=StringUtils.splitPreserveAllTokens(a, ",");
		System.out.println("the number of the queue is "+abs.length);
		for(String b:abs){
			if("".equals(b))
				b="NULL";
			System.out.println(b);
		}
	}
	@Override
	public Map<String,String> LoadTableData(String table_name,ShmTableLoad shmtable,String firstKey) { // 这里好像返回值的类型有问题哦
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		HashMap<String,String> recordValue=new HashMap();
		StringBuilder index_key= new StringBuilder();
		index_key.append(shmtable.getIndexKey());
		//开始进行select语句的拼接
		String [] indexs=StringUtils.splitPreserveAllTokens(index_key.toString(), BillingConstants.KEY_JOINER);
		StringBuilder selectSql= new StringBuilder();
		String[] records=StringUtils.splitPreserveAllTokens(shmtable.getRecord(), BillingConstants.KEY_JOINER);
		selectSql.append(" select GROUP_CONCAT( ");
		int flag=0;
		for(String record:records){
			if(flag==0)
				selectSql.append("IFNULL(").append(record).append(",'')");
			else 
				selectSql.append(BillingConstants.CONCANT).append("IFNULL(").append(record).append(",'')");
			flag++;
		}
		selectSql.append(" SEPARATOR '%') as a from ").append(table_name).append(" group by ");
		int indexFlag=0;
		for(String index:indexs){
			if(indexFlag==0)
			 selectSql.append(index);
			else
				selectSql.append(BillingConstants.VALUE_JOINER).append(index);
			indexFlag++;
		}
		//开始执行sql语句，需要确定连接源
		StringBuilder sqlDb=new StringBuilder();
		//sqlDb.append(transportService.getDbInfo(shmtable.getDbConnect(), shmtable.getTenantId()));
		sqlDb.append(transportService.getDbInfo(shmtable.getDbConnect()));
		System.out.println("the sqldb is "+sqlDb.toString());
		Connection conn=dbConnect(sqlDb.toString());
		try{
			System.out.println("the sql is "+selectSql.toString());
			pstmt = conn.prepareStatement(selectSql.toString());
			rs = pstmt.executeQuery();	
			if(rs.next()){					
				do{
					String[] dataRecords=StringUtils.splitPreserveAllTokens(rs.getString(1), "%");
					int sameFlag=0;
					StringBuilder value =new StringBuilder();
					StringBuilder index_second= new StringBuilder();
					index_second.append(shmtable.getIndexKey());
					//开始进行多条数据的拼接
					for(String dataRecord:dataRecords){
						HashMap<String,String> recordmap=new HashMap();				
						String[] dataSplit=StringUtils.splitPreserveAllTokens(dataRecord, ",");
						int datalength=dataSplit.length;
						int recordlegth=records.length;
						if(recordlegth!=datalength){
							Logger.log.error("the select num is not equal to records num ........");
							break;
						}
						else{
							for(int i=0;i<datalength;i++){
								if("".equals(dataSplit[i]))
									dataSplit[i]="NULL";
								recordmap.put(records[i], dataSplit[i]);
							}
							if(sameFlag==0){ //表示是第一条记录，需要拼接index-key
								for(String index:indexs)
									index_second.append(BillingConstants.KEY_JOINER).append(recordmap.get(index));	
								//再需要将map形式变成json的形式
								JSONObject jsonObject = JSONObject.fromObject(recordmap);
								value.append(jsonObject.toString());
							}else{
								JSONObject jsonObject = JSONObject.fromObject(recordmap);
								value.append("$").append(jsonObject.toString());
							}
							sameFlag++;
						}
							
					}
					System.out.println("the firstKey is "+firstKey+"  the index_second is "+index_second.toString());
					System.out.println("the value is "+value.toString());
					transportService.Data2Dso(firstKey, index_second.toString(), value.toString());
					//recordValue.put(index_key.toString(), value.toString());
				}while(rs.next());
			}
			conn.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.log.error("context", e);
		}	
		return recordValue;
	}
	
	private Connection dbConnect(String sqlDb){
		JSONObject jsonObject= JSONObject.fromObject((Object)sqlDb.toString());
		EbillingShmTableDb dbinfo=(EbillingShmTableDb) jsonObject.toBean(jsonObject, EbillingShmTableDb.class);
		//Connection connection = null;
		Connection connection=null;
		try {
			System.out.println("DataBaseUtil.getConnection()" + dbinfo.getUrl() + ":" + dbinfo.getUserName() + ":" + dbinfo.getPassWord());
			connection = DriverManager.getConnection(dbinfo.getUrl(), dbinfo.getUserName(), dbinfo.getPassWord());
		} catch (SQLException e) {
			Logger.log.debug("DataBaseUtil.getConnection()" + dbinfo.getUrl() + ":" + dbinfo.getUserName() + ":" + dbinfo.getPassWord());
			throw new RuntimeException("context", e);
		}
		return connection;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
