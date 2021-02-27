package key;

import lombok.extern.java.Log;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

import java.util.Random;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-11-07
 */
@Log
public class MyFlatMapFunction extends RichFlatMapFunction<String, Tuple2<String, Integer>> {

    private Random random;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        random = new Random();
    }

    @Override
    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
        String[] split = s.split("\\s+");
        for (int i = 0; i < split.length; i++) {
            String oneStr = split[i];
            Tuple2<String, Integer> output = new Tuple2<>();
            output.setFields(oneStr, 1);
            collector.collect(output);
        }
    }
}
