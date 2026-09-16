package com.ivanzhao.IDesign.compositepattern.service.engine;

import com.ivanzhao.IDesign.compositepattern.model.aggregates.TreeRich;
import com.ivanzhao.IDesign.compositepattern.model.vo.EngineResult;

import java.util.Map;

public interface IEngine {

    EngineResult process(final Long treeId, final String userId, TreeRich treeRich, final Map<String, String> decisionMatter);

}