package arithmetic

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
  * Description : XXX
  *
  * @author lyh
  * @date 2020-07-09
  */
object KeyByDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.streaming.api.scala._

    env.fromElements((1, 5), (2, 2), (2, 4), (1, 3))
      .keyBy(0)
      .print()

    env.execute()
  }
}
