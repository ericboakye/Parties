package net.infinicraft.parties.party;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PartyInvite {
    private long startTime;
    private String String;
    private String cooldown;
    private long lengthInMillis;
    private long endTime;
    private static HashMap<String, String> invites = new HashMap<String, String>();

    public static String getInviter(String target) {
        return invites.get(target);
    }

    public static String createInvitation(String target, String other) {
        return invites.put(target, other);
    }

    public PartyInvite(String cooldown, String String, long lengthInMillis) {
        this.String = String;
        this.cooldown = cooldown;
        this.startTime = System.currentTimeMillis();
        this.lengthInMillis = lengthInMillis;
        this.endTime = (this.startTime + this.lengthInMillis);
    }

    public static class Cooldown {
        private static Set<PartyInvite> cooldowns = new HashSet<PartyInvite>();

        public static void addCooldown(String cooldown, String String, long lengthInMillis) {
            PartyInvite pi = new PartyInvite(cooldown, String, lengthInMillis);

            Iterator it = cooldowns.iterator();
            while (it.hasNext()) {
                PartyInvite iterated = (PartyInvite) it.next();
                if ((iterated.getString().equals(pi.getString())) && (iterated.getCooldownString().equals(pi.getCooldownString()))) {
                    it.remove();
                }
            }
            cooldowns.add(pi);
        }

        public static PartyInvite getCooldown(String cooldown, String String) {
            for (PartyInvite pi : cooldowns) {
                if ((pi.getCooldownString().equals(cooldown)) && (pi.getString().equals(String))) {
                    return pi;
                }
            }
            return null;
        }
    }

    public boolean isOver() {
        return this.endTime < System.currentTimeMillis();
    }

    public int getTimeLeft() {
        return (int) (this.endTime - System.currentTimeMillis());
    }

    public String getString() {
        return this.String;
    }

    public String getCooldownString() {
        return this.cooldown;
    }

    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.endTime = (this.startTime + this.lengthInMillis);
    }
}
