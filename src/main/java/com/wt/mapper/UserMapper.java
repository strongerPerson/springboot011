package com.wt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wt.cache.RedisCache;
import com.wt.pojo.User;
import org.apache.ibatis.annotations.CacheNamespace;

@CacheNamespace(implementation= RedisCache.class,eviction=RedisCache.class)
public interface UserMapper extends BaseMapper<User> {
}
