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

public class Info
        extends SubCompartment<BasePartyCommand>
{
    public Info()
    {
        super("/party info", "info", "list");
    }

    public void execute(CommandSender sender, String[] args)
    {
        UUID uuid = ((ProxiedPlayer)sender).getUniqueId();
        Party party = PartyAPI.getParty(uuid);
        if (party == null)
        {
            sender.sendMessage(new ComponentBuilder("You are not in a party.").color(ChatColor.RED).create());
            return;
        }
        StringBuilder members = new StringBuilder();
        for (int i = 0; i < party.getMembers().size(); i++) {
            if (i == 0) {
                members.append(Validator.toPlayer((UUID)party.getMembers().toArray()[i]).getName());
            } else {
                members.append(", ").append(Validator.toPlayer((UUID)party.getMembers().toArray()[i]).getName());
            }
        }
        sender.sendMessage(new ComponentBuilder(Validator.toPlayer(party.getLeader()).getName() + "'s party:").color(ChatColor.YELLOW).create());

        sender.sendMessage(new ComponentBuilder("Members: " + members.toString()).color(ChatColor.RED).create());
    }
}
