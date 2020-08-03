package arithmetic

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import source.StationLog

/**
  * Description : 把呼叫成功的通话信息转化成真实的用户姓名，通话用户对应的用户表在mysql中
  * （频繁的查数据库不正确，我应该先查redis有没有，有的话join，没有的话在查mysql，然后把数据存在redis中）
  *
  * @author lyh
  * @date 2020-07-13
  */
object RichClassDemo2 {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.readTextFile("data/stationlog")
      .map(line => {
        val values = line.split(",")
        StationLog(values(0), values(1), values(2), values(3), values(4).toLong, values(5).toLong)
      })
      .map(new DatabaseMapFuction)
      .print()

    env.execute()
  }
}
