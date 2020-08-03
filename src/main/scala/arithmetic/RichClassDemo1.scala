package arithmetic

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import source.{MyCustomerSource, StationLog}

/**
  * Description : 计算出每个通话成功的日志中呼叫起始和结束时间
  *
  * @author lyh
  * @date 2020-07-13
  */
object RichClassDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.addSource(new MyCustomerSource)
      .map(new TimeMapFunction)
      .print()

    env.execute()
  }

}

class TimeMapFunction extends MapFunction[StationLog, String] {
  override def map(t: StationLog): String = {
    val duration = t.duration
    val callTime = t.callTime
    val endTime = callTime + duration * 1000

    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val callTimeFormat = format.format(new Date(callTime))
    val endTimeFormat = format.format(new Date(endTime))

    return "打电话者：" + t.callIn + " 被叫者：" + t.callOut + " 开始时间：" + callTimeFormat + " 结束时间：" + endTimeFormat + " 通话时间：" + duration
  }
}

