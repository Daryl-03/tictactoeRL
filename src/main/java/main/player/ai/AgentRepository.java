package main.player.ai;

import java.util.Map;

public interface AgentRepository {

    Map<String, Double> readValueFunction();
    void saveValueFunction(Map<String, Double> valueFunction);

}
