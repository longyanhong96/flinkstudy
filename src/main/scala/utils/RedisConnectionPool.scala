package utils

import redis.clients.jedis.{JedisPool, JedisPoolConfig}

/**
  * Description : Redis连接池
  *
  * @author lyh
  * @date 2020-06-21
  */
object RedisConnectionPool {

  private val config = new JedisPoolConfig()
  config.setMaxTotal(20)
  config.setMaxIdle(10)
  config.setTestOnBorrow(true)

  private val pool = new JedisPool(config,"mini1",6379)

  def getConnection()={
    pool.getResource
  }
}
