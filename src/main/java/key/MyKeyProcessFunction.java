package key;

import lombok.extern.java.Log;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-11-07
 */
@Log
public class MyKeyProcessFunction extends KeyedProcessFunction<Tuple, Tuple2<String, Integer>, Tuple2<String, Integer>> {

    private int result;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        result = 0;
    }

    @Override
    public void processElement(Tuple2<String, Integer> stringIntegerTuple2, Context context, Collector<Tuple2<String, Integer>> collector) throws Exception {
        String first = stringIntegerTuple2.getField(0).toString();
        Object secondField = stringIntegerTuple2.getField(1);
        int second = Integer.parseInt(secondField.toString());

        result += second;

        Tuple2<String, Integer> output = new Tuple2<>();
        output.setFields(first, result);
        collector.collect(output);

    }
}
