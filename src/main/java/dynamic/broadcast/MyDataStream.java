package dynamic.broadcast;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.concurrent.TimeUnit;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-02
 */
public class MyDataStream extends RichSourceFunction<String> {

    private volatile boolean isRunning = true;

    private String[] dataSet = new String[]{
            "java是世界上最优秀的语言",
            "swift是世界上最优秀的语言",
            "php是世界上最优秀的语言",
            "go是世界上最优秀的语言",
            "python是世界上最优秀的语言"
    };

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        int size = dataSet.length;
        while (isRunning) {
            TimeUnit.SECONDS.sleep(3);
            int seed = (int) (Math.random() * size);
            sourceContext.collect(dataSet[seed]);
            System.out.println("读取到上游发送的消息：" + dataSet[seed]);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
