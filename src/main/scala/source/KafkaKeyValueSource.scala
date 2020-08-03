package source

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, KafkaDeserializationSchema}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.flink.streaming.api.scala._
import utils.PropertyRead

/**
  * Description : 读取kafka键值对的数据(重要)
  *
  * @author lyh
  * @date 2020-07-06
  */
object KafkaKeyValueSource {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val props = PropertyRead.getProperties("connect-kafka.properties")

    val stream = env.addSource(new FlinkKafkaConsumer[(String, String)]("t_bjsxt", new MyKafkaReader, props))

    stream.print()

    env.execute()
  }

  // 自定义一个类，从kafka中读取键值对的数据
  class MyKafkaReader extends KafkaDeserializationSchema[(String, String)] {
    // 是否流结束，读到什么流结束
    override def isEndOfStream(t: (String, String)): Boolean = {
      false
    }

    // 反序列化
    override def deserialize(consumerRecord: ConsumerRecord[Array[Byte], Array[Byte]]): (String, String) = {
      if (consumerRecord != null) {
        var key: String = null
        var value: String = null
        if (consumerRecord.key() != null) {
          key = new String(consumerRecord.key(), "UTF-8")
        }
        if (consumerRecord.value() != null) {
          value = new String(consumerRecord.value(), "UTF-8")
        }
        (key, value)
      } else {
        ("null", "null")
      }
    }

    // 指定类型
    override def getProducedType: TypeInformation[(String, String)] = {
      createTuple2TypeInformation(createTypeInformation[String], createTypeInformation[String])
    }
  }

}
