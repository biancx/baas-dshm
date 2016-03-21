package com.ai.runner.center.dshm.service.interfaces;

import java.util.Map;
import java.util.Map.Entry;

public interface ITransportService {
	//用来定义操作缓存的方法；
	/**
	 * 用于将info表，db表和record表的基本信息加载进缓存
	 */
	public Boolean EbillingTable2Dso();
	/**
	 * 用于将info表中的数据刷入到缓存中
	 * @param table_name
	 * @param tenant_id
	 */
	public void LoadData2Dso(String table_name,String infoValue,String tableId);
	/**
	 * 用于获取db数据库的连接信息
	 * @param 数据库的名字
	 * @param 数据库所属的租户id
	 * @return 数据库的基本信息
	 */
	//public String getDbInfo(String dbName,String tenant_id);
	public String getDbInfo(String dbName);
	/**
	 * 用于实时向缓存中刷入采集中心传递的数据
	 * @param 需要刷新的表名
	 * @param 需要向表中刷新的数据
	 * @return string[0] table_name:table_id, string[1] index_key:value, string[2] value值 
	 */
	public Boolean initCtp2Dso(String recordValue,String tableId,Map<String, String> dataMap,String tableName,int type);
	/**
	 * 用于加载表的info信息
	 * @param 表名，租户id
	 * @return Map<String, String> info表的信息
	 */
	public Map<String, String> LoadTableInfo(String tableName,String tenantId);
	/**
	 * 用于删除特定表在内存中的存储
	 * @param 表名，租户id
	 * @return Boolean 表示是否删除成功
	 */
	public Long DeleteTableMem(String tableName,String tableId);
	/**
	 * 将json串转换成map的形式
	 * @param json
	 * @return
	 */
	public  Map<String, String> json2Map(String json);
	/**
	 * 用于直接将数据加载进缓存
	 */
	public void Data2Dso(String firstKey,String fieldKey,String value);
}
