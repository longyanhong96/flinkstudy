import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-09-08
 */
public class SimpleTest {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        long start = System.currentTimeMillis();
        env.readTextFile("H:\\myworkspace\\flinkstudy\\data\\wordcount").print();
        long end = System.currentTimeMillis();

        System.out.println(end - start);
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
