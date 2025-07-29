package normchel.portedbarrel.common.block;



import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;

import net.minecraft.block.material.Material;


import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import normchel.portedbarrel.PortedBarrel;

import normchel.portedbarrel.common.tileentities.TileEntityBarrel;

public class Barrel extends Block {

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
    public static final int GUI_BARREL = 0;


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
            if (side == 1) return topIcon;
            if (side == 0) return bottomIcon;
            return sideIcon;
        } else if (meta == 1) { // вверх
            if (side == 0) return topIcon; // лицом вверх
            if (side == 1) return bottomIcon; // зад вниз
            return sideIcon;
        } else {
            // Горизонтальное направление
            if (side == meta) return bottomIcon; // лицевая сторона
            if (side == getOpposite(meta)) return topIcon; // задняя сторона


            if (side==1 && (meta==2 || meta==3)){
                return sideIcon;
            } else if (side==0 && (meta==4 || meta==5)) {
                return sideTurnedIcon;
            } else if (side==0 && (meta==2 || meta==3)) {
                return sideIcon;
            }
            else return sideTurnedIcon;
        }
    }

    private int getOpposite(int side) {
        switch (side) {
            case 0: return 1;
            case 1: return 0;
            case 2: return 3;
            case 3: return 2;
            case 4: return 5;
            case 5: return 4;
            default: return side;
        }
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
                                    int side, float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {
            player.openGui(PortedBarrel.instance, PortedBarrel.GUI_BARREL, world, x, y, z);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof IInventory) {
            IInventory inv = (IInventory) tile;

            for (int i = 0; i < inv.getSizeInventory(); ++i) {
                ItemStack itemstack = inv.getStackInSlot(i);

                if (itemstack != null) {
                    float f = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int j = world.rand.nextInt(21) + 10;

                        if (j > itemstack.stackSize) {
                            j = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j;
                        ItemStack dropped = new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage());

                        if (itemstack.hasTagCompound()) {
                            dropped.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }

                        EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, dropped);
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }

            world.func_147453_f(x, y, z, block); // уведомление соседям
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }
    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileEntityBarrel();
    }
}

