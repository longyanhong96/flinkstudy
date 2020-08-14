package arithmetic

import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector
import source.StationLog

/**
  * Description : XXX
  *
  * @author lyh
  * @date 2020-07-13
  */
object SideOutDemo {

  val tag = new OutputTag[StationLog]("noSuccess")

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sideOutStream = env.readTextFile("data/stationlog")
      .map(line => {
        val values = line.split(",")
        StationLog(values(0), values(1), values(2), values(3), values(4).toLong, values(5).toLong)
      })
      .process(new SideOutProcessFunctions2)

    sideOutStream.print("成功的")
    sideOutStream.getSideOutput(tag).print("不成功的")

    env.execute()
  }


  class SideOutProcessFunctions extends ProcessFunction[StationLog, StationLog] {
    override def processElement(i: StationLog, context: ProcessFunction[StationLog, StationLog]#Context, collector: Collector[StationLog]): Unit = {
      if (i.callType.equals("success")) {
        collector.collect(i)
      } else {
        context.output(tag, i)
      }
    }
  }

  class SideOutProcessFunctions2 extends ProcessFunction[StationLog, StationLog] {
    override def processElement(i: StationLog, context: ProcessFunction[StationLog, StationLog]#Context, collector: Collector[StationLog]): Unit = {
      collector.collect(i)
      if (!i.callType.equals("success")) {
        context.output(tag, i)
      }
    }
  }

}
