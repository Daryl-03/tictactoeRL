package main.ai;

import java.util.Map;

public interface AgentRepository {

    Map<String, Double> readValueFunction();
    boolean saveValueFunction(Map<String, Double> valueFunction);

}
