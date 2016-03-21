package com.ai.runner.center.dshm.api.dshmprocess.params;

import com.ai.runner.base.vo.BaseInfo;

/**
 * 请求参数
 * @author biancx
 *
 */
public class ReqParam extends BaseInfo{


	private static final long serialVersionUID = -5452699762452333281L;
	private String[] tableNames;
	private String[] tableId;
	private String[] tenantIds;
	
	public String[] getTenantIds() {
		return tenantIds;
	}
	public void setTenantIds(String[] tenantIds) {
		this.tenantIds = tenantIds;
	}
	public String[] getTableNames() {
		return tableNames;
	}
	public void setTableNames(String[] tableNames) {
		this.tableNames = tableNames;
	}
	public String[] getTableId() {
		return tableId;
	}
	public void setTableId(String[] tableId) {
		this.tableId = tableId;
	}
	

}
