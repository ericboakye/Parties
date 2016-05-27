package net.infinicraft.parties.commands.party;

import java.util.UUID;

import net.infinicraft.parties.Validator;
import net.infinicraft.parties.api.PartyAPI;
import net.infinicraft.parties.commands.BasePartyCommand;
import net.infinicraft.parties.commands.SubCompartment;
import net.infinicraft.parties.party.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Leave
        extends SubCompartment<BasePartyCommand> {
    public Leave() {
        super("/party leave", "leave");
    }

    public void execute(CommandSender sender, String[] args) {
        UUID uuid = ((ProxiedPlayer) sender).getUniqueId();
        Party party = PartyAPI.getParty(uuid);
        if (party == null) {
            sender.sendMessage(new ComponentBuilder("You are not in a party.").color(ChatColor.RED).create());
            return;
        }
        if (party.isLeader(uuid)) {
            UUID newLeader = (UUID) party.getMembers().toArray()[0];

            party.setLeader(newLeader);
            party.broadcast(new ComponentBuilder(sender.getName() + " has left the party.").color(ChatColor.YELLOW).create());
            party.broadcast(new ComponentBuilder(Validator.toPlayer(newLeader) + " is now the new party leader.").color(ChatColor.YELLOW).create());
        } else {
            party.getMembers().remove(uuid);
            party.broadcast(new ComponentBuilder(sender.getName() + " has left the party.").color(ChatColor.YELLOW).create());
        }
        sender.sendMessage(new ComponentBuilder("You have left the party.").color(ChatColor.YELLOW).create());
        if (party.getMembers().size() <= 1)
            party.disband();
    }
}
