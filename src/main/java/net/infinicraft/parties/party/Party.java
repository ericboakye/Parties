package net.infinicraft.parties.party;

import net.infinicraft.parties.Validator;
import net.infinicraft.parties.api.PartyAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

public abstract class Party {

    private UUID leader;
    private ArrayList<UUID> members = new ArrayList<UUID>();

    protected Party(UUID leader, UUID... members) {
        this.leader = leader;
        this.members.addAll(Arrays.asList(members));
        PartyAPI.getParties().add(this);
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public UUID getLeader() {
        return this.leader;
    }

    public ArrayList<UUID> getMembers() {
        return this.members;
    }

    public Server getLeaderCurrentServer(){
        return Validator.toPlayer(leader).getServer();
    }

    public boolean has(UUID member) {
        return (this.members.contains(member)) || (this.leader.equals(member));
    }

    public boolean isLeader(UUID leader) {
        return this.leader.equals(leader);
    }

    public void broadcast(BaseComponent[] component) {
        if (Validator.isOnline(this.leader)) {
            Validator.toPlayer(this.leader).sendMessage(component);
        }
        UUID member;
        for (Iterator i$ = this.members.iterator(); i$.hasNext(); Validator.toPlayer(member).sendMessage(component)) {
            member = (UUID) i$.next();
            if ((!Validator.isOnline(member)) || (this.leader == member)) {
            }
        }
    }

    public void disband() {
        this.leader = null;
        this.members.clear();
        PartyAPI.getParties().remove(this);
    }
}
