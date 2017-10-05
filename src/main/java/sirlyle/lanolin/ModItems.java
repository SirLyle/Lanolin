package sirlyle.lanolin;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import sirlyle.lanolin.items.ItemLanolin;

public class ModItems {
    @GameRegistry.ObjectHolder("lanolin:lanolin")
    public static ItemLanolin itemLanolin;

    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(new ItemLanolin());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemLanolin.initModel();
    }
}
