package arithmetic

import java.sql.Connection

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.configuration.Configuration
import redis.clients.jedis.Jedis
import source.StationLog
import utils.{MysqlConnectPool, RedisConnectionPool}

import scala.collection.mutable


/**
  * Description : XXX
  *
  * @author lyh
  * @date 2020-07-13
  */
class DatabaseMapFuction extends RichMapFunction[StationLog, String] {

  val dataBaseName = "phone"
  var rt = new mutable.HashMap[String, String]
  var connection: Connection = null;
  var jedis: Jedis = null;

  override def open(parameters: Configuration): Unit = {
    connection = MysqlConnectPool.getConnection
    jedis = RedisConnectionPool.getConnection()
  }


  override def close(): Unit = {
    connection.close()
    jedis.close()
  }

  override def map(t: StationLog): String = {
    val callIn = t.callIn
    val callOut = t.callOut

    var callInName = rt.get(callIn).getOrElse("null")
    var callOutName = rt.get(callOut).getOrElse("null")
    if (callInName.eq("null") || callOutName.eq("null")) {
      val getInRedis = selectToRedis(dataBaseName, callIn, callOut)
      if (!getInRedis) {
        selectToMysql(callIn, callOut)
        callInName = rt.get(callIn).getOrElse("null")
        callOutName = rt.get(callOut).getOrElse("null")
        insertIntoRedis(dataBaseName, callIn, callOut, callInName, callOutName)
      }
    }

    t.toString + " callInName:" + callInName + " callOutName:" + callOutName
  }

  /**
    * 如果rt没有该值，redis也没有该值，再查mysql
    *
    * @param callIn
    * @param callOut
    */
  def selectToMysql(callIn: String, callOut: String) = {
    //    new mutable.HashMap[String,JSONObject]()

    val sql =
      """
        |select phonenum
        | ,name
        |from phone
        |where phonenum=?
      """.stripMargin

    val ps = connection.prepareStatement(sql)

    ps.setString(1, callIn)
    val callInResult = ps.executeQuery()
    while (callInResult.next()) {
      val callInName = callInResult.getString(2)
      rt.put(callIn, callInName)
    }

    ps.setString(1, callOut)
    val callOutResult = ps.executeQuery()
    while (callOutResult.next()) {
      val callOutName = callOutResult.getString(2)
      rt.put(callOut, callOutName)
    }
  }

  /**
    * 如果rt没有该值，查redis
    *
    * @param dataBaseName redis数据库名字
    * @param callIn
    * @param callOut
    * @return
    */
  def selectToRedis(dataBaseName: String, callIn: String, callOut: String) = {
    var flag = true
    val callInName = jedis.hget(dataBaseName, callIn)
    val callOutName = jedis.hget(dataBaseName, callOut)
    if (callInName == null && callOutName == null) {
      flag = false
    } else {
      rt.put(callIn, callInName)
      rt.put(callOut, callOutName)
    }

    flag
  }

  /**
    * 如果redis没有，查mysql，并写入到redis中
    *
    * @param dataBaseName
    * @param callIn
    * @param callOut
    * @param callInName
    * @param callOutName
    */
  def insertIntoRedis(dataBaseName: String,
                      callIn: String,
                      callOut: String,
                      callInName: String,
                      callOutName: String): Unit = {
    jedis.hset(dataBaseName, callIn, callInName)
    jedis.hset(dataBaseName, callOut, callOutName)
  }

}
