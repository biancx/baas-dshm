package com.ai.runner.center.dshm.api.dshmprocess.interfaces;

import com.ai.runner.base.exception.CallerException;
import com.ai.runner.center.dshm.api.dshmprocess.params.ReqParam;

/**
 * 共享内存dubbo服务<br>
 * Date: 2015年9月14日 <br>
 * Copyright (c) 2015 asiainfo.com <br>
 * 
 * @author biancx
 */ 
public interface IdshmSV {
	 /**
     * 对ebilling_shm_table_info表全量加载
     * 
     * @return 加载成功标识符   1表示成功，-1 表示不成功
     * @throws CallerException
     * @author biancx 
     * @ApiCode DSHM-0001
     */ 
	public int fullLoader()throws CallerException;
	/**
	 * 根据前台请求将具体表刷进缓存
	 * @param req 请求参数
	 * @return 加载成功标识符   1表示成功，-1 表示不成功
     * @throws CallerException
     * @author biancx 
     * @ApiCode DSHM-0002
	 */
	public int refreshLoader(ReqParam req) throws CallerException;
	
	/**
	 * 释放具体表空间的缓存
	 * @param req 请求参数
	 * @return 加载成功标识符   1表示成功，-1 表示不成功
     * @throws CallerException
     * @author biancx 
     * @ApiCode DSHM-0003
	 */
	public int shmDelete(ReqParam req) throws CallerException;
	/**
	 * 提供给采集平台用于实时向缓存刷入数据
	 * @param table_name   表示实时加载数据的表名   obj 是实时加载的数据 ,type=1 表示insert，type=0表示更新（或者是新delete 再insert）
	 * @return 1 表示加载成功，-1  表示加载失败
	 * @throws CallerException
	 * @author biancx
	 * @ApiCode DSHM-0004
	 */
	public int initLoader(String table_name,Object obj,int type)throws CallerException;
	/**
	 * 提供给采集平台用于实时删除缓存中的数据
	 * @param table_name 实时删除的表名  obj 实时删除的数据
	 * @return 1 表示加载成功，-1  表示加载失败
	 * @throws CallerException
	 * @author biancx
	 * @ApiCode DSHM-0005
	 */
	public int initdel(String tableName,Object obj)throws CallerException;

}