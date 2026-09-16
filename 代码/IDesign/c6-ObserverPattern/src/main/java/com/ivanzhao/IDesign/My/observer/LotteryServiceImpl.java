package com.ivanzhao.IDesign.My.observer;

import com.ivanzhao.IDesign.Infra.MinibusTargetService;
import com.ivanzhao.IDesign.common.LotteryResult;

import java.util.Date;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class LotteryServiceImpl extends LotteryService {

    MinibusTargetService minibusTargetService = new MinibusTargetService();

    @Override
    public LotteryResult doDraw(String uid) {
        String lottery = minibusTargetService.lottery(uid);
        return new LotteryResult(uid,lottery,new Date());
    }
}
