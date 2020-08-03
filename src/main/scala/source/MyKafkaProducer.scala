package source

import java.util.{Properties, Random}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

/**
  * Description : XXX
  *
  * @author lyh
  * @date 2020-07-06
  */
object MyKafkaProducer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.setProperty("bootstrap.servers", "mini1:9092,mini2:9092,mini3:9092")
    props.setProperty("key.serializer",classOf[StringSerializer].getName)
    props.setProperty("value.serializer",classOf[StringSerializer].getName)

    val producer = new KafkaProducer[String,String](props)

    val r = new Random()
    while(true){
      val record = new ProducerRecord[String,String]("t_bjsxt","key"+r.nextInt(10),"value"+r.nextInt(100))
      producer.send(record)
      Thread.sleep(1000)
    }
    producer.close()
  }
}
