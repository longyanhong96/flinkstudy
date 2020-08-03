package arithmetic

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import source.StationLog

/**
  * Description : 监控每一个手机，如果在5秒内呼叫它的通话都是失败的，发出警告信息
  *
  * @author lyh
  * @date 2020-07-13
  */
object MonitorDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val logStream = env.socketTextStream("mini1", 7777)
      .map(line => {
        val values = line.split(",")
        val stationLog = StationLog(values(0), values(1), values(2), values(3), values(4).toLong, values(5).toLong)
        //        (stationLog.callIn, stationLog)
        stationLog
      })

    logStream.keyBy(_.callIn) //先keyBy才可以
      .process(new MonitorProcessFunction)
      .print()

    env.execute()
  }
}
