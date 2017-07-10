package com.vates.wifibus.backoffice.api.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Application configs
 * 
 * @author luis.stubbia
 *
 */
@Configuration
@EnableCaching
@PropertySource("classpath:app.properties")
public class ApplicationConfiguration {

	@Value("${redis.host}")
	private String redisHost;

	@Value("${redis.port}")
	private int redisPort;
	
	@Value("#{${config.api_keys}}")
	private Map<String,String> apiKeys;

	@Value("#{${config.api_properties.facebook}}")
	private Map<String,List<String>> propertiesMapper;

	public Map<String, String> getApiKeys() {
		return apiKeys;
	}

	public Map<String, List<String>> getPropertiesMapper() {
		return propertiesMapper;
	}
	
	// Redis cache configuration.
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory(){
		JedisConnectionFactory connFactory = new JedisConnectionFactory();
		connFactory.setHostName(redisHost);
		connFactory.setPort(redisPort);
		connFactory.setUsePool(true);
		return connFactory;
	}
	
	@Bean
	RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return redisTemplate;
	}
	
	@Bean
	CacheManager cacheManager(){
		RedisCacheManager cacheManager =  new RedisCacheManager(redisTemplate());
		cacheManager.setDefaultExpiration(0);
		return cacheManager;
	}
}
