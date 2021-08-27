package com.example.demo.config;

import com.google.common.base.CharMatcher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author qingyun
 * @date 2020/6/9 2:03 下午
 */
@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ApplicationConfig {

  @Value("${spring.application.name}")
  private String applicationName;

  @Value("${spring.redis.build-name}")
  private boolean buildName;

  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private Integer redisPort;

  @Value("${spring.redis.password}")
  private String redisPwd;

  @Value("${spring.redis.timeout}")
  private Integer redisTimeout;

  @Value("${spring.redis.count}")
  private Integer redisCount;



  public String rebuildAppName() {
    String applicationName = this.applicationName;
    CharMatcher charMatcher = CharMatcher.anyOf("-");
    return charMatcher.replaceFrom(applicationName, "_");
  }
}
