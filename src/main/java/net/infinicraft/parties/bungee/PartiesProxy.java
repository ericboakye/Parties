package net.infinicraft.parties.bungee;

import net.infinicraft.parties.Parties;
import net.md_5.bungee.api.plugin.Plugin;

public class PartiesProxy
        extends Plugin
{
    private Parties controller;

    public void onEnable()
    {
        this.controller = new Parties(this);
    }

    public void onDisable()
    {
        this.controller.disable();
    }
}
