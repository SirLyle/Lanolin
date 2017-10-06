package sirlyle.lanolin;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import sirlyle.lanolin.proxy.CommonProxy;

import java.util.ArrayList;

public class Config {
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_LIMITS = "limits";

    // List of item registry names to not allow crafting on
    public static ArrayList<String> LANOLIN_BLACKLIST;

    // Maximum Water Resistance modifiers
    public static boolean ENABLE_ARMOR = true;
    public static boolean ENABLE_TOOLS = true;

    // Maximum Water Resistance modifiers
    public static int MAX_LANOLIN_ARMOR = 15;
    public static int MAX_LANOLIN_TOOLS = 15;

    // Misc. Lanolin behaviors
    public static int LANOLIN_PER_SMELTING = 1;
    public static int ARMOR_TICKS_PER_LANOLIN = 20;


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

        ENABLE_ARMOR = cfg.getBoolean("enableArmor", CATEGORY_GENERAL, ENABLE_ARMOR, "Allow Lanolin to be applied to armor.");
        ENABLE_TOOLS = cfg.getBoolean("enableTools", CATEGORY_GENERAL, ENABLE_TOOLS, "Allow Lanolin to be applied to tools.");

        ARMOR_TICKS_PER_LANOLIN = cfg.getInt("armorTicks", CATEGORY_GENERAL, ARMOR_TICKS_PER_LANOLIN, 1, 300, "Ticks before Lanolin resets the Oxygen Meter.");
        LANOLIN_PER_SMELTING = cfg.getInt("lanolinSmelting", CATEGORY_GENERAL, LANOLIN_PER_SMELTING, 1, 64, "Ticks before Lanolin resets the Oxygen Meter.");

        MAX_LANOLIN_ARMOR = cfg.getInt("armorLimit", CATEGORY_LIMITS, MAX_LANOLIN_ARMOR, 1, 1024, "Maximum Water Resistance modifier for armor.");
        MAX_LANOLIN_TOOLS = cfg.getInt("toolsLimit", CATEGORY_LIMITS, MAX_LANOLIN_TOOLS, 1, 1024, "Maximum Water Resistance modifier for tools.");



    }
}