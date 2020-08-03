package dynamic.broadcast;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.concurrent.TimeUnit;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-02
 */
public class MyBroadcastStream extends RichSourceFunction<String> {

    private volatile boolean isRunning = true;
    //测试数据集
    private String[] dataSet = new String[]{
            "java",
            "swift",
            "php",
            "go",
            "python"
    };

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        int size = dataSet.length;
        while (isRunning) {
            TimeUnit.SECONDS.sleep(30);
            int seed = (int) (Math.random() * size);
            sourceContext.collect(dataSet[seed]);
            System.out.println("读取到上游发送的关键字:" + dataSet[seed]);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
