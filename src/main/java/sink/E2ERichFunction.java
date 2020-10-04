package sink;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.runtime.state.CheckpointListener;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-09-23
 */
public class E2ERichFunction extends RichMapFunction<String,String> implements CheckpointedFunction, CheckpointListener {
    @Override
    public String map(String s) throws Exception {
        return null;
    }

    @Override
    public void notifyCheckpointComplete(long l) throws Exception {

    }

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext functionInitializationContext) throws Exception {

    }
}
