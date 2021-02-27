package cp;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import source.MyKafkaSource;
import utils.PropertyRead;

import java.util.Properties;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2021-02-26
 */
public class CheckpointRestoreTest {

    private static String topic = "flinktest";

    public static void main(String[] args) {
        Properties props = PropertyRead.getProperties("connect-kafka.properties");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.enableCheckpointing(1000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        env.getCheckpointConfig().setFailOnCheckpointingErrors(true);

        env.setStateBackend(new FsStateBackend("hdfs:///flink/checkpoints"));

        DataStreamSource<String> dataSourceStream = env.addSource(new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), props));
        dataSourceStream.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] split = s.split(",");
                for (int i = 0; i < split.length; i++) {
                    Tuple2<String, Integer> tuple2 = new Tuple2<>();
                    tuple2.setFields(split[i], 1);
                    collector.collect(tuple2);
                }
            }
        }).keyBy(0).sum(1).print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
