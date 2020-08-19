package source;

import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-19
 */
public class KafkaKVSource implements KafkaDeserializationSchema<Tuple2<String, String>> {

    @Override
    public boolean isEndOfStream(Tuple2<String, String> stringStringTuple2) {
        return false;
    }

    @Override
    public Tuple2<String, String> deserialize(ConsumerRecord<byte[], byte[]> consumerRecord) throws Exception {
        if (consumerRecord != null) {
            String key = null;
            String value = null;
            if (consumerRecord.key() != null) {
                key = new String(consumerRecord.key(), "UTF-8");
            }

            if (consumerRecord.value() != null) {
                value = new String(consumerRecord.value(), "UTF-8");
            }

            return Tuple2.of(key, value);
        } else {
            return Tuple2.of("null", "null");
        }
    }

    @Override
    public TypeInformation<Tuple2<String, String>> getProducedType() {
//        TypeInformation.of(new TypeHint<Tuple2<String,String>>(){});
        // new TypeHint是一个抽象类
        return TypeInformation.of(new TypeHint<Tuple2<String,String>>(){});
    }
}
