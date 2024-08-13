package com.example.UserRepository;

import com.example.UserEntity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class UserCacheRepository {
    public static final String USER_CACHE_PREFIX = "::usr";

    public static final Integer USER_CACHE_EXPIRY = 600;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    public UserEntity get(Integer userId){
        Object result =
                redisTemplate.opsForValue().get(getKey(userId));
        if(result == null){
            return null;
        }
        return (UserEntity) result;
    }
    public void set(UserEntity user){
        redisTemplate.opsForValue().set(getKey(user.getId()),
                user,USER_CACHE_EXPIRY, TimeUnit.SECONDS);
    }

    private String getKey(Integer id){
        return USER_CACHE_PREFIX + id;
    }
}
