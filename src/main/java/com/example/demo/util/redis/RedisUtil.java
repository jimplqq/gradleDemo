package com.example.demo.util.redis;

import com.example.demo.config.ApplicationConfig;
import com.example.demo.enums.RedisStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author qingyun
 * @date 2020/6/5 4:00 下午
 */
@Component
@Configuration
public class RedisUtil {

  @Resource private ApplicationConfig applicationConfig;

  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  private String buildKey(String key) {
    return applicationConfig.isBuildName() ? (applicationConfig.rebuildAppName() + ":" + key) : key;
  }

  /**
   * 置过期时间，就返回-1;
   * 没有该值，就返回-2;
   * @param key
   * @return
   */
  public long ttl(String key) {
    return redisTemplate.opsForValue().getOperations().getExpire(this.buildKey(key));
  }

  /**
   * 指定缓存失效时间
   *
   * @param key 键
   * @param time 时间(秒)
   * @return
   */
  public boolean expire(String key, long time) {
    try {
      if (time > 0) {
        redisTemplate.expire(this.buildKey(key), time, TimeUnit.SECONDS);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 根据key 获取过期时间
   *
   * @param key 键 不能为null
   * @return 时间(秒) 返回0代表为永久有效
   */
  public long getExpire(String key) {
    return redisTemplate.getExpire(this.buildKey(key), TimeUnit.SECONDS);
  }

  /**
   * 判断key是否存在
   *
   * @param key 键
   * @return true 存在 false不存在
   */
  public boolean hasKey(String key) {
    try {
      return redisTemplate.hasKey(this.buildKey(key));
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 删除缓存
   *
   * @param key 可以传一个值 或多个
   */
  @SuppressWarnings("unchecked")
  public void del(String... key) {
    if (key != null && key.length > 0) {
      if (key.length == 1) {
        redisTemplate.delete(this.buildKey(key[0]));
      } else {
        List list =
            (List)
                CollectionUtils.arrayToList(key).stream()
                    .map(k -> this.buildKey(String.valueOf(k)))
                    .collect(Collectors.toList());
        redisTemplate.delete(list);
      }
    }
  }

  // ============================String=============================

  /**
   * 普通缓存获取
   *
   * @param key 键
   * @return 值
   */
  public Object getWithoutApplicationName(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(key);
  }

  /**
   * 普通缓存获取
   *
   * @param key 键
   * @return 值
   */
  public Object getGlobal(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(GLOBAL_KEY + key);
  }

  /**
   * 普通缓存获取
   *
   * @param key 键
   * @return 值
   */
  public Object get(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(this.buildKey(key));
  }

  /**
   * 普通缓存放入
   *
   * @param key 键
   * @param value 值
   * @return true成功 false失败
   */
  public boolean setWithoutApplicationName(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public static final String GLOBAL_KEY = "global:";

  /**
   * 普通缓存放入
   *
   * @param key 键
   * @param value 值
   * @return true成功 false失败
   */
  public boolean setGlobal(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(GLOBAL_KEY + key, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 普通缓存放入
   *
   * @param key 键
   * @param value 值
   * @return true成功 false失败
   */
  public boolean set(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(this.buildKey(key), value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 普通缓存放入并设置时间
   *
   * @param key 键
   * @param value 值
   * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
   * @return true成功 false 失败
   */
  public boolean setGlobal(String key, Object value, long time) {
    try {
      if (time > 0) {
        redisTemplate.opsForValue().set(GLOBAL_KEY + key, value, time, TimeUnit.SECONDS);
      } else {
        set(GLOBAL_KEY + key, value);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 普通缓存放入并设置时间
   *
   * @param key 键
   * @param value 值
   * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
   * @return true成功 false 失败
   */
  public boolean set(String key, Object value, long time) {
    try {
      if (time > 0) {
        redisTemplate.opsForValue().set(this.buildKey(key), value, time, TimeUnit.SECONDS);
      } else {
        set(this.buildKey(key), value);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 递增
   *
   * @param key 键
   * @param delta 要增加几(大于0)
   * @return
   */
  public long incr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递增因子必须大于0");
    }
    return redisTemplate.opsForValue().increment(this.buildKey(key), delta);
  }

  /**
   * 递减
   *
   * @param key 键
   * @param delta 要减少几(小于0)
   * @return
   */
  public long decr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递减因子必须大于0");
    }
    return redisTemplate.opsForValue().increment(this.buildKey(key), -delta);
  }

  // ================================Map=================================

  /**
   * HashGet
   *
   * @param key 键 不能为null
   * @param item 项 不能为null
   * @return 值
   */
  public Object hget(String key, String item) {
    return redisTemplate.opsForHash().get(this.buildKey(key), item);
  }

  /**
   * 获取hashKey对应的所有键值
   *
   * @param key 键
   * @return 对应的多个键值
   */
  public Map<Object, Object> hmget(String key) {
    return redisTemplate.opsForHash().entries(this.buildKey(key));
  }

  /**
   * HashSet
   *
   * @param key 键
   * @param map 对应多个键值
   * @return true 成功 false 失败
   */
  public boolean hmset(String key, Map<String, Object> map) {
    try {
      redisTemplate.opsForHash().putAll(this.buildKey(key), map);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * HashSet 并设置时间
   *
   * @param key 键
   * @param map 对应多个键值
   * @param time 时间(秒)
   * @return true成功 false失败
   */
  public boolean hmset(String key, Map<String, Object> map, long time) {
    try {
      redisTemplate.opsForHash().putAll(this.buildKey(key), map);
      if (time > 0) {
        expire(this.buildKey(key), time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 向一张hash表中放入数据,如果不存在将创建
   *
   * @param key 键
   * @param item 项
   * @param value 值
   * @return true 成功 false失败
   */
  public boolean hset(String key, String item, Object value) {
    try {
      redisTemplate.opsForHash().put(this.buildKey(key), item, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 向一张hash表中放入数据,如果不存在将创建
   *
   * @param key 键
   * @param item 项
   * @param value 值
   * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
   * @return true 成功 false失败
   */
  public boolean hset(String key, String item, Object value, long time) {
    try {
      redisTemplate.opsForHash().put(this.buildKey(key), item, value);
      if (time > 0) {
        expire(this.buildKey(key), time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 删除hash表中的值
   *
   * @param key 键 不能为null
   * @param item 项 可以使多个 不能为null
   */
  public void hdel(String key, Object... item) {
    redisTemplate.opsForHash().delete(this.buildKey(key), item);
  }

  /**
   * 判断hash表中是否有该项的值
   *
   * @param key 键 不能为null
   * @param item 项 不能为null
   * @return true 存在 false不存在
   */
  public boolean hHasKey(String key, String item) {
    return redisTemplate.opsForHash().hasKey(this.buildKey(key), item);
  }

  /**
   * hash递增 如果不存在,就会创建一个 并把新增后的值返回
   *
   * @param key 键
   * @param item 项
   * @param by 要增加几(大于0)
   * @return
   */
  public double hincr(String key, String item, double by) {
    return redisTemplate.opsForHash().increment(this.buildKey(key), item, by);
  }

  /**
   * hash递减
   *
   * @param key 键
   * @param item 项
   * @param by 要减少记(小于0)
   * @return
   */
  public double hdecr(String key, String item, double by) {
    return redisTemplate.opsForHash().increment(this.buildKey(key), item, -by);
  }

  // ============================set=============================

  /**
   * 根据key获取Set中的所有值
   *
   * @param key 键
   * @return
   */
  public Set<Object> sGet(String key) {
    try {
      return redisTemplate.opsForSet().members(this.buildKey(key));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 根据value从一个set中查询,是否存在
   *
   * @param key 键
   * @param value 值
   * @return true 存在 false不存在
   */
  public boolean sHasKey(String key, Object value) {
    try {
      return redisTemplate.opsForSet().isMember(this.buildKey(key), value);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 将数据放入set缓存
   *
   * @param key 键
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public long sSet(String key, Object... values) {
    try {
      return redisTemplate.opsForSet().add(this.buildKey(key), values);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * 将set数据放入缓存
   *
   * @param key 键
   * @param time 时间(秒)
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public long sSetAndTime(String key, long time, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().add(this.buildKey(key), values);
      if (time > 0) {
        expire(key, time);
      }
      return count;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * 获取set缓存的长度
   *
   * @param key 键
   * @return
   */
  public long sGetSetSize(String key) {
    try {
      return redisTemplate.opsForSet().size(this.buildKey(key));
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * 移除值为value的
   *
   * @param key 键
   * @param values 值 可以是多个
   * @return 移除的个数
   */
  public long setRemove(String key, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().remove(this.buildKey(key), values);
      return count;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }
  // ===============================list=================================

  /**
   * 获取list缓存的内容
   *
   * @param key 键
   * @param start 开始
   * @param end 结束 0 到 -1代表所有值
   * @return
   */
  public List<Object> lGet(String key, long start, long end) {
    try {
      return redisTemplate.opsForList().range(this.buildKey(key), start, end);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 获取list缓存的长度
   *
   * @param key 键
   * @return
   */
  public long lGetListSize(String key) {
    try {
      return redisTemplate.opsForList().size(this.buildKey(key));
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * 通过索引 获取list中的值
   *
   * @param key 键
   * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
   * @return
   */
  public Object lGetIndex(String key, long index) {
    try {
      return redisTemplate.opsForList().index(this.buildKey(key), index);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key 键
   * @param value 值
   * @return
   */
  public boolean lSet(String key, Object value) {
    try {
      redisTemplate.opsForList().rightPush(this.buildKey(key), value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key 键
   * @param value 值
   * @param time 时间(秒)
   * @return
   */
  public boolean lSet(String key, Object value, long time) {
    try {
      redisTemplate.opsForList().rightPush(this.buildKey(key), value);
      if (time > 0) {
        expire(this.buildKey(key), time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key 键
   * @param value 值
   * @return
   */
  public boolean lSet(String key, List<Object> value) {
    try {
      redisTemplate.opsForList().rightPushAll(this.buildKey(key), value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key 键
   * @param value 值
   * @param time 时间(秒)
   * @return
   */
  public boolean lSet(String key, List<Object> value, long time) {
    try {
      redisTemplate.opsForList().rightPushAll(this.buildKey(key), value);
      if (time > 0) {
        expire(this.buildKey(key), time);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 根据索引修改list中的某条数据
   *
   * @param key 键
   * @param index 索引
   * @param value 值
   * @return
   */
  public boolean lUpdateIndex(String key, long index, Object value) {
    try {
      redisTemplate.opsForList().set(this.buildKey(key), index, value);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 移除N个值为value
   *
   * @param key 键
   * @param count 移除多少个
   * @param value 值
   * @return 移除的个数
   */
  public long lRemove(String key, long count, Object value) {
    try {
      Long remove = redisTemplate.opsForList().remove(this.buildKey(key), count, value);
      return remove;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * 模糊查询获取key值
   *
   * @param pattern
   * @return
   */
  public Set keys(String pattern) {
    return redisTemplate.keys(pattern);
  }

  /**
   * 使用Redis的消息队列
   *
   * @param channel
   * @param message 消息内容
   */
  public void convertAndSend(String channel, Object message) {
    redisTemplate.convertAndSend(channel, message);
  }

  // =========BoundListOperations 用法 start============

  /**
   * 将数据添加到Redis的list中（从右边添加）
   *
   * @param listKey
   * @param expireEnum 有效期的枚举类
   * @param values 待添加的数据
   */
  public void addToListRight(String listKey, RedisStatus.ExpireEnum expireEnum, Object... values) {
    // 绑定操作
    BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
    // 插入数据
    boundValueOperations.rightPushAll(values);
    // 设置过期时间
    boundValueOperations.expire(expireEnum.getTime(), expireEnum.getTimeUnit());
  }

  /**
   * 根据起始结束序号遍历Redis中的list
   *
   * @param listKey
   * @param start 起始序号
   * @param end 结束序号
   * @return
   */
  public List<Object> rangeList(String listKey, long start, long end) {
    // 绑定操作
    BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
    // 查询数据
    return boundValueOperations.range(start, end);
  }

  /**
   * 弹出右边的值 --- 并且移除这个值
   *
   * @param listKey
   */
  public Object rifhtPop(String listKey) {
    // 绑定操作
    BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
    return boundValueOperations.rightPop();
  }

  // =========BoundListOperations 用法 End============
}
