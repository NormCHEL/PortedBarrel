package normchel.portedbarrel.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import normchel.portedbarrel.PortedBarrel;
import normchel.portedbarrel.common.StorageTile;

public class Barrel extends BlockContainer {


    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon topOpenIcon;
    @SideOnly(Side.CLIENT)
    private IIcon bottomIcon;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;
    @SideOnly(Side.CLIENT)
    private IIcon sideTurnedIcon;


    protected Barrel() {
        super(Material.wood);
        setBlockName("barrel");
        setBlockTextureName(PortedBarrel.MOD_ID + ":barrel");
        setHardness(2.5f);
        setResistance(2.5f);
        setStepSound(Block.soundTypeWood);
        setCreativeTab(CreativeTabs.tabDecorations);

    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new StorageTile();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        topIcon = iconRegister.registerIcon(PortedBarrel.MOD_ID+":BarrelTop");
        bottomIcon = iconRegister.registerIcon(PortedBarrel.MOD_ID+":BarrelBottom");
        sideIcon = iconRegister.registerIcon(PortedBarrel.MOD_ID+":BarrelSide");
        sideTurnedIcon = iconRegister.registerIcon(PortedBarrel.MOD_ID+":BarrelSideTurned");
        topOpenIcon = iconRegister.registerIcon(PortedBarrel.MOD_ID+":BarrelTopOpen");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int direction;

        if (Math.abs(placer.rotationPitch) > 60) {
            // Смотрит вверх или вниз
            if (placer.rotationPitch > 0) {
                direction = 0; // вниз
            } else {
                direction = 1; // вверх
            }
        } else {
            // Горизонтальные направления (как обычно)
            int yaw = MathHelper.floor_double((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            switch (yaw) {
                case 0:
                    direction = 3;
                    break; // юг  -  S
                case 1:
                    direction = 4;
                    break; // запад  +  W
                case 2:
                    direction = 2;
                    break; // север  -  N
                case 3:
                    direction = 5;
                    break; // восток  +  E
                default:
                    direction = 2;
                    break;
            }
        }

        world.setBlockMetadataWithNotify(x, y, z, direction, 2);
    }



    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta == 0) { // вниз
            if (side == 0) return bottomIcon; // лицом вниз
            if (side == 1) return topIcon; // зад вверх
            return sideIcon;
        } else if (meta == 1) { // вверх
            if (side == 1) return bottomIcon; // лицом вверх
            if (side == 0) return topIcon; // зад вниз
            return sideIcon;

        } else {
            // Горизонтальное направление
            if (side == meta) return bottomIcon; // лицевая сторона
            if (side == getOpposite(meta)) return topIcon; // задняя сторона
            if (side==0 | side==1 && (meta==4 || meta==5)) return sideTurnedIcon;
            if (side==0 | side==1 && (meta==3 || meta==2)) return sideIcon;
            return sideTurnedIcon;
        }
    }

    private int getOpposite(int side) {
        switch (side) {
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
                return 3;
            case 3:
                return 2;
            case 4:
                return 5;
            case 5:
                return 4;
            default:
                return side;
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                                    EntityPlayer player, int side,
                                    float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(PortedBarrel.instance, 0, world, x, y, z); // ID = 0
        }
        return true;
    }
}

