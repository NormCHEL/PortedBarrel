package normchel.portedbarrel.common.block;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static void register() {
        GameRegistry.registerBlock(new Barrel(), "barrel");
    }
}
