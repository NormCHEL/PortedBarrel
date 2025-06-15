package normchel.portedbarrel;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import normchel.portedbarrel.client.ClientProxy;
import normchel.portedbarrel.common.CommonProxy;
import normchel.portedbarrel.common.gui.GuiHandlerBarrel;

@Mod(modid = PortedBarrel.MOD_ID, version = PortedBarrel.VERSION)
public class PortedBarrel {
    public static final String MOD_ID = "portedbarrel";
    public static final String VERSION = "1.0";
    @Mod.Instance(PortedBarrel.MOD_ID)
    public static PortedBarrel instance;

    @SidedProxy(
            serverSide = "normchel.portedbarrel.common.CommonProxy",
            clientSide = "normchel.portedbarrel.client.ClientProxy"
    )
    public static CommonProxy proxy;
    public static ClientProxy clientProxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerBarrel());
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
