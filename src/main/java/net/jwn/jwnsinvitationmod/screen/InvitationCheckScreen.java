package net.jwn.jwnsinvitationmod.screen;

import net.jwn.jwnsinvitationmod.JWNsMod;
import net.jwn.jwnsinvitationmod.networking.packet.InvitationC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.List;

public class InvitationCheckScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "textures/gui/invitation.png");
    private static final ResourceLocation BUTTON = ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "button2");
    private static final ResourceLocation BUTTON_PRESSED = ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "button2_highlighted");

    private static String name;

    private static final int IMAGE_WIDTH = 256;
    private static final int IMAGE_HEIGHT = 256;
    private static final int DRAW_WIDTH = 140;
    private static final int DRAW_HEIGHT = 90;

    private static final int BUTTON_WIDTH = 37;
    private static final int BUTTON_HEIGHT = 15;

    int x;
    int y;
    int button_x;
    int button_y;

    public InvitationCheckScreen(String name) {
        super(Component.translatable("item.jwnsinvitationmod.invitation_card"));
        InvitationCheckScreen.name = name;
    }

    @Override
    protected void init() {
        x = (this.width - DRAW_WIDTH) / 2;
        y = (this.height - DRAW_HEIGHT) / 2;

        button_x = (this.width - 2 * BUTTON_WIDTH - 4) / 2;
        button_y = (this.height + DRAW_HEIGHT) / 2 - 10 - BUTTON_HEIGHT;

        ImageButton imageButton1 = new ImageButton(
                button_x, button_y, BUTTON_WIDTH, BUTTON_HEIGHT, new WidgetSprites(BUTTON, BUTTON_PRESSED),
                button -> {
                    assert Minecraft.getInstance().player != null;
                    Minecraft.getInstance().player.displayClientMessage(
                            Component.translatable("message.jwnsinvitationmod.invitaiton_card.cancel"), false);
                    this.onClose();
                });

        ImageButton imageButton2 = new ImageButton(
                button_x + BUTTON_WIDTH + 4, button_y, BUTTON_WIDTH, BUTTON_HEIGHT, new WidgetSprites(BUTTON, BUTTON_PRESSED),
                button -> {
                    assert Minecraft.getInstance().player != null;
                    InvitationC2SPacket packet = new InvitationC2SPacket(Minecraft.getInstance().player.getPlainTextName(), name);
                    ClientPacketDistributor.sendToServer(packet);
                    this.onClose();
                });

        addRenderableWidget(imageButton1);
        addRenderableWidget(imageButton2);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        graphics.blit(
                RenderPipelines.GUI_TEXTURED, TEXTURE, x, y,
                0.0F, 0.0F, DRAW_WIDTH, DRAW_HEIGHT,
                DRAW_WIDTH, DRAW_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT
        );

        super.render(graphics, mouseX, mouseY, partialTicks);

        graphics.drawString(this.font, this.title, x + 8, y + 8, 0xFF000000, false);

        Component text = Component.translatable("gui.jwnsinvitationmod.invitaiton_card.intive_success", name);
        int maxWidth = DRAW_WIDTH - 16;
        int startX = x + 8;
        int startY = y + 24;

        List<FormattedCharSequence> lines = this.font.split(text, maxWidth);

        int lineHeight = this.font.lineHeight;

        for (int i = 0; i < lines.size(); i++) {
            graphics.drawString(
                    this.font,
                    lines.get(i),
                    startX,
                    startY + (i * lineHeight) + 1,
                    0xFF000000,
                    false
            );
        }

        graphics.drawString(this.font, Component.translatable("gui.jwnsinvitationmod.invitaiton_card.no"),
            button_x + BUTTON_WIDTH / 2 - this.font.width(Component.translatable("gui.jwnsinvitationmod.invitaiton_card.no")) / 2,
                button_y + 3, 0xFF000000, false);

        graphics.drawString(this.font, Component.translatable("gui.jwnsinvitationmod.invitaiton_card.yes"),
                button_x + BUTTON_WIDTH + 4 + BUTTON_WIDTH / 2 - this.font.width(Component.translatable("gui.jwnsinvitationmod.invitaiton_card.yes")) / 2,
                button_y + 3, 0xFF000000, false);
    }
}
