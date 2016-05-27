package net.infinicraft.parties.commands.party;

import java.util.UUID;
import net.infinicraft.parties.api.PartyAPI;
import net.infinicraft.parties.commands.BasePartyCommand;
import net.infinicraft.parties.commands.SubCompartment;
import net.infinicraft.parties.party.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Disband
        extends SubCompartment<BasePartyCommand>
{
    public Disband()
    {
        super("/party disband", "disband");
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
        if (!party.isLeader(uuid))
        {
            sender.sendMessage(new ComponentBuilder("You are not the leader of this party.").color(ChatColor.RED).create());
            return;
        }
        party.broadcast(new ComponentBuilder(sender.getName() + " has disbanded the party.").color(ChatColor.YELLOW).create());
        party.disband();
    }
}
