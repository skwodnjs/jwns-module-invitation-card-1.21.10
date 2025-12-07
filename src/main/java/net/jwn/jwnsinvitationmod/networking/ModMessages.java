package net.jwn.jwnsinvitationmod.networking;

import net.jwn.jwnsinvitationmod.JWNsMod;
import net.jwn.jwnsinvitationmod.networking.packet.InvitationC2SPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = JWNsMod.MOD_ID)
public class ModMessages {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                InvitationC2SPacket.TYPE,
                InvitationC2SPacket.STREAM_CODEC,
                InvitationC2SPacket::handle
        );
    }
}
