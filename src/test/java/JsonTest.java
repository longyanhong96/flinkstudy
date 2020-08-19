import com.alibaba.fastjson.JSONObject;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-19
 */
public class JsonTest {
    public static void main(String[] args) {
        String key = "{\"transInfo\":\"123456\",\"transCode\":\"T004\"}";
        String value = "{\"props\":\"abc\",\"batch\":\"qwe\"}";

        JSONObject keyJson = JSONObject.parseObject(key);
        JSONObject valueJson = JSONObject.parseObject(value);

        keyJson.keySet().forEach(k -> valueJson.put(k, keyJson.getString(k)));
        System.out.println("valueJson = " + valueJson);
    }
}
