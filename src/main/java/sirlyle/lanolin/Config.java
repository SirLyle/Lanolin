package sirlyle.lanolin;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import sirlyle.lanolin.proxy.CommonProxy;

import java.util.ArrayList;

public class Config {
    private static final String CATEGORY_GENERAL = "general";
    public static ArrayList<String> LANOLIN_BLACKLIST;
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            Lanolin.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }
    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");

    }
}