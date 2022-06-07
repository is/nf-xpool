package nextflow.xpool;

import java.util.Map;

public interface Pool {
    String getName();
    Object allocated(Object handler);
    void release(String rstr);
}
