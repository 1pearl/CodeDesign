package com.ivanzhao.IDesign.builderpattern;

import com.ivanzhao.IDesign.BaseInfra.Matter;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public interface IMenu {

    IMenu appendCeiling(Matter matter);

    IMenu appendCoat(Matter matter);

    IMenu appendFloor(Matter matter);

    IMenu appendTile(Matter matter);

    String getDetail();
}
