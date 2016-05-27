package net.infinicraft.parties.party;

import java.util.UUID;

public class StandardParty
        extends Party
{
    public StandardParty(UUID leader, UUID... members)
    {
        super(leader, members);
    }
}
