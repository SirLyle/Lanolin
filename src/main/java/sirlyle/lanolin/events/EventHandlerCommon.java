package sirlyle.lanolin.events;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sirlyle.lanolin.Config;

public class EventHandlerCommon {
    @SubscribeEvent
    public void updateEntity(LivingEvent.LivingUpdateEvent event){
        if(event.getEntity() instanceof EntityPlayer && event.getEntity().getAir() <= 300 - Config.ARMOR_TICKS_PER_LANOLIN){
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

    @SubscribeEvent
    public void breakSpeed(PlayerEvent.BreakSpeed event){
        // Check if the break speed has been modified due to water
        // prevent the modification if there is lanolin on the tool being used
        // decrement the lanolin when the block is successfully broken
        EntityPlayer player = event.getEntityPlayer();
        IBlockState state = event.getState();
        BlockPos pos = event.getPos();
        if(player.isInsideOfMaterial(Material.WATER)) {
            //Player is inside water, check for lanolin
            if(player.getHeldItemMainhand().hasTagCompound() && player.getHeldItemMainhand().getTagCompound().hasKey("lanolin")){
                // Recalculate ala EntityPlayer.getDigSpeed(), but skip the water portion
                float f = player.inventory.getStrVsBlock(state);

                if (f > 1.0F)
                {
                    int i = EnchantmentHelper.getEfficiencyModifier(player);
                    ItemStack itemstack = player.getHeldItemMainhand();

                    if (i > 0 && !itemstack.isEmpty())
                    {
                        f += (float)(i * i + 1);
                    }
                }

                if (player.isPotionActive(MobEffects.HASTE))
                {
                    f *= 1.0F + (float)(player.getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1) * 0.2F;
                }

                if (player.isPotionActive(MobEffects.MINING_FATIGUE))
                {
                    float f1;

                    switch (player.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier())
                    {
                        case 0:
                            f1 = 0.3F;
                            break;
                        case 1:
                            f1 = 0.09F;
                            break;
                        case 2:
                            f1 = 0.0027F;
                            break;
                        case 3:
                        default:
                            f1 = 8.1E-4F;
                    }

                    f *= f1;
                }
                if(f > event.getNewSpeed()){
                    event.setNewSpeed(f < 0 ? 0 : f);
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event){
        EntityPlayer player = event.getPlayer();
        if(player.isInsideOfMaterial(Material.WATER)) {
            //Player is inside water, check for lanolin
            if (player.getHeldItemMainhand().hasTagCompound() && player.getHeldItemMainhand().getTagCompound().hasKey("lanolin")) {
                int newLanolin = player.getHeldItemMainhand().getTagCompound().getInteger("lanolin") - 1;
                if (newLanolin <= 0)
                    player.getHeldItemMainhand().getTagCompound().removeTag("lanolin");
                else
                    player.getHeldItemMainhand().getTagCompound().setInteger("lanolin", newLanolin);
            }
        }
    }
}
