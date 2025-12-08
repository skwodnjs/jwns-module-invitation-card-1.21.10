package net.jwn.jwnsinvitationmod.networking.packet;

import io.netty.buffer.ByteBuf;
import net.jwn.jwnsinvitationmod.Config;
import net.jwn.jwnsinvitationmod.JWNsMod;
import net.jwn.jwnsinvitationmod.item.ModItems;
import net.jwn.jwnsinvitationmod.setting.InvitationSettingsData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Arrays;
import java.util.UUID;

public record InvitationC2SPacket(String inviter, String invitee) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<InvitationC2SPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "invitation_data"));

    public static final StreamCodec<ByteBuf, InvitationC2SPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, InvitationC2SPacket::inviter,
            ByteBufCodecs.STRING_UTF8, InvitationC2SPacket::invitee,
            InvitationC2SPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final InvitationC2SPacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }

            ServerLevel level = player.level();
            MinecraftServer server = level.getServer();
            InvitationSettingsData invitationSettingsData = InvitationSettingsData.get(server);
            boolean allowed = invitationSettingsData.isAllowInvite();

            if (!level.getServer().isUsingWhitelist()) {
                player.displayClientMessage(Component.translatable("message.jwnsinvitationmod.whitelist.not_using_whitelist"), false);
                return;
            } else if (!allowed) {
                player.displayClientMessage(Component.translatable("message.jwnsinvitationmod.whitelist.inactive"), false);
                return;
            }

            PlayerList playerList = server.getPlayerList();
            UserWhiteList whitelist = playerList.getWhiteList();

            String name = data.invitee();

            if (Arrays.asList(whitelist.getUserList()).contains(name)) {
                player.displayClientMessage(Component.translatable("message.jwnsinvitationmod.whitelist.already_exists", name), false);
            } else {
                UserWhiteListEntry userWhiteListEntry = new UserWhiteListEntry(new NameAndId(
                        UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes()),
                        name
                ));
                whitelist.add(userWhiteListEntry);
                player.displayClientMessage(Component.translatable("message.jwnsinvitationmod.whitelist.added", name), false);

                for (InteractionHand hand : InteractionHand.values()) {
                    ItemStack stack = player.getItemInHand(hand);

                    if (stack.getItem() == ModItems.INVITATION_CARD.get()) {
                        stack.shrink(1);

                        if (stack.isEmpty()) {
                            player.setItemInHand(hand, ItemStack.EMPTY);
                        }

                        break;
                    }
                }
            }
        }).exceptionally(e -> {
            context.disconnect(Component.translatable("jwnsinvitationmod.networking.failed", e.getMessage()));
            return null;
        });
    }
}
