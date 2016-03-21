package com.ai.runner.center.dshm.dso.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ai.runner.center.dshm.constants.CacheBLMapper;
import com.ai.runner.center.dshm.dso.interfaces.IDso;
import com.ai.runner.center.dshm.util.CacheFactoryUtil;
import com.ai.runner.sdk.cache.client.ICacheClient;

@Repository("cacheDso")
public class CacheDso implements IDso{
	private ICacheClient  cacheClient=null;
//	private ICacheClient  cacheClient =  CacheFactoryUtil
//            .getCacheClient(CacheBLMapper.CACHE_BL_CAL_PARAM);
	public CacheDso() throws Exception{
		cacheClient =  CacheFactoryUtil
                .getCacheClient(CacheBLMapper.CACHE_BL_CAL_PARAM);
	}
	@Override
	public void hset(String key, String field, String value) {
		this.cacheClient.hset(key, field, value);
	}
	@Override
	public String hget(String key, String field) {
		return this.cacheClient.hget(key, field);
	}
	@Override
	public Boolean hexists(String key, String field) {
		return this.cacheClient.hexists(key, field);
	}
	@Override
	public Long hdel(String key, String[] field) {
		return this.cacheClient.hdel(key, field);
	}
	@Override
	public Map<String, String> getMap(String key) {
		return this.cacheClient.hgetAll(key);
	}
	@Override
	public boolean isExist(String key) {
		return this.cacheClient.exists(key);
	}
	@Override
	public void addItem2ListTail(String key, String item) {
		
	}

	@Override
	public void addItem2ListHead(String key, String item) {
		
	}
	@Override
	public Long del(String key) {
		return this.cacheClient.del(key);
		
	}
	@Override
	public void set(String key, String value) {
		this.cacheClient.set(key, value);
	}
	@Override
	public String get(String key) {
		return this.cacheClient.get(key);
	}
}
