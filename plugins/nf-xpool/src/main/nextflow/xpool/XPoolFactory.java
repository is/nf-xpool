package nextflow.xpool;

import nextflow.Session;
import nextflow.trace.TraceObserver;
import nextflow.trace.TraceObserverFactory;
import nextflow.xpool.api.XPool;
import nextflow.xpool.api.XPoolObserver;

import java.util.ArrayList;
import java.util.Collection;

public class XPoolFactory implements TraceObserverFactory {
    @Override
    public Collection<TraceObserver> create(Session session) {
        if (XPool.observer == null) {
            XPool.observer = new XPoolObserver();
        }

        ArrayList<TraceObserver> observers = new ArrayList<>();
        observers.add(XPool.observer);
        return observers;
    }
}
