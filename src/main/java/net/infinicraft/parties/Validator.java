package net.infinicraft.parties;

import java.util.*;

import net.infinicraft.parties.commands.SubCompartment;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Validator {
    public static ProxiedPlayer toPlayer(UUID uuid) {
        return ProxyServer.getInstance().getPlayer(uuid);
    }

    public static ProxiedPlayer toPlayer(String name) {
        return ProxyServer.getInstance().getPlayer(name);
    }

    public static ArrayList<ProxiedPlayer> toPlayer(ArrayList<UUID> uuids) {
        ArrayList<ProxiedPlayer> players = new ArrayList<ProxiedPlayer>();
        UUID uuid;
        for (Iterator i$ = uuids.iterator(); i$.hasNext(); players.add(toPlayer(uuid))) {
            uuid = (UUID) i$.next();
        }
        return players;
    }

    public static boolean isOnline(UUID uuid) {
        return ProxyServer.getInstance().getPlayer(uuid) != null;
    }

    public static boolean isOnline(String uuid) {
        return ProxyServer.getInstance().getPlayer(uuid) != null;
    }

    public static boolean validateCommand(SubCompartment compartment, String argument) {
        for (String name : compartment.getNames()) {
            if (name.equalsIgnoreCase(argument)) {
                return true;
            }
        }
        return false;
    }

    public static void helper(CommandSender sender) {
        sender.sendMessage(new ComponentBuilder("-------------------------------------").color(ChatColor.GOLD).create());
        sender.sendMessage(new ComponentBuilder("/p invite <player> - Invite someone to your party.").color(ChatColor.RED).create());
        sender.sendMessage(new ComponentBuilder("/p accept,join - Accept someone's party invite.").color(ChatColor.RED).create());
        sender.sendMessage(new ComponentBuilder("/p leave - Leave the party you are currently in.").color(ChatColor.RED).create());
        sender.sendMessage(new ComponentBuilder("/p kick,remove <player> - Kick a player in your party.").color(ChatColor.RED).create());
        sender.sendMessage(new ComponentBuilder("/p summon,warp - Sends all the players in your party to your server.").color(ChatColor.RED).create());
        sender.sendMessage(new ComponentBuilder("/p promote <player> - Make someone else the party leader.").color(ChatColor.RED).create());
        sender.sendMessage(new ComponentBuilder("/p chat <message> - Send a message to all players in your party.").color(ChatColor.RED).create());
        sender.sendMessage(new ComponentBuilder("/pchat <message> - Send a message to all players in your party.").color(ChatColor.RED).create());
        sender.sendMessage(new ComponentBuilder("-------------------------------------").color(ChatColor.GOLD).create());
    }
}
