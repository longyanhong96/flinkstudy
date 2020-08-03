package sink

import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import source.{MyCustomerSource, StationLog}

/**
  * Description : 把自定义的source作为数据源，把基站日志数据写入HDFS并且每个两秒钟生成一个文件
  *
  * @author lyh
  * @date 2020-07-06
  */
object HDFSSink {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val stream = env.addSource(new MyCustomerSource)

    // 默认一个小时一个目录（分桶）
    //设置一个滚动策略
    val rolling: DefaultRollingPolicy[StationLog, String] = DefaultRollingPolicy.create()
      .withInactivityInterval(5000) //不活动的分桶时间
      .withRolloverInterval(2000)
      .build()

    val hdfsSink = StreamingFileSink.forRowFormat[StationLog](
      new Path("hdfs://mini2:9000/mysink/"),
      new SimpleStringEncoder[StationLog]("UTF-8")
    ).withRollingPolicy(rolling)
      .withBucketCheckInterval(1000)
      .build()

    stream.addSink(hdfsSink)

    env.execute()
  }
}
