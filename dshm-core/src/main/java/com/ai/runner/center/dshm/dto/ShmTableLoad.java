package com.ai.runner.center.dshm.dto;

public class ShmTableLoad {
	private String indexKey;
	private String record;
	//private String tableId;
	private String dbConnect;
	private String primaryKey;
	private String tenantId;
	
	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getDbConnect() {
		return dbConnect;
	}
	public void setDbConnect(String dbConnect) {
		this.dbConnect = dbConnect;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getIndexKey() {
		return indexKey;
	}
	public void setIndexKey(String indexKey) {
		this.indexKey = indexKey;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
//	public String getTableId() {
//		return tableId;
//	}
//	public void setTableId(String tableId) {
//		this.tableId = tableId;
//	}
	
}
