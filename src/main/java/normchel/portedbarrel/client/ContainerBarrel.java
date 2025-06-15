package normchel.portedbarrel.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerBarrel extends Container {
    private IInventory barrelInventory;

    public ContainerBarrel(IInventory playerInventory, IInventory barrelInventory) {
        this.barrelInventory = barrelInventory;

        // слоты бочки (3x3)
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                addSlotToContainer(new Slot(barrelInventory, x + y * 3, 62 + x * 18, 17 + y * 18));
            }
        }

        // слоты инвентаря игрока
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // хотбар
        for (int x = 0; x < 9; ++x) {
            addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return barrelInventory.isUseableByPlayer(player);
    }
}
