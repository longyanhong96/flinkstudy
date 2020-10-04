package source;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-09-02
 */
public class MyMultiKafkaSource implements KafkaDeserializationSchema<String> {

    @Override
    public boolean isEndOfStream(String s) {
        return false;
    }

    @Override
    public String deserialize(ConsumerRecord<byte[], byte[]> consumerRecord) throws Exception {
        if (consumerRecord != null) {
            String value = null;
            if (consumerRecord.value() != null) {
                value = new String(consumerRecord.value(), "UTF-8");
            }
            JSONObject valueJson = JSONObject.parseObject(value);
            valueJson.put("topic", consumerRecord.topic());
            return valueJson.toJSONString();
        }
        return null;
    }

    @Override
    public TypeInformation<String> getProducedType() {
        return TypeInformation.of(String.class);
    }
}
