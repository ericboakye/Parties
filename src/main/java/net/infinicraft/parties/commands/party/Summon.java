package net.infinicraft.parties.commands.party;

import net.infinicraft.parties.Validator;
import net.infinicraft.parties.api.PartyAPI;
import net.infinicraft.parties.commands.BasePartyCommand;
import net.infinicraft.parties.commands.SubCompartment;
import net.infinicraft.parties.party.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class Summon
        extends SubCompartment<BasePartyCommand> {
    public Summon() {
        super("/party summon", "summon", "warp");
    }

    public void execute(CommandSender sender, String[] args) {
        UUID uuid = ((ProxiedPlayer) sender).getUniqueId();
        Party party = PartyAPI.getParty(uuid);
        if (party == null) {
            sender.sendMessage(new ComponentBuilder("You are not in a party.").color(ChatColor.RED).create());
            return;
        }
        if (!party.isLeader(uuid)) {
            sender.sendMessage(new ComponentBuilder("You are not the leader of this party.").color(ChatColor.RED).create());
            return;
        }
        for (ProxiedPlayer player : Validator.toPlayer(party.getMembers())) {
            if (!player.getServer().getInfo().equals(((ProxiedPlayer) sender).getServer().getInfo())) {
                player.connect(((ProxiedPlayer) sender).getServer().getInfo());
            }
        }
        party.broadcast(new ComponentBuilder("All players have been summoned to party leader's server").color(ChatColor.RED).create());
    }
}
