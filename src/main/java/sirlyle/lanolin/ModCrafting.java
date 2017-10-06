package sirlyle.lanolin;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class ModCrafting extends GameRegistry{
    public static void registerSmelting(){
        // Register a wool smelting recipe for each meta value
        for(int meta=0;meta<16;meta++)
            FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(Blocks.WOOL, 1, meta), new ItemStack(ModItems.itemLanolin, Config.LANOLIN_PER_SMELTING, 0), 0.35f);
    }
    public static void registerCrafting(){

    }

}
