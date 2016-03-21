package com.ai.runner.center.dshm.dto;

public class EbillingShmTableInfo {
    private Integer id;

    private Integer tableId;

    private Integer isEnable;

    private String indexKey;

    private String tableName;

    private Integer indexMode;

    private Integer storageType;

    private Long indexCount;

    private Long dataCount;

    private Integer isPara;

    private Integer indexId;

    private Integer hashType;

    private String lesseeName;

    private String dbConnect;

    private String tenantId;

    private String tableKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getIndexKey() {
        return indexKey;
    }

    public void setIndexKey(String indexKey) {
        this.indexKey = indexKey == null ? null : indexKey.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public Integer getIndexMode() {
        return indexMode;
    }

    public void setIndexMode(Integer indexMode) {
        this.indexMode = indexMode;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public Long getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(Long indexCount) {
        this.indexCount = indexCount;
    }

    public Long getDataCount() {
        return dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }

    public Integer getIsPara() {
        return isPara;
    }

    public void setIsPara(Integer isPara) {
        this.isPara = isPara;
    }

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public Integer getHashType() {
        return hashType;
    }

    public void setHashType(Integer hashType) {
        this.hashType = hashType;
    }

    public String getLesseeName() {
        return lesseeName;
    }

    public void setLesseeName(String lesseeName) {
        this.lesseeName = lesseeName == null ? null : lesseeName.trim();
    }

    public String getDbConnect() {
        return dbConnect;
    }

    public void setDbConnect(String dbConnect) {
        this.dbConnect = dbConnect == null ? null : dbConnect.trim();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public String getTableKey() {
        return tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey == null ? null : tableKey.trim();
    }
}