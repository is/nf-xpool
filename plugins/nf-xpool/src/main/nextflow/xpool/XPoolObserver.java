package nextflow.xpool;

import nextflow.Session;
import nextflow.processor.TaskHandler;
import nextflow.processor.TaskProcessor;
import nextflow.trace.TraceObserver;
import nextflow.trace.TraceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XPoolObserver implements TraceObserver {
    private static final Logger L = LoggerFactory.getLogger(XPoolObserver.class);

    static class AllocInfo {
        public Object id;
        public String key;
        public Pool pool;

        public AllocInfo(Object id, String key, Pool pool) {
            this.id = id;
            this.key = key;
            this.pool = pool;
        }
    }

    final Map<Object, List<AllocInfo>> allocInfos;


    public XPoolObserver() {
        this.allocInfos = new HashMap<>();
    }

    public void add(Object id, Pool pool, String key) {
        L.info("XPOOL.OBSERVER add {} {} {}", id, pool, key);
        synchronized (allocInfos) {
            allocInfos.compute(id, (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                v.add(new AllocInfo(id, key, pool));
                return v;
            });
        }
    }

    @Override
    public void onFlowCreate(Session session) {
    }

    @Override
    public void onFlowBegin() {
    }

    @Override
    public void onFlowComplete() {
    }

    @Override
    public void onProcessCreate(TaskProcessor process) {
    }

    @Override
    public void onProcessTerminate(TaskProcessor process) {
    }

    @Override
    public void onProcessPending(TaskHandler handler, TraceRecord trace) {
    }

    @Override
    public void onProcessSubmit(TaskHandler handler, TraceRecord trace) {
    }

    @Override
    public void onProcessStart(TaskHandler handler, TraceRecord trace) {
    }

    @Override
    public void onProcessComplete(TaskHandler handler, TraceRecord trace) {
        L.info("XPOOL.OBSERVER onProcessComplete {} {}",
            handler.getTask().getName(), handler.getTask().getConfig());
        Object id = handler.getTask().getConfig();
        synchronized (allocInfos) {
            List<AllocInfo> infos = allocInfos.remove(id);
            if (infos != null) {
                for (AllocInfo info: infos) {
                    L.info("XPOOL.OBSERVER release {}", info.key);
                    info.pool.release(info.key);
                }
            }
        }
    }

    @Override
    public void onProcessCached(TaskHandler handler, TraceRecord trace) {
    }

    @Override
    public boolean enableMetrics() {
        return false;
    }

    @Override
    public void onFlowError(TaskHandler handler, TraceRecord trace) {

    }

    @Override
    public void onFilePublish(Path destination) {

    }
}
