package net.infinicraft.parties.commands.party;

import java.util.Set;
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

public class Promote
        extends SubCompartment<BasePartyCommand> {
    public Promote() {
        super("/party promote <player>", "promote");
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(getUsage());
            return;
        }
        if (Validator.toPlayer(args[0]) == null) {
            sender.sendMessage(getUsage());
            return;
        }
        UUID uuid = ((ProxiedPlayer) sender).getUniqueId();
        UUID target = Validator.toPlayer(args[0]).getUniqueId();
        Party party = PartyAPI.getParty(uuid);
        if (party == null) {
            sender.sendMessage(new ComponentBuilder("You are not in a party.").color(ChatColor.RED).create());
            return;
        }
        if (!party.isLeader(uuid)) {
            sender.sendMessage(new ComponentBuilder("You are not the leader of this party.").color(ChatColor.RED).create());
            return;
        }
        if (!party.getMembers().contains(target)) {
            sender.sendMessage(new ComponentBuilder(args[0] + " is not in your party.").color(ChatColor.RED).create());
            return;
        }
        party.setLeader(target);
        party.getMembers().remove(target);

        party.broadcast(new ComponentBuilder(Validator.toPlayer(args[0]).getName() + " is now the new party leader.").color(ChatColor.YELLOW).create());
    }
}
