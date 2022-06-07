package nextflow.xpool;

import nextflow.Session;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class XPool {
    final public static Map<String, Pool> pools = new HashMap<>();
    public static Session session;
    public static XPoolObserver observer;

    public static BasicPool loadBasicPool(String poolName, String fn) throws IOException {
        Yaml yaml = new Yaml();
        try(InputStream ins = new FileInputStream(fn)) {
            Map<String, Object> resources = yaml.load(ins);
            BasicPool pool = new BasicPool(poolName, resources);
            pools.put(poolName, pool);
            return pool;
        }
    }
}
