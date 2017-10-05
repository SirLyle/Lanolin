package sirlyle.lanolin.events;

import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerCommon {
    @SubscribeEvent
    public void onLanolinTooltip(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty() && stack.getItem() instanceof ItemArmor && stack.hasTagCompound()
                && stack.getTagCompound().hasKey("lanolin")) {
            String levelKey = "tooltip.lanolin.level." + stack.getTagCompound().getByte("lanolin");
            event.getToolTip().add(TextFormatting.DARK_AQUA + "" + TextFormatting.ITALIC +
                    I18n.format("tooltip.lanolin.lanolin") + " " +
                    I18n.format(levelKey));
        }
    }

    @SubscribeEvent
    public void updateEntity(LivingEvent.LivingUpdateEvent event){
        if(event.getEntity() instanceof EntityPlayer && event.getEntity().getAir() <= 280){
            // The entity is a player and is not at full air
            if(event.getEntity().isInsideOfMaterial(Material.WATER)){
                for(int i = 0; i < 4; i++){
                    ItemStack armorPiece = ((EntityPlayer) event.getEntity()).inventory.armorInventory.get(i);
                    if(armorPiece != ItemStack.EMPTY){
                        NBTTagCompound tags = armorPiece.getTagCompound();
                        if(tags != null && tags.hasKey("lanolin")){
                            // Found armor with lanolin. Reset air and remove a piece of lanolin.
                            event.getEntity().setAir(300);
                            if(tags.getInteger("lanolin") == 1){
                                // No more lanolin on this item, remove the tag
                                tags.removeTag("lanolin");
                            }
                            else{
                                tags.setInteger("lanolin", tags.getInteger("lanolin")-1);
                            }
                            armorPiece.setTagCompound(tags);
                            ((EntityPlayer) event.getEntity()).inventory.armorInventory.set(i, armorPiece);
                            break;
                        }
                    }
                }
            }
        }
    }
}
