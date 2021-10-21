package com.wt.cache;

import com.wt.utils.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 自定义redis缓存
 */
public class RedisCache implements Cache {

    private final  String id;

    public RedisCache(String id) {
        System.out.println("当前构造函数"+id);
        this.id = id;
    }

    //获取唯一标识的id
    @Override
    public String getId() {
        return id;
    }
    //放入缓存中方法
    @Override
    public void putObject(Object key, Object value) {
        System.out.println("key:"+key.toString());
        System.out.println("value+"+value);
        //使用redisHash类型做缓存存储
        getRedisTemplate().opsForHash().put(id.toString(),key.toString(),value);


    }
   //取出缓存中数据方法
    @Override
    public Object getObject(Object key) {
        System.out.println("key:"+key);
        //根据key从redisHash获取数据
        return getRedisTemplate().opsForHash().get(id.toString(),key.toString());

    }
     //删除指定的缓存
    @Override
    public Object removeObject(Object o) {
        return null;
    }
     //清空缓存，默认调用这个
    @Override
    public void clear() {
        System.out.println("清空缓存~~~");
        //清空缓存
       getRedisTemplate().delete(id.toString());

    }
//用来计算缓存数量
    @Override
    public int getSize() {
        //获取hash中key value的数量
        return getRedisTemplate().opsForHash().size(id.toString()).intValue();

    }
    //封装redisTemplate
    private RedisTemplate getRedisTemplate(){
        //通过工具类获取redisTemplate对象
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //根据key从redisHash获取数据
        return redisTemplate;
    }
}
