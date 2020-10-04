package source;


import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-09-28
 */
public class HDFSSource extends RichSourceFunction<String> {

    private boolean isRunning = true;

    private String cleanxmlPath = "/clnstd/install.log";

    private FileSystem fs;
    @Override
    public void open(Configuration parameters) throws Exception {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.addResource("core-site.xml");
        conf.addResource("hdfs-site.xml");
        fs = FileSystem.get(conf);
    }

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        while (isRunning){
            String xml = getXML(cleanxmlPath);
            sourceContext.collect(xml);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

    private String getXML(String path) throws Exception{
        FSDataInputStream in = fs.open(new Path(path));
        BufferedReader d = new BufferedReader(new InputStreamReader(in));
        String line = null;
        String rsXML = "";
        while ((line = d.readLine()) != null){
            rsXML += line;
        }
        return rsXML;
    }
}
