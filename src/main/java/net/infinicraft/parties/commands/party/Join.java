package net.infinicraft.parties.commands.party;

import net.infinicraft.parties.Validator;
import net.infinicraft.parties.api.PartyAPI;
import net.infinicraft.parties.commands.BasePartyCommand;
import net.infinicraft.parties.commands.SubCompartment;
import net.infinicraft.parties.party.Party;
import net.infinicraft.parties.party.PartyInvite;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class Join
        extends SubCompartment<BasePartyCommand> {
    public Join() {
        super("/party join", "join");
    }

    public void execute(CommandSender sender, String[] args) {
        UUID uuid = ((ProxiedPlayer) sender).getUniqueId();
        String inviter = PartyInvite.getInviter(sender.getName());
        PartyInvite invitation = PartyInvite.Cooldown.getCooldown(sender.getName(), inviter);
        if (invitation == null) {
            sender.sendMessage(new ComponentBuilder("You do not have any invitations.").color(ChatColor.RED).create());
            return;
        }
        if (invitation.isOver()) {
            sender.sendMessage(new ComponentBuilder("You do not have any invitations.").color(ChatColor.RED).create());
            return;
        }
        if (!Validator.isOnline(inviter)) {
            sender.sendMessage(new ComponentBuilder("This player is no longer online.").color(ChatColor.RED).create());
            return;
        }
        if (PartyAPI.hasParty(uuid)) {
            sender.sendMessage(new ComponentBuilder("You must leave your party first before you join another party").color(ChatColor.RED).create());
            return;
        }
        Party party = PartyAPI.getParty(Validator.toPlayer(inviter).getUniqueId());
        if ((party != null) && (party.isLeader(Validator.toPlayer(inviter).getUniqueId()))) {
            party.getMembers().add(uuid);
        } else {
            party = PartyAPI.createStandardParty(Validator.toPlayer(inviter).getUniqueId(), uuid);
            Validator.toPlayer(inviter).sendMessage(new ComponentBuilder("You were not in a party so one has been created for you.").color(ChatColor.YELLOW).create());
        }
        party.broadcast(new ComponentBuilder("+" + sender.getName() + " has joined the party!").color(ChatColor.GREEN).create());
    }
}
