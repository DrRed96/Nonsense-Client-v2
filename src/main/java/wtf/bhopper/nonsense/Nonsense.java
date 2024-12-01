package wtf.bhopper.nonsense;

import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wtf.bhopper.nonsense.alt.AltManager;
import wtf.bhopper.nonsense.command.CommandManager;
import wtf.bhopper.nonsense.config.ConfigManager;
import wtf.bhopper.nonsense.event.bus.EventBus;
import wtf.bhopper.nonsense.gui.hud.Hud;
import wtf.bhopper.nonsense.module.Module;
import wtf.bhopper.nonsense.module.ModuleManager;
import wtf.bhopper.nonsense.module.impl.visual.ClickGui;
import wtf.bhopper.nonsense.util.minecraft.BlinkUtil;
import wtf.bhopper.nonsense.util.minecraft.TickRate;
import wtf.bhopper.nonsense.util.render.Fonts;
import wtf.bhopper.nonsense.util.render.NVGHelper;

import java.io.File;

public enum Nonsense {
    INSTANCE;

    public static final String NAME = "Nonsense";
    public static final String VERSION = "241116";

    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final Gson GSON = new Gson();

    // Event Bus
    private EventBus eventBus;

    // Managers
    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private ConfigManager configManager;
    private AltManager altManager;

    // Render Components
    private Hud hud;

    // Util
    private long startTime;
    private File dataDir;
    private TickRate tickRate;

    public void setup() {
        LOGGER.info("Loading {} {}", NAME, VERSION);
        this.startTime = System.currentTimeMillis();

        this.dataDir = new File(Minecraft.getMinecraft().mcDataDir, NAME);
        if (!this.dataDir.exists()) {
            this.dataDir.mkdirs();
        }

        this.eventBus = new EventBus();
        this.moduleManager = new ModuleManager();
        this.commandManager = new CommandManager();
        this.configManager = new ConfigManager();
        this.altManager = new AltManager();
        this.altManager.tryLoad();

        NVGHelper.init();
        Fonts.init();
        this.hud = new Hud();
        module(ClickGui.class).initGuis();

        this.tickRate = new TickRate();
        BlinkUtil.init();

        this.configManager.loadDefaultConfig();
    }

    public static EventBus getEventBus() {
        return INSTANCE.eventBus;
    }

    public static ModuleManager getModuleManager() {
        return INSTANCE.moduleManager;
    }

    public static <T extends Module> T module(Class<T> clazz) {
        return INSTANCE.moduleManager.get(clazz);
    }

    public static CommandManager getCommandManager() {
        return INSTANCE.commandManager;
    }

    public static ConfigManager getConfigManager() {
        return INSTANCE.configManager;
    }

    public static AltManager getAltManager() {
        return INSTANCE.altManager;
    }

    public static Hud getHud() {
        return INSTANCE.hud;
    }

    public static long getStartTime() {
        return INSTANCE.startTime;
    }

    public static File getDataDir() {
        return INSTANCE.dataDir;
    }

    public static TickRate getTickRate() {
        return INSTANCE.tickRate;
    }

}
