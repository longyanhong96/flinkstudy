package source;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-09-01
 */
public class HiveSource extends RichSourceFunction<String> {

    private Boolean isRunning = true;

    private String driverName = "org.apache.hive.jdbc.HiveDriver";

    private String Url = "jdbc:hive2://mini1:10000/qftest";

    private String sql = "select * from test1 where userid=?";

    private Connection conn = null;
    private PreparedStatement state = null;
    private ResultSet res = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName(driverName);
        conn = DriverManager.getConnection(Url, "root", "123456");
        state = conn.prepareStatement(sql);
    }

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        while (isRunning) {
            state.setString(1, "A");
            res = state.executeQuery();
            while (res.next()) {
                String result = res.getString(1) + "\t" + res.getString(2) + "\t" + res.getString(3);
                sourceContext.collect(result);
            }
            TimeUnit.SECONDS.sleep(10);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
