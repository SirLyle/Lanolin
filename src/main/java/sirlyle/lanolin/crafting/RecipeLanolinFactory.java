package sirlyle.lanolin.crafting;

import com.google.gson.JsonObject;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.Level;
import sirlyle.lanolin.Config;
import sirlyle.lanolin.Lanolin;
import sirlyle.lanolin.ModItems;
import sirlyle.lanolin.items.ItemLanolin;

import javax.annotation.Nonnull;

import static net.minecraft.util.math.MathHelper.clamp;

public class RecipeLanolinFactory implements IRecipeFactory{
    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        Lanolin.logger.log(Level.DEBUG,"Loaded Lanolin Crafting");
        return new RecipeLanolin();
    }
    public static class RecipeLanolin extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
        public RecipeLanolin(){

        }

        @Override
        @Nonnull
        public ItemStack getCraftingResult(InventoryCrafting inv) {
            int lanolinCount = 0;
            ItemStack craftStack = null;
            for(int i = 0; i < inv.getSizeInventory(); i++){
                ItemStack tempStack = inv.getStackInSlot(i);
                if(tempStack.getItem().getRegistryName().equals(ModItems.itemLanolin.getRegistryName()))
                    lanolinCount++;
                else if(ItemLanolin.canCraftWith(tempStack) && craftStack == null) {
                    craftStack = tempStack.copy();
                }
                else if(tempStack != ItemStack.EMPTY)
                    return ItemStack.EMPTY;
            }
            if (craftStack == ItemStack.EMPTY || !ItemLanolin.canCraftWith(craftStack)) {
                return ItemStack.EMPTY;
            }
            // Copy Existing NBT
            if(craftStack.hasTagCompound()) {
                if(craftStack.getTagCompound().hasKey("lanolin")){
                    // Increase existing lanolin count
                    lanolinCount += craftStack.getTagCompound().getInteger("lanolin");
                }
            }
            if(craftStack.getItem() instanceof ItemArmor)
                craftStack.setTagInfo("lanolin", new NBTTagByte((byte) clamp(lanolinCount,0, Config.MAX_LANOLIN_ARMOR)));
            else if(craftStack.getItem() instanceof ItemTool)
                craftStack.setTagInfo("lanolin", new NBTTagByte((byte) clamp(lanolinCount,0, Config.MAX_LANOLIN_TOOLS)));
            else // Unconfigured item, that passed
                craftStack.setTagInfo("lanolin", new NBTTagByte((byte) clamp(lanolinCount,0, 15)));
            return craftStack;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return ItemStack.EMPTY;
        }

        public boolean isHidden()
        {
            return true;
        }

        @Override
        public boolean canFit(int width, int height) {
            return width * height >= 2;
        }

        @Override
        public boolean matches(InventoryCrafting inv, World world){
            int lanolinCount = 0;
            int craftCount = 0;
            for(int i = 0; i < inv.getSizeInventory(); i++){
                ItemStack tempStack = inv.getStackInSlot(i);
                if(tempStack.getItem() instanceof ItemLanolin)
                    lanolinCount++;
                else if(ItemLanolin.canCraftWith(tempStack)) {
                    craftCount++;
                    if(craftCount > 1){
                        return false;
                    }
                }
                else if(tempStack != ItemStack.EMPTY)
                    return false;
            }
            return lanolinCount > 0 && craftCount == 1;
        }

    }
}
