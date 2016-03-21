package com.ai.runner.center.dshm.dto;

public class EbillingShmTableRecord {
    private Integer id;

    private Integer tableId;

    private String tableName;

    private Integer fieldSeq;

    private String fieldName;

    private Integer fieldType;

    private String fieldFormat;

    private Integer fieldSize;

    private Integer isHashkey;

    private Integer isPrimary;

    private String searchType;

    private String tenantId;

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public Integer getFieldSeq() {
        return fieldSeq;
    }

    public void setFieldSeq(Integer fieldSeq) {
        this.fieldSeq = fieldSeq;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldFormat() {
        return fieldFormat;
    }

    public void setFieldFormat(String fieldFormat) {
        this.fieldFormat = fieldFormat == null ? null : fieldFormat.trim();
    }

    public Integer getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(Integer fieldSize) {
        this.fieldSize = fieldSize;
    }

    public Integer getIsHashkey() {
        return isHashkey;
    }

    public void setIsHashkey(Integer isHashkey) {
        this.isHashkey = isHashkey;
    }

    public Integer getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Integer isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType == null ? null : searchType.trim();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }
}