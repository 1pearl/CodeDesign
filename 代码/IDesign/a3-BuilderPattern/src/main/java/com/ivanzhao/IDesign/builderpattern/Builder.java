package com.ivanzhao.IDesign.builderpattern;

import com.ivanzhao.IDesign.BaseInfra.ceiling.LevelOneCeiling;
import com.ivanzhao.IDesign.BaseInfra.ceiling.LevelTwoCeiling;
import com.ivanzhao.IDesign.BaseInfra.coat.DuluxCoat;
import com.ivanzhao.IDesign.BaseInfra.coat.LiBangCoat;
import com.ivanzhao.IDesign.BaseInfra.floor.ShengXiangFloor;
import com.ivanzhao.IDesign.BaseInfra.tile.DongPengTile;
import com.ivanzhao.IDesign.BaseInfra.tile.MarcoPoloTile;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Builder {

    public IMenu levelOne(Double area) {
        return new DecorationPackageMenu(area,"豪华式")
                .appendCeiling(new LevelTwoCeiling())
                .appendCoat(new DuluxCoat())
                .appendFloor(new ShengXiangFloor());
    }

    public IMenu levelTwo(Double area) {
        return new DecorationPackageMenu(area,"轻奢式")
                .appendCeiling(new LevelTwoCeiling())
                .appendCoat(new LiBangCoat())
                .appendTile(new MarcoPoloTile());
    }

    public IMenu levelThree(Double area) {
        return new DecorationPackageMenu(area,"现代式")
                .appendCeiling(new LevelOneCeiling())
                .appendCoat(new LiBangCoat())
                .appendTile(new DongPengTile());
    }
}
