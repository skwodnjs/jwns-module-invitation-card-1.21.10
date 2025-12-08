package net.jwn.jwnsinvitationmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClearWhitelistCommand {
    public ClearWhitelistCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("whitelist")
                .requires(source ->
                        source.getServer().isDedicatedServer()
                )
                .then(Commands.literal("clear")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        MinecraftServer server = context.getSource().getServer();
        PlayerList playerList = server.getPlayerList();
        UserWhiteList whitelist = playerList.getWhiteList();

        if (server.isUsingWhitelist()) {
            whitelist.clear();
            context.getSource().sendSuccess(() -> Component.translatable("message.jwnsinvitationmod.whitelist.clear"), true);
        } else {
            context.getSource().sendSuccess(() -> Component.translatable("message.jwnsinvitationmod.whitelist.not_using_whitelist"), true);
        }

        return 1;
    }
}
