package com.example.twitterclone.user;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
public class UserController {
    @Resource(name = "redisTemplate")
    private HashOperations hashOperations;

    @Resource(name = "redisTemplate")
    private ValueOperations valueOperations;

    Jackson2HashMapper mapper = new Jackson2HashMapper(false);

    @PostMapping("/api/user/create")
    public void createUser(@RequestBody UserInfo userInfo) {
        hashOperations.putAll("user:" + getUserId(), mapper.toHash(userInfo));
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
