package net.jwn.jwnsinvitationmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class SetAllowInvitesCommand {
    public SetAllowInvitesCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("gamerule")
                        .then(Commands.literal("allowInvites")
                                .then(Commands.argument("value", BoolArgumentType.bool())
                                        .executes(ctx -> {
                                            boolean value = BoolArgumentType.getBool(ctx, "value");
                                            // holy
                                            ctx.getSource().sendSuccess(
                                                    () -> Component.translatable("message.jwnsinvitationmod.gamerule.allow_invite", value ? "true" : "false"),
                                                    true
                                            );
                                            return 1;
                                        })
                                )
                        )
        );
    }
}
