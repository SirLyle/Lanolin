package sirlyle.lanolin.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sirlyle.lanolin.Lanolin;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLanolin extends Item {
    public ItemLanolin() {
        setRegistryName("lanolin");
        setUnlocalizedName(Lanolin.MODID + ".lanolin");
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        tooltip.add(getToolTipData());
    }

    private String getToolTipData(){
        return  I18n.format("item.lanolin.lanolin.tooltip");
    }



    public static boolean canCraftWith(ItemStack itemStack){
        return canCraftWith(itemStack.getItem());
    }
    public static boolean canCraftWith(Item item){
        //TODO Implement a black list here.
        return item instanceof ItemArmor || item instanceof ItemTool;
    }

}
