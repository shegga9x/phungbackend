package com.example.backend.thirtParty.RedisConfig;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/redis")
public class RedisTestController {

    private final StringRedisTemplate redisTemplate;

    public RedisTestController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/set")
    public String setValue() {
        redisTemplate.opsForValue().set("testKey", "Hello from Spring Boot!");
        return "Value set in Redis!";
    }

    @GetMapping("/get")
    public String getValue() {
        return "Redis value: " + redisTemplate.opsForValue().get("testKey");
    }
}
