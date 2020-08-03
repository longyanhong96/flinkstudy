package source

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import utils.PropertyRead

/**
  * Description : 读取kafka的无键值对数据
  *
  * @author lyh
  * @date 2020-07-06
  */
object KafkaSource {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.streaming.api.scala._

    val props = PropertyRead.getProperties("connect-kafka.properties")

    val stream = env.addSource(new FlinkKafkaConsumer[String]("t_bjsxt", new SimpleStringSchema(), props))

    stream.print()

    env.execute()
  }
}
