package net.jwn.jwnsinvitationmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.jwn.jwnsinvitationmod.setting.InvitationSettingsData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class SetAllowInvitesCommand {
    public SetAllowInvitesCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("gamerule")
            .then(Commands.literal("allowInvites")
            .requires(source -> source.getServer().isDedicatedServer())
            .then(Commands.literal("set")
            .then(Commands.argument("value", BoolArgumentType.bool())
            .executes(ctx -> {
                boolean value = BoolArgumentType.getBool(ctx, "value");
                InvitationSettingsData data = InvitationSettingsData.get(ctx.getSource().getServer());
                data.setAllowInvite(value);
                ctx.getSource().sendSuccess(
                    () -> Component.translatable("message.jwnsinvitationmod.gamerule.set_allow_invite", value ? "true" : "false"),
                    true
                );
                return 1;
            }))))
        );
    }
}
