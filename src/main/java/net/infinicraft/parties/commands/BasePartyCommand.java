package net.infinicraft.parties.commands;

import net.infinicraft.parties.Validator;
import net.infinicraft.parties.api.PartyAPI;
import net.infinicraft.parties.commands.party.*;
import net.infinicraft.parties.party.PartyInvite;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class BasePartyCommand
        extends Command
        implements SubExecutor {
    public BasePartyCommand() {
        super("party", null, new String[]{"p"});
        compartments.add(Invite.class);
        compartments.add(Join.class);
        compartments.add(Leave.class);
        compartments.add(Info.class);
        compartments.add(Summon.class);
        compartments.add(Chat.class);
        compartments.add(Promote.class);
        compartments.add(Kick.class);
        compartments.add(Disband.class);
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
        if (Validator.toPlayer(args[0]) != null) {
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
                PartyInvite.Cooldown.addCooldown(target, sender.getName(), 60000L);
                sender.sendMessage(new ComponentBuilder("Your party invitation has been sent!").color(ChatColor.YELLOW).create());
                Validator.toPlayer(target).sendMessage(new ComponentBuilder(Validator.toPlayer(uuid).getName() + " has invited you to a party!").color(ChatColor.YELLOW).create());
                Validator.toPlayer(target).sendMessage(new ComponentBuilder("If you want to join type /party join to accept the invitation.").color(ChatColor.YELLOW).create());
                PartyInvite.createInvitation(target, sender.getName());
                return;
            }
            Validator.toPlayer(uuid).sendMessage(new ComponentBuilder("Please wait " + invitation.getTimeLeft() / 1000 + " seconds for last invitation to expire ..").color(ChatColor.RED).create());
            return;
        }
        ArrayList<String> a = new ArrayList(Arrays.asList(args));
        a.remove(0);
        for (Class<? extends SubCompartment> cs : compartments) {
            try {
                SubCompartment compartment = (SubCompartment) cs.newInstance();
                if (Validator.validateCommand(compartment, args[0])) {
                    compartment.execute(sender, (String[]) a.toArray(new String[a.size()]));
                    return;
                }
            } catch (Exception ignored) {
            }
        }
        Validator.helper(sender);
    }
}
