package arithmetic

import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.util.Collector
import source.StationLog

/**
  * Description : XXX
  *
  * @author lyh
  * @date 2020-07-13
  */
class MonitorProcessFunction extends KeyedProcessFunction[String, StationLog, String] {


  lazy val timeState = getRuntimeContext.getState(new ValueStateDescriptor[Long]("time", classOf[Long]))

  override def processElement(i: StationLog, context: KeyedProcessFunction[String, StationLog, String]#Context, collector: Collector[String]): Unit = {
    val time = timeState.value()
    if (time == 0 && !i.callType.equals("success")) {
      val nowTime = context.timerService().currentProcessingTime()
      val onTime = nowTime + 5000L

      context.timerService().registerProcessingTimeTimer(onTime)
      timeState.update(onTime)
    }

    if (i.callType.equals("success") && time != 0) {
      context.timerService().deleteProcessingTimeTimer(time)
      timeState.clear()
    }
  }

  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, StationLog, String]#OnTimerContext, out: Collector[String]): Unit = {
    val warnString = "触发时间：" + timestamp + " 手机号：" + ctx.getCurrentKey
    out.collect(warnString)
    timeState.clear()
  }
}
