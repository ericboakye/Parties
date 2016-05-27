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

public class Kick
        extends SubCompartment<BasePartyCommand>
{
    public Kick()
    {
        super("/party kick <player>", "kick", "remove");
    }

    public void execute(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(getUsage());
            return;
        }
        if (Validator.toPlayer(args[0]) == null)
        {
            sender.sendMessage(getUsage());
            return;
        }
        UUID uuid = ((ProxiedPlayer)sender).getUniqueId();
        UUID target = Validator.toPlayer(args[0]).getUniqueId();
        Party party = PartyAPI.getParty(uuid);
        if (party == null)
        {
            sender.sendMessage(new ComponentBuilder("You are not in a party.").color(ChatColor.RED).create());
            return;
        }
        if (!party.isLeader(uuid))
        {
            sender.sendMessage(new ComponentBuilder("You are not the leader of this party.").color(ChatColor.RED).create());
            return;
        }
        if (!party.getMembers().contains(target))
        {
            sender.sendMessage(new ComponentBuilder(args[0] + " is not in your party.").color(ChatColor.RED).create());
            return;
        }
        party.broadcast(new ComponentBuilder(Validator.toPlayer(args[0]).getName() + " has kicked from the party.").color(ChatColor.RED).create());
        party.getMembers().remove(target);
        if (party.getMembers().size() > 1)
        {
            party.broadcast(new ComponentBuilder("There are no more players in your party so, This party is no longer valid.").color(ChatColor.RED).create());
            Validator.toPlayer(args[0]).sendMessage(new ComponentBuilder("There were no more players in that party so, It is has been disbanded.").color(ChatColor.RED).create());
            party.disband();
        }
    }
}
