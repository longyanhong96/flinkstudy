package source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import utils.PropertyRead;
import utils.XMLParse;

import java.util.Map;
import java.util.Properties;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-20
 */
public class ConsumerKafkaSource {

    private static String topic = "flinktest";

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Map<String, String> xmlContents = XMLParse.parseXmlByRelativePath("realtime.xml", "position");
        Properties props = PropertyRead.getProperties("connect-kafka.properties");

        DataStreamSource<String> kafkaStream = env.addSource(new FlinkKafkaConsumer<>(topic, new MyKafkaSource(xmlContents), props));

        kafkaStream.print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
