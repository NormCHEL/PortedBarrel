package normchel.portedbarrel.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import normchel.portedbarrel.PortedBarrel;
import normchel.portedbarrel.common.StorageTile;

public class ModBlocks {

    public static void register() {
        GameRegistry.registerBlock(new Barrel(), "barrel");
        GameRegistry.registerTileEntity(StorageTile.class, PortedBarrel.MOD_ID+"barrelTile");
    }
}
