package dynamic.broadcast;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-02
 */
public class MyBroadcastProcessFunction extends BroadcastProcessFunction<String, String, String> {

    private String keywords = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        keywords = "java";
        System.out.println("初始化模拟连接数据库读取拦截关键字：" + keywords);
    }

    @Override
    public void processElement(String s, ReadOnlyContext readOnlyContext, Collector<String> collector) throws Exception {
        if (s.contains(keywords)){
            collector.collect("拦截消息:" + s + ", 原因:包含拦截关键字：" + keywords);
        }
    }

    @Override
    public void processBroadcastElement(String s, Context context, Collector<String> collector) throws Exception {
        keywords = s;
        System.out.println("关键字更新成功，更新拦截关键字：" + s);
    }
}
