package source

import java.util.Random

import org.apache.flink.streaming.api.functions.source.SourceFunction

/**
  * Description : 自定义的source，每个两秒钟，生成10条随机基站的数据
  *
  * @author lyh
  * @date 2020-07-06
  */
class MyCustomerSource extends SourceFunction[StationLog] {
  // 是否终止数据流的标记
  var flag = true

  // run方法一直进行，如果run方法停止了，则数据流停止
  override def run(sourceContext: SourceFunction.SourceContext[StationLog]): Unit = {
    val r = new Random()
    var types = Array("fail", "busy", "barring", "success")
    while (flag) {
      1.to(10).map(i => {
        //主叫号码
        var callOut = "1860000%04d".format(r.nextInt(10000))
        //被叫号码
        var callIn = "1860000%04d".format(r.nextInt(10000))
        //生成一条数据
        new StationLog("station_" + r.nextInt(10), callOut, callIn, types(r.nextInt(4)), System.currentTimeMillis(), r.nextInt(100) + 1)
      }).foreach(sourceContext.collect(_))
      Thread.sleep(2000) //每隔两秒发送一条数据
    }
  }

  // 终止数据流
  override def cancel(): Unit = {
    flag = false
  }
}
