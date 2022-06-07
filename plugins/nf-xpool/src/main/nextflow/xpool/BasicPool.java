package nextflow.xpool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class BasicPool implements Pool {
    String name;
    HashMap<String, Object> resources;
    LinkedList<String> availables;
    XPoolObserver observer;

    public String getName() {
        return name;
    }

    public BasicPool(String name, Map<String, Object> resources) {
        this.name = name;
        this.resources = new HashMap<>(resources);
        this.availables = new LinkedList(resources.keySet());

        this.observer = XPool.observer;
    }


    @Override
    public Object allocated(Object handler) {
        synchronized (this) {
            if (availables.size() == 0) {
                return null;
            }
            String key = availables.pop();
            observer.add(handler, this, key);
            return resources.get(key);
        }
    }

    @Override
    public synchronized void release(String key) {
        synchronized (this) {
            availables.push(key);
        }
    }
}
