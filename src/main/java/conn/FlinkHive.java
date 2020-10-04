package conn;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import source.HiveSource;

import java.sql.Connection;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-09-01
 */
public class FlinkHive {


    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.addSource(new HiveSource()).print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
