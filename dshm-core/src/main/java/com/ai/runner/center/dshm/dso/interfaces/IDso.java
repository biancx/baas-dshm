package com.ai.runner.center.dshm.dso.interfaces;

import java.util.Map;

public interface IDso {
	//通过接口来分别对接mcs和redis；
	/**
	 * 想内存中存储map型的数值
	 * @param key  一级key
	 * @param field   二级key
	 * @param value   用于存储的值
	 */
	public void hset(String key,String field,String value);
	/**
	 * 获取通过hset存储的数值
	 * @param key  一级key
	 * @param field  二级key
	 * @return value值
	 */
	public String hget(String key,String field);
	/**
	 * 判断在缓存中是否存在
	 * @param key
	 * @param field
	 * @return 存在返回true
	 */
	public Boolean hexists(String key,String field);
	/**
	 * 删除在key和field在缓存中的存储
	 * @param key
	 * @param field
	 * @return
	 */
	public Long hdel(String key,String[] field);
	/**
	 * 在缓存中的map值
	 * @param key
	 * @return key下存储的map值
	 */
	public Map<String, String> getMap(String key);
	/**
	 * 判断key关键字在缓存中时候存在
	 * @param key
	 * @return 存在true
	 */
	public boolean isExist(String key);
	/**
	 * 向list的尾部添加元素
	 * @param key
	 * @param item
	 */
	public void addItem2ListTail(String key,String item);
	/**
	 * 向list的头部添加元素
	 * @param key
	 * @param item
	 */
	public void addItem2ListHead(String key,String item);
	/**
	 * 删除可以的键值对
	 * @param key
	 */
	public Long del(String key);
	/**
	 * 插入string类型
	 * @param key
	 * @param value
	 */
	public void set(String key,String value);
	/**
	 * 获取key对应的值
	 * @param key
	 * @return
	 */
	public String get(String key);
	
	
}
