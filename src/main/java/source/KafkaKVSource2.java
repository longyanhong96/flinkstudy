package source;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : 把key和value合成一个json
 *
 * @author lyh
 * @date 2020-08-19
 */
public class KafkaKVSource2 implements KafkaDeserializationSchema<String> {

    private Map<String, String> contents;

    @Override
    public boolean isEndOfStream(String s) {
        return false;
    }

    @Override
    public String deserialize(ConsumerRecord<byte[], byte[]> consumerRecord) throws Exception {
        if (consumerRecord != null) {
            String key = null;
            String value = null;
            if (consumerRecord.key() != null) {
                key = new String(consumerRecord.key(), "UTF-8");
            }

            if (consumerRecord.value() != null) {
                value = new String(consumerRecord.value(), "UTF-8");
            }

            JSONObject keyJson = JSONObject.parseObject(key);
            JSONObject valueJson = JSONObject.parseObject(value);

            keyJson.keySet().forEach(k -> valueJson.put(k, keyJson.getString(k)));
            return valueJson.toJSONString();
        }
        return null;
    }

    @Override
    public TypeInformation<String> getProducedType() {
        return TypeInformation.of(String.class);
    }

}
