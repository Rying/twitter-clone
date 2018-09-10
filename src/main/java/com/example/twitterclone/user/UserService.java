package com.example.twitterclone.user;

import com.example.twitterclone.exception.BusinessException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserService {
    @Resource(name = "redisTemplate")
    private HashOperations hashOperations;

    @Resource(name = "redisTemplate")
    private ValueOperations valueOperations;

    public void create(UserInfo userInfo) {
        Integer userId = getUserId();
        Jackson2HashMapper mapper = new Jackson2HashMapper(false);
        if (Optional.ofNullable(hashOperations.get("users", userInfo.getName())).isPresent()) {
            throw new BusinessException("该用户已经注册过了");
        }
        hashOperations.putAll("user:" + userId, mapper.toHash(userInfo));
        hashOperations.put("users", userInfo.getName(), userId.toString());
    }

    private Integer getUserId() {
        Optional<Object> next_user_id = Optional.ofNullable(valueOperations.get("next_user_id"));
        if(next_user_id.isPresent()) {
            valueOperations.increment("next_user_id", 1);
        } else {
            valueOperations.set("next_user_id", "0");
        }
        return Integer.valueOf((String) valueOperations.get("next_user_id"));
    }
}
