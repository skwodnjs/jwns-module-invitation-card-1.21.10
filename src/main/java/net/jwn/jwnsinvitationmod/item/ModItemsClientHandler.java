package net.jwn.jwnsinvitationmod.item;

import net.jwn.jwnsinvitationmod.JWNsMod;
import net.jwn.jwnsinvitationmod.screen.InvitationScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = JWNsMod.MOD_ID, value = Dist.CLIENT)
public class ModItemsClientHandler {
    public static void openInvitationScreen() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        minecraft.setScreen(new InvitationScreen());
    }
}
