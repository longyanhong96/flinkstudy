package source;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-11-06
 */
public class MySource extends RichSourceFunction<String> {

    private boolean isRunning = true;

    private List<String> words;

    private Random random;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        words = Arrays.asList("java", "scala", "spark", "flink", "hive");
        random = new Random();
    }

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        while (isRunning) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < 3; i++) {
                int index = random.nextInt(words.size());
                buffer.append(words.get(index)).append(" ");
            }
            TimeUnit.SECONDS.sleep(5);
            sourceContext.collect(buffer.toString());
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
