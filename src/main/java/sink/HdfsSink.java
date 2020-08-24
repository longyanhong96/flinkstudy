package sink;

import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.DateTimeBucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;
import source.MyCustomerSource;
import source.StationLog;

import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-08-20
 */
public class HdfsSink {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<StationLog> mySourceStream = env.addSource(new MyCustomerSource());

        DefaultRollingPolicy<StationLog, String> rolling = DefaultRollingPolicy.create()
                .withInactivityInterval(TimeUnit.SECONDS.toMillis(60))
                .withRolloverInterval(TimeUnit.SECONDS.toMillis(60))
                .build();

        StreamingFileSink<StationLog> hdfsSink = StreamingFileSink.forRowFormat(
                new Path("hdfs://ns1/flinkIntoHdfs"),
                new SimpleStringEncoder<StationLog>("UTF-8"))
                .withBucketAssigner(new DateTimeBucketAssigner<>("yyyyMMdd", ZoneId.of("Asia/Shanghai")))
                .withRollingPolicy(rolling)
                .withBucketCheckInterval(TimeUnit.SECONDS.toMillis(10))
                .build();

        mySourceStream.addSink(hdfsSink).setParallelism(1);

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
