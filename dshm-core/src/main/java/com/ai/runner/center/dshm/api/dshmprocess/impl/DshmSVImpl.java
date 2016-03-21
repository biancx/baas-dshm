package com.ai.runner.center.dshm.api.dshmprocess.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ai.runner.base.exception.CallerException;
import com.ai.runner.center.dshm.api.dshmprocess.interfaces.IdshmSV;
import com.ai.runner.center.dshm.api.dshmprocess.params.ReqParam;
import com.ai.runner.center.dshm.dto.FullTableLoad;
import com.ai.runner.center.dshm.service.interfaces.ITransportService;
import com.ai.runner.center.dshm.util.ApplicationContextUtil;
import com.ai.runner.center.dshm.util.Logger;
import com.alibaba.dubbo.config.annotation.Service;


public class DshmSVImpl implements IdshmSV{
	static private Log logger = Logger.getLogger(DshmSVImpl.class);
	//注入服务对象
	@Autowired
	private ITransportService transportService;
	private JdbcTemplate jdbcTemplate;
	ApplicationContextUtil appContextUtil = ApplicationContextUtil.getInstance();
	public DshmSVImpl(){
//		ApplicationContextUtil appContextUtil = ApplicationContextUtil.getInstance();
//		jdbcTemplate =(JdbcTemplate) appContextUtil.getBean("mysqlJdbcTemplate");
	}
	@Override
	public int fullLoader() throws CallerException {
		//首先需要加载表结构进内存
		jdbcTemplate =(JdbcTemplate) appContextUtil.getBean("mysqlJdbcTemplate");
		Boolean tableFlag=transportService.EbillingTable2Dso();
		if(tableFlag){
			//将表结构加载完毕之后需要选出需要加载的表名和租户id
			String sqlString=" select distinct table_name,tenant_id from ebilling_shm_table_info ";
			List<FullTableLoad> nameIds=jdbcTemplate.query(sqlString, new BeanPropertyRowMapper<FullTableLoad>(FullTableLoad.class));
			for(FullTableLoad nameid:nameIds){
				Map<String, String> infos=transportService.LoadTableInfo(nameid.getTableName().toLowerCase(), nameid.getTenantId());
				for(Entry<String,String> infoentry:infos.entrySet()){
					String infoValue=infoentry.getValue();
					String tableId=infoentry.getKey();
					transportService.LoadData2Dso(nameid.getTableName().toLowerCase(), infoValue,tableId);
				}
			}
			return 1;
		}
		else{
			logger.error("load table info is error.......");
			return -1;
		}
	}

	@Override
	public int refreshLoader(ReqParam req) throws CallerException {
		String[] tableName=req.getTableNames();
		String[] tableId=req.getTableId();
		String[] tenantId=req.getTenantIds();
		Boolean tableFlag=transportService.EbillingTable2Dso();
		if(tableFlag){
			if((tableName.length==tableId.length)&&(tableName.length==tenantId.length)){
				for(int i=0;i<tableName.length;i++){
					Map<String, String> infoMap=transportService.LoadTableInfo(tableName[i], tenantId[i]);
					String infoValue=infoMap.get(tableId[i]);
					transportService.LoadData2Dso(tableName[i], infoValue,tableId[i]);
				}
				return 1;		
			}
			else{
				logger.error("the param is error please check......");
				return -1;
			}
		}else{
			logger.error("load table info is error.......");
			return -1;
		}	
	}

	@Override
	public int shmDelete(ReqParam req) throws CallerException {
		// 应该先删除然后在加载表结构到内存中
		String[] tableName=req.getTableNames();
		String[] tableId=req.getTableId();
		String[] tenantId=req.getTenantIds();
		if((tableName.length==tableId.length)&&(tableName.length==tenantId.length)){
			for(int i=0;i<tableName.length;i++){
				int j =transportService.DeleteTableMem(tableName[i], tableId[i]).intValue();
			}
			return 1;
		}else{
			logger.error("the param is error please check......");
			return -1;
		}
	}

	@Override
	public int initLoader(String tableName, Object obj,int type) throws CallerException {
		Map<String, String> initMap=new HashMap<String, String>();
		initMap=transportService.json2Map(obj.toString().toLowerCase());
		String tenantId=initMap.get("tenant_id");
		Map<String, String> infoMaps=transportService.LoadTableInfo(tableName,tenantId);
		int flag=0;
		for(Entry<String,String> infoentry:infoMaps.entrySet()){
			String infoValue=infoentry.getValue();
			String tableId=infoentry.getKey(); 
			Boolean suc=transportService.initCtp2Dso(infoValue, tableId,initMap,tableName,type);
			if(!suc){
				flag=-1;
				logger.error("the tableId="+tableId+" initLoader is error.........");
			}
		}
		if(flag==-1)
			return -1;
		else {
			return 1;
		}
	}
	@Override
	public int initdel(String tableName, Object obj) throws CallerException {
		//默认实时删除是的type=2；
		Map<String, String> initMap=new HashMap<String, String>();
		initMap=transportService.json2Map(obj.toString().toLowerCase());
		String tenantId=initMap.get("tenant_id");
		Map<String, String> infoMaps=transportService.LoadTableInfo(tableName,tenantId);
		int flag=0;
		for(Entry<String,String> infoentry:infoMaps.entrySet()){
			String infoValue=infoentry.getValue();
			String tableId=infoentry.getKey(); 
			Boolean suc=transportService.initCtp2Dso(infoValue, tableId,initMap,tableName,2);
			if(!suc){
				flag=-1;
				logger.error("the tableId="+tableId+" initDel is error.........");
			}
		}
		if(flag==-1)
			return -1;
		else {
			return 1;
		}
	}
	public static void main(String[] args){
//		logger.debug("start fuller load ......");
//		System.out.println("start fuller load ......");
//		ApplicationContextUtil appContextUtil = ApplicationContextUtil.getInstance();
//		DshmSVImpl dshmsv = (DshmSVImpl)appContextUtil.getBean("dshmsv");
//		dshmsv.fullLoader();
	}
	
}
