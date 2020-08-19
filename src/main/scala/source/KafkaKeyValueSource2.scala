package source

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.util.serialization.TypeInformationKeyValueSerializationSchema
import org.apache.flink.streaming.api.scala._
import utils.PropertyRead

/**
  * Description : XXX
  *
  * @author lyh
  * @date 2020-08-19
  */
object KafkaKeyValueSource2 {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val props = PropertyRead.getProperties("connect-kafka.properties")
    val schema = new TypeInformationKeyValueSerializationSchema(classOf[String], classOf[String], env.getConfig)
    val kafkaStream = env.addSource(new FlinkKafkaConsumer("flinktest",schema,props))

    kafkaStream.print()

    env.execute()
  }

}
