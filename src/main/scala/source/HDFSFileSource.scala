package source

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
  * Description : XXX
  *
  * @author lyh
  * @date 2020-07-06
  */
object HDFSFileSource {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.streaming.api.scala._

//    env.readTextFile()

  }
}
