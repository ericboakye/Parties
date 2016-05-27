package net.infinicraft.parties;

import net.infinicraft.parties.api.PartyAPI;
import net.infinicraft.parties.party.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class PartyListener
        implements Listener {
    @EventHandler
    public void change(ServerConnectEvent event) {
        Party party = PartyAPI.getParty(event.getPlayer().getUniqueId());
        if (party == null) return;
        if (party.isLeader(event.getPlayer().getUniqueId()) || event.getTarget() == party.getLeaderCurrentServer().getInfo())
            return;
        event.getPlayer().sendMessage(new ComponentBuilder("Type /party leave to connect to this server").color(ChatColor.RED).create());
        event.setTarget(event.getPlayer().getServer().getInfo());
        event.setCancelled(true);
    }

    @EventHandler
    public void leave(PlayerDisconnectEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        Party party = PartyAPI.getParty(uuid);
        if (party == null) {
            return;
        }
        if (party.isLeader(uuid)) {
            UUID leader = (UUID) party.getMembers().toArray()[0];

            party.setLeader(leader);
            party.broadcast(new ComponentBuilder(event.getPlayer().getName() + " has left the party.").color(ChatColor.YELLOW).create());
            party.broadcast(new ComponentBuilder(Validator.toPlayer(leader) + " is now the new party leader.").color(ChatColor.YELLOW).create());
        } else {
            party.getMembers().remove(uuid);
            party.broadcast(new ComponentBuilder(event.getPlayer().getName() + " has left the party.").color(ChatColor.YELLOW).create());
        }
        if (party.getMembers().size() <= 1)
            party.disband();
    }
}
