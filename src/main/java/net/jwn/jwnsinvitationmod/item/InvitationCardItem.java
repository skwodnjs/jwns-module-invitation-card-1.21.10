package net.jwn.jwnsinvitationmod.item;

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

import java.util.Arrays;
import java.util.UUID;

public class InvitationCardItem extends Item {
    public InvitationCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            ModItemsClientHandler.openInvitationScreen();
        } else {
            MinecraftServer server = level.getServer();

            assert server != null;
            PlayerList playerList = server.getPlayerList();
            UserWhiteList whitelist = playerList.getWhiteList();

            String name = "JWN__";

            if (Arrays.asList(whitelist.getUserList()).contains(name)) {
                player.displayClientMessage(Component.literal("ALREADY IN THE WHITELIST"), false);
            } else {
                UserWhiteListEntry userWhiteListEntry = new UserWhiteListEntry(new NameAndId(
                        UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes()),
                        name
                ));
                whitelist.add(userWhiteListEntry);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
