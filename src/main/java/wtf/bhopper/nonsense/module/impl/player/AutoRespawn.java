package wtf.bhopper.nonsense.module.impl.player;

import net.minecraft.client.gui.GuiGameOver;
import wtf.bhopper.nonsense.event.bus.EventLink;
import wtf.bhopper.nonsense.event.bus.Listener;
import wtf.bhopper.nonsense.event.impl.EventTick;
import wtf.bhopper.nonsense.module.Module;
import wtf.bhopper.nonsense.module.ModuleCategory;
import wtf.bhopper.nonsense.module.ModuleInfo;
import wtf.bhopper.nonsense.util.minecraft.PlayerUtil;

@ModuleInfo(name = "Auto Respawn", description = "Automatically respawns you upon death.", category = ModuleCategory.PLAYER)
public class AutoRespawn extends Module {

    @EventLink
    public final Listener<EventTick> onTick = event -> {
        if (PlayerUtil.canUpdate() && mc.currentScreen instanceof GuiGameOver) {
            this.mc.thePlayer.respawnPlayer();
            this.mc.displayGuiScreen(null);
        }
    };

}