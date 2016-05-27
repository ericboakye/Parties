package net.infinicraft.parties.commands;

import java.util.ArrayList;

public interface SubExecutor {
    ArrayList<Class<? extends SubCompartment>> compartments = new ArrayList();
}
