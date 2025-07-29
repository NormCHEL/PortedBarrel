package normchel.portedbarrel;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import normchel.portedbarrel.client.ClientProxy;
import normchel.portedbarrel.common.CommonProxy;
import normchel.portedbarrel.common.block.ModBlocks;
import normchel.portedbarrel.common.tileentities.GuiHandler;
import normchel.portedbarrel.common.tileentities.TileEntityBarrel;


@Mod(modid = PortedBarrel.MOD_ID, version = PortedBarrel.VERSION)
public class PortedBarrel {
    public static final String MOD_ID = "portedbarrel";
    public static final String VERSION = "1.0";
    @Mod.Instance(PortedBarrel.MOD_ID)
    public static PortedBarrel instance;
    public static final int GUI_BARREL = 0;


    @SidedProxy(
            serverSide = "normchel.portedbarrel.common.CommonProxy",
            clientSide = "normchel.portedbarrel.client.ClientProxy"
    )
    public static CommonProxy proxy;
    public static ClientProxy clientProxy;



    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.register();
        GameRegistry.registerTileEntity(TileEntityBarrel.class, "portedbarrel_storage");
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
