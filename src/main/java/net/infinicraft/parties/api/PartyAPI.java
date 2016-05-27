package net.infinicraft.parties.api;

import java.util.ArrayList;
import java.util.UUID;
import net.infinicraft.parties.party.Party;
import net.infinicraft.parties.party.StandardParty;

public class PartyAPI
{
    private static ArrayList<Party> parties = new ArrayList<Party>();

    public static StandardParty createStandardParty(UUID leader, UUID... members)
    {
        return new StandardParty(leader, members);
    }

    public static boolean hasParty(UUID user)
    {
        return getParty(user) != null;
    }

    public static Party getParty(UUID user)
    {
        for (Party party : parties) {
            if (party.has(user)) {
                return party;
            }
        }
        return null;
    }

    public static ArrayList<Party> getParties()
    {
        return parties;
    }
}
