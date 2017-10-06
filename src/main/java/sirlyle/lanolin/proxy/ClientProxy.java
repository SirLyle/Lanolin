package sirlyle.lanolin.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import sirlyle.lanolin.ModItems;
import sirlyle.lanolin.events.EventHandlerClient;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        MinecraftForge.EVENT_BUS.register(new EventHandlerClient());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // ModBlocks.initModels();
        ModItems.initModels();
    }
}