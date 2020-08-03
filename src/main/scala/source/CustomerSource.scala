package source

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
/**
  * Description : XXX
  *
  * @author lyh
  * @date 2020-07-06
  */
object CustomerSource {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val stream = env.addSource(new MyCustomerSource)

    stream.print()

    env.execute()
  }
}
