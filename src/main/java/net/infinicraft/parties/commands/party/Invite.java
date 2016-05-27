package net.infinicraft.parties.commands.party;

import net.infinicraft.parties.Validator;
import net.infinicraft.parties.api.PartyAPI;
import net.infinicraft.parties.commands.BasePartyCommand;
import net.infinicraft.parties.commands.SubCompartment;
import net.infinicraft.parties.party.PartyInvite;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Chat;

import java.util.UUID;

public class Invite
        extends SubCompartment<BasePartyCommand> {
    public Invite() {
        super("/party invite <player>", "invite", "accept");
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }
        if (args.length == 0) {
            sender.sendMessage(getUsage());
            return;
        }

        UUID uuid = ((ProxiedPlayer) sender).getUniqueId();
        String target = args[0];
        if (target.equals(uuid)) {
            sender.sendMessage(new ComponentBuilder("You cannot invite yourself.").color(ChatColor.RED).create());
            return;
        }
        if ((PartyAPI.hasParty(uuid)) && (!PartyAPI.getParty(uuid).isLeader(uuid))) {
            sender.sendMessage(new ComponentBuilder("You must be the party leader to invite players to your party otherwise leave this party.").color(ChatColor.RED).create());
            return;
        }
        PartyInvite invitation = PartyInvite.Cooldown.getCooldown(target, sender.getName());
        if ((invitation == null) || (invitation.isOver())) {
            String jsoncode = "{text:\"\",extra:[{text:\"You have been invited to a party by " + sender.getName() + "\",color:\"yellow\"},{text:\"\\nClick here to join the party\",color:\"aqua\",clickEvent:{action:\"run_command\",value:\"/p accept\"},hoverEvent:{action:\"show_text\",value:\"Join the party\",color:\"byFabriyo98\"}}]}";
            PartyInvite.Cooldown.addCooldown(target, sender.getName(), 60000L);
            sender.sendMessage(new ComponentBuilder("Your party invitation has been sent!").color(ChatColor.YELLOW).create());

            Validator.toPlayer(target).unsafe().sendPacket(new Chat(jsoncode));
            //Validator.toPlayer(target).sendMessage(new ComponentBuilder(Validator.toPlayer(uuid).getName() + " has invited you to a party!").color(ChatColor.YELLOW).create());
            Validator.toPlayer(target).sendMessage(new ComponentBuilder("If you want to join type /party join to accept the invitation.").color(ChatColor.YELLOW).create());
            PartyInvite.createInvitation(target, sender.getName());
            return;
        }
        Validator.toPlayer(uuid).sendMessage(new ComponentBuilder("Please wait " + invitation.getTimeLeft() / 1000 + " seconds for last invitation to expire ..").color(ChatColor.RED).create());
    }
}
