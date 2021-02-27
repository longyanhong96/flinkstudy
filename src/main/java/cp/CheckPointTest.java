package cp;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import utils.PropertyRead;

import java.util.Properties;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-12-10
 */
public class CheckPointTest {

    private static Properties properties = PropertyRead.getProperties("connect-kafka.properties");

    private static String topic = "flinkintojanus";
    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.enableCheckpointing(1000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);

        env.setStateBackend(new FsStateBackend("file:///h:/myworkspace/flinkstudy/data/flink/checkpoints"));

        FlinkKafkaConsumer<String> flinkKafkaConsumer = new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), properties);

        DataStreamSource<String> kafkaStream = env.addSource(flinkKafkaConsumer);

        SingleOutputStreamOperator<String> mapStream = kafkaStream.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                if (s.contains("a")) {
                    String msg = String.format("Bad data [%s]...", s);
                    throw new RuntimeException(msg);
                }
                return s;
            }
        });

        mapStream.print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
