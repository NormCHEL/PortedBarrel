package normchel.portedbarrel.common.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import normchel.portedbarrel.client.ContainerBarrel;
import normchel.portedbarrel.common.StorageTile;

public class GuiHandlerBarrel implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof StorageTile) {
            return new ContainerBarrel(player.inventory, (IInventory) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof StorageTile) {
            return new GuiBarrel(player.inventory, (IInventory) te);
        }
        return null;
    }
}

