package com.ivanzhao.IDesign.compositepattern.service.engine;

import com.ivanzhao.IDesign.compositepattern.service.logic.LogicFilter;
import com.ivanzhao.IDesign.compositepattern.service.logic.impl.UserAgeFilter;
import com.ivanzhao.IDesign.compositepattern.service.logic.impl.UserGenderFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EngineConfig {

    static Map<String, LogicFilter> logicFilterMap;

    static {
        logicFilterMap = new ConcurrentHashMap<>();
        logicFilterMap.put("userAge", new UserAgeFilter());
        logicFilterMap.put("userGender", new UserGenderFilter());
    }

    public Map<String, LogicFilter> getLogicFilterMap() {
        return logicFilterMap;
    }

    public void setLogicFilterMap(Map<String, LogicFilter> logicFilterMap) {
        this.logicFilterMap = logicFilterMap;
    }

}