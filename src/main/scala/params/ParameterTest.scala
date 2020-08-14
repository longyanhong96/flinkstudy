package params

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

/**
  * Description : 动态参数配置test
  *
  * @author lyh
  * @date 2020-08-13
  */
object ParameterTest {
  def main(args: Array[String]): Unit = {
    val parameterTool = ParameterTool.fromArgs(args)

    val path = parameterTool.get("path")
    println(path)
    val taskId = parameterTool.get("taskId")
    println(taskId)

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.readTextFile(path)
      .flatMap(_.split("\\s+"))
      .map((_,1))
      .keyBy(0)
      .sum(1)
      .print()

    env.execute()
  }
}
