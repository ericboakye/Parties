package net.infinicraft.parties;

import net.infinicraft.parties.commands.BasePartyCommand;
import net.infinicraft.parties.commands.PartyChatCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class Parties {

    private Plugin plugin;
    public static final long COOLDOWN = 60000L;

    public Parties(Plugin plugin) {
        this.plugin = plugin;

        registerCommand(BasePartyCommand.class);
        registerCommand(PartyChatCommand.class);

        PartyListener listener = new PartyListener();
        this.plugin.getProxy().getPluginManager().registerListener(plugin, listener);
    }

    private void registerCommand(Class<? extends Command> command) {
        try {
            this.plugin.getProxy().getPluginManager().registerCommand(this.plugin, command.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disable() {
    }

    public static BaseComponent[] getRegularChatFormat(String player, String message) {
        return new ComponentBuilder(player).color(ChatColor.BLUE).append(": ").color(ChatColor.YELLOW).append(message).color(ChatColor.WHITE).create();
    }

    public static BaseComponent[] getLeaderChatFormat(String player, String message) {
        return new ComponentBuilder(player).color(ChatColor.AQUA).append(": ").color(ChatColor.YELLOW).append(message).color(ChatColor.WHITE).create();
    }
}
