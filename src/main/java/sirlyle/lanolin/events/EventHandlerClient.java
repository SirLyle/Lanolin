package sirlyle.lanolin.events;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sirlyle.lanolin.items.ItemLanolin;

public class EventHandlerClient {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onLanolinTooltip(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty() && ItemLanolin.canCraftWith(stack) && stack.hasTagCompound()
                && stack.getTagCompound().hasKey("lanolin")) {
            String levelKey = "tooltip.lanolin.level." + stack.getTagCompound().getByte("lanolin");
            event.getToolTip().add(TextFormatting.DARK_AQUA + "" + TextFormatting.ITALIC +
                    I18n.format("tooltip.lanolin.lanolin") + " " +
                    I18n.format(levelKey));
        }
    }
}
