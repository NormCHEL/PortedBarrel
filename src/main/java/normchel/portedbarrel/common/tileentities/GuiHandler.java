package normchel.portedbarrel.common.tileentities;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import normchel.portedbarrel.common.container.ContainerBarrel;
import normchel.portedbarrel.common.gui.BarrelGui;


public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityBarrel) {
            return new ContainerBarrel(player.inventory, (TileEntityBarrel) tileEntity);
        }
        return null;
    }



    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityBarrel) {
            return new BarrelGui(new ContainerBarrel(player.inventory, (IInventory) tileEntity));
        }
        return null;
    }
}

