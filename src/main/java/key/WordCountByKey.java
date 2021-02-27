package key;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import source.MyCustomerSource;
import source.MySource;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-11-06
 */
public class WordCountByKey {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.addSource(new MySource())
                .flatMap(new MyFlatMapFunction())
                .keyBy(0)
                .sum(1)
//                process(new MyKeyProcessFunction())
                .print();



        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
