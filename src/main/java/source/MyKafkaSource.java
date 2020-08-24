package source;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-19
 */
public class MyKafkaSource implements KafkaDeserializationSchema<String> {

    private Map<String, String> contents;

    public MyKafkaSource(Map<String, String> contents) {
        this.contents = contents;
    }

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
            return combineXmlAndJson(valueJson);
        }
        return null;
    }

    @Override
    public TypeInformation<String> getProducedType() {
        return TypeInformation.of(String.class);
    }

    private String combineXmlAndJson(JSONObject source) {
        HashMap<String, Object> target = new HashMap<>();
        source.forEach((k, v) -> {
            JSONObject xmlContent = JSON.parseObject(contents.get(k));
            String type = xmlContent.getString("type");
            changeValueType(type, v.toString());
            target.put(xmlContent.getString("name"), v);
        });
        String targetJson = JSON.toJSONString(target);
        return targetJson;
    }

    private void changeValueType(String type, String value) {
        switch (type) {
            case "0":
                Integer.parseInt(value);
                break;
            case "2":
                Date.valueOf(value);
                break;
            case "3":
                Long.parseLong(value);
                break;
            default:
                break;
        }
    }
}
