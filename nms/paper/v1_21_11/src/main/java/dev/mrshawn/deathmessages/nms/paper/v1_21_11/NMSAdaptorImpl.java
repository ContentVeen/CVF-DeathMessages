package dev.mrshawn.deathmessages.nms.paper.v1_21_11;

import org.bukkit.GameRules;
import org.bukkit.World;

public class NMSAdaptorImpl extends dev.mrshawn.deathmessages.nms.paper.v1_21_4.NMSAdaptorImpl {

    // GameRule.SHOW_DEATH_MESSAGES is deprecated and marked for removal since 1.21.11
    @Override
    public boolean showDeathMessages(World world) {
        return Boolean.TRUE.equals(world.getGameRuleValue(GameRules.SHOW_DEATH_MESSAGES));
    }

    @Override
    public void showDeathMessages(World world, boolean show) {
        world.setGameRule(GameRules.SHOW_DEATH_MESSAGES, show);
    }
}
