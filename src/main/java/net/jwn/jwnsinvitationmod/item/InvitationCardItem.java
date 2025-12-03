package net.jwn.jwnsinvitationmod.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.jwn.jwnsinvitationmod.screen.InvitationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;

public class InvitationCardItem extends Item {
    public InvitationCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            Minecraft.getInstance().setScreen(new InvitationScreen());
        } else {
//            MinecraftServer server = level.getServer();
//
//            assert server != null;
//            PlayerList playerList = server.getPlayerList();
//            UserWhiteList whitelist = playerList.getWhiteList();
//
//            CommandSourceStack sourceStack = new CommandSourceStack(
//                    server,                  // CommandSource (서버)
//                    Vec3.ZERO,               // 위치
//                    Vec2.ZERO,               // 시야각
//                    null,                    // ServerLevel (null이면 대부분 콘솔 취급)
//                    4,                       // 권한 레벨 (4 = op 최대)
//                    "Server",                // 이름
//                    Component.literal("Server"), // 표시 이름
//                    server,                  // MinecraftServer
//                    null                     // Entity (없음)
//            );
//
//            String name = "JWN__";
//            String cmd = "whitelist add " + name;
//
//            if (Arrays.asList(whitelist.getUserList()).contains(name)) {
//                player.displayClientMessage(Component.literal("ALREADY IN THE WHITELIST"), false);
//            } else {
//                server.getCommands().performPrefixedCommand(sourceStack, cmd);
//            }
        }
        return InteractionResult.SUCCESS;
    }
}
