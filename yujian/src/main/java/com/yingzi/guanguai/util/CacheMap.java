package com.yingzi.guanguai.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheMap {

    private CacheMap (){

    }
    /**
     * 数据缓存map
     */
    private static Map<Long, Object> dataMap = new ConcurrentHashMap<Long, Object>();

    /**
     * 将一个key、value值放入内存缓存,并设置过期分钟数	 * 	 * @param key	 * @param val	 * @param expireMiute
     */
    public static void put(Long key, Object val) {
        dataMap.put(key, val);
    }

    /**
     * 从缓存中获取一个key的数据(若过期返回null)	 * 	 * @param key	 * @return
     */
    public static Object get(Long cacheKey) {
        return dataMap.get(cacheKey);
    }
    public static void del(Long key) {
        dataMap.remove(key);
    }
}
