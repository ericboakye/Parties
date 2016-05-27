package net.infinicraft.parties.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public abstract class SubCompartment<T extends Command>
{
    private String[] name;
    private String usage = "unknown usage";

    public SubCompartment(String usage, String... name)
    {
        this.usage = usage;
        this.name = name;
    }

    public abstract void execute(CommandSender paramCommandSender, String[] paramArrayOfString);

    public String[] getNames()
    {
        return this.name;
    }

    public String getUsage()
    {
        return this.usage;
    }
}
