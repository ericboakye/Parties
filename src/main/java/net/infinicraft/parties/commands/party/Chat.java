package net.infinicraft.parties.commands.party;

import java.util.UUID;

import net.infinicraft.parties.Parties;
import net.infinicraft.parties.api.PartyAPI;
import net.infinicraft.parties.commands.BasePartyCommand;
import net.infinicraft.parties.commands.SubCompartment;
import net.infinicraft.parties.party.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Chat
        extends SubCompartment<BasePartyCommand> {
    public Chat() {
        super("/party chat <message>", "chat");
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(getUsage());
            return;
        }
        UUID uuid = ((ProxiedPlayer) sender).getUniqueId();
        Party party = PartyAPI.getParty(uuid);
        String name = sender.getName();
        if (party == null) {
            sender.sendMessage(new ComponentBuilder("You are not in a party.").color(ChatColor.RED).create());
            return;
        }
        StringBuilder message = new StringBuilder(args[0]);
        for (int arg = 1; arg < args.length; arg++) {
            message.append(" ").append(args[arg]);
        }
        party.broadcast(party.isLeader(uuid) ? Parties.getLeaderChatFormat(name, message.toString()) : Parties.getRegularChatFormat(name, message.toString()));
    }
}
