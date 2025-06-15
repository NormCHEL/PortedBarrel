package normchel.portedbarrel.common.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import normchel.portedbarrel.PortedBarrel;
import normchel.portedbarrel.client.ContainerBarrel;
import org.lwjgl.opengl.GL11;


@SideOnly(Side.CLIENT)
public class GuiBarrel extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation(PortedBarrel.MOD_ID, "textures/gui/BarrelGui.png");
    private final IInventory barrelInventory;


    public GuiBarrel(IInventory playerInventory, IInventory barrelInventory) {
        super(new ContainerBarrel(playerInventory, barrelInventory));
        this.barrelInventory = barrelInventory;
        this.ySize = 166;
        this.xSize = 176;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString(I18n.format(this.barrelInventory.getInventoryName()), 8, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
}

