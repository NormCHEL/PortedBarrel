package normchel.portedbarrel.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerBarrel extends Container {
    public ContainerBarrel(InventoryPlayer playerInventory, IInventory customInventory) {
        // --- 27 слотов бочки ---
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlotToContainer(
                        new Slot(customInventory, col + row * 9, 8 + col * 18, 18 + row * 18)
                );
            }
        }

        // --- 27 слотов инвентаря игрока (основной) ---
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlotToContainer(
                        new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18)
                );
            }
        }

        // --- 9 слотов хотбара ---
        for (int col = 0; col < 9; ++col) {
            this.addSlotToContainer(
                    new Slot(playerInventory, col, 8 + col * 18, 142)
            );
        }
    }


        @Override
        public boolean canInteractWith(EntityPlayer player) {
            return true;
        }


}
