package net.jwn.jwnsinvitationmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.jwn.jwnsinvitationmod.setting.InvitationSettingsData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class GetAllowInvitesCommand {
    public GetAllowInvitesCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("gamerule")
            .then(Commands.literal("allowInvites")
            .then(Commands.literal("get")
            .executes(ctx -> {
                InvitationSettingsData data = InvitationSettingsData.get(ctx.getSource().getServer());
                boolean allowed = data.isAllowInvite();
                ctx.getSource().sendSuccess(
                    () -> Component.translatable("message.jwnsinvitationmod.gamerule.get_allow_invite", allowed ? "true" : "false"),
                    true
                );
                return 1;
            })))
        );
    }
}
