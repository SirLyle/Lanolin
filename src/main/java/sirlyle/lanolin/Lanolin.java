package sirlyle.lanolin;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import sirlyle.lanolin.proxy.CommonProxy;
import sirlyle.lanolin.events.EventHandlerCommon;

@Mod(modid = Lanolin.MODID, name = Lanolin.MODNAME, version = Lanolin.MODVERSION, acceptedMinecraftVersions = "[1.12, 1.13)", useMetadata = true)
public class Lanolin {
    public static final String MODID = "lanolin";
    public static final String MODNAME = "Lanolin";
    public static final String MODVERSION = "@VERSION@";


    @SidedProxy(clientSide = "sirlyle.lanolin.proxy.ClientProxy", serverSide = "sirlyle.lanolin.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Lanolin instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
