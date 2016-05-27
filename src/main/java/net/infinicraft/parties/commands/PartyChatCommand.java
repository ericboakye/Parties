package net.infinicraft.parties.commands;

import java.io.PrintStream;
import java.util.UUID;

import net.infinicraft.parties.Parties;
import net.infinicraft.parties.Validator;
import net.infinicraft.parties.api.PartyAPI;
import net.infinicraft.parties.party.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyChatCommand
        extends Command
        implements SubExecutor {
    public PartyChatCommand() {
        super("partychat", null, "pchat");
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            System.out.println("Dont.");
            return;
        }
        if (args.length == 0) {
            Validator.helper(sender);
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
