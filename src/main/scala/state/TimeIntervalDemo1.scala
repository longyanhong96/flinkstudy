package state

import org.apache.flink.api.common.functions.{RichFlatMapFunction, RichMapFunction}
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import source.StationLog
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
  * Description : 计算每个手机的呼叫间隔时间，单位是毫秒
  *
  * @author lyh
  * @date 2020-07-15
  */
object TimeIntervalDemo1 {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val textStream = env.readTextFile("data/stationlog")
      .map(line => {
        val values = line.split(",")
        StationLog(values(0), values(1), values(2), values(3), values(4).toLong, values(5).toLong)
      })

    textStream.keyBy(_.callOut)
      .flatMap(new TimeIntervalFlatMapFunction)
      .print()

    env.execute()
  }

  class TimeIntervalFlatMapFunction extends RichFlatMapFunction[StationLog, String] {

    private var preStation: ValueState[StationLog] = _

    override def open(parameters: Configuration): Unit = {
      preStation = getRuntimeContext.getState(new ValueStateDescriptor[StationLog]("interval", classOf[StationLog]))
    }

    override def close(): Unit = {}

    override def flatMap(in: StationLog, collector: Collector[String]): Unit = {
      val preStationValue = preStation.value()
      if (preStationValue == null) {
        preStation.update(in)
      } else {
        val preStationLog = preStation.value()
        val interval = Math.abs(preStationLog.callTime - in.callTime)
        val outputStr = "callOut:" + in.callOut + " interval:" + interval
        collector.collect(outputStr)
        preStation.update(in)
      }
    }


  }

}
