package source;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import utils.PropertyRead;

import java.util.Properties;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-19
 */
public class KafkaKeyValue {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties props = PropertyRead.getProperties("connect-kafka.properties");
        // java.io.EOFException
//        DataStreamSource<Tuple2<String, String>> kafkaStream = env.addSource(new FlinkKafkaConsumer<>("flinktest",
//                new TypeInformationKeyValueSerializationSchema<String, String>(TypeInformation.of(String.class), TypeInformation.of(String.class),
//                        env.getConfig()), props));

        DataStreamSource<String> kafkaStream = env.addSource(new FlinkKafkaConsumer<>("flinktest", new KafkaKVSource2(), props));

        kafkaStream.print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
