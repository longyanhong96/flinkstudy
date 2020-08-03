package state

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import source.StationLog
import org.apache.flink.streaming.api.scala._

/**
  * Description : XXX
  *
  * @author lyh
  * @date 2020-07-15
  */
object TimeIntervalDemo2 {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val textStream = env.readTextFile("data/stationlog")
      .map(line => {
        val values = line.split(",")
        StationLog(values(0), values(1), values(2), values(3), values(4).toLong, values(5).toLong)
      })

    textStream.keyBy(_.callOut)
      .flatMapWithState[String, StationLog] {
      case (in: StationLog, None) => (List.empty, Some(in))
      case (in: StationLog, pre: Some[StationLog]) => {
        val interval = Math.abs(pre.get.callTime - in.callTime)
        val outputStr = "callOut:" + in.callOut + " interval:" + interval
        (List(outputStr), Some(in))
      }
    }
      .print()

    env.execute()
  }
}
