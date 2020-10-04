package sink;

import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.streaming.api.functions.sink.TwoPhaseCommitSinkFunction;

/**
 * Description : XXX
 *
 * @author lyh
 * @date 2020-09-23
 */
public class E2ESink extends TwoPhaseCommitSinkFunction {
    public E2ESink(TypeSerializer transactionSerializer, TypeSerializer contextSerializer) {
        super(transactionSerializer, contextSerializer);
    }

    @Override
    protected void invoke(Object o, Object o2, Context context) throws Exception {

    }

    @Override
    protected Object beginTransaction() throws Exception {
        return null;
    }

    @Override
    protected void preCommit(Object o) throws Exception {

    }

    @Override
    protected void commit(Object o) {

    }

    @Override
    protected void abort(Object o) {

    }
}
