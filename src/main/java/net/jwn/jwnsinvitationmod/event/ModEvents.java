package net.jwn.jwnsinvitationmod.event;

import net.jwn.jwnsinvitationmod.JWNsMod;
import net.jwn.jwnsinvitationmod.command.ClearWhitelistCommand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = JWNsMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new ClearWhitelistCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
