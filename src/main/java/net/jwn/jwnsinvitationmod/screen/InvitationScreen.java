package net.jwn.jwnsinvitationmod.screen;

import net.jwn.jwnsinvitationmod.JWNsMod;
import net.jwn.jwnsinvitationmod.item.ModItemsClientHandler;
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
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class InvitationScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "textures/gui/invitation.png");
    private static final ResourceLocation TEXT_BOX = ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "text_box");
    private static final ResourceLocation BUTTON = ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "button1");
    private static final ResourceLocation BUTTON_PRESSED = ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "button1_highlighted");

    private static String name = "";

    private static final int IMAGE_WIDTH = 256;
    private static final int IMAGE_HEIGHT = 256;
    private static final int DRAW_WIDTH = 140;
    private static final int DRAW_HEIGHT = 90;

    private static final int BUTTON_WIDTH = 52;
    private static final int BUTTON_HEIGHT = 15;

    private static final int BOX_WIDTH = 114;
    private static final int BOX_HEIGHT = 16;

    int x;
    int y;
    int button_x;
    int button_y;
    int box_x;
    int box_y;

    public InvitationScreen() {
        super(Component.translatable("item.jwnsinvitationmod.invitation_card"));
    }

    @Override
    protected void init() {
        x = (this.width - DRAW_WIDTH) / 2;
        y = (this.height - DRAW_HEIGHT) / 2;

        button_x = (this.width - BUTTON_WIDTH) / 2;
        button_y = (this.height + DRAW_HEIGHT) / 2 - 10 - BUTTON_HEIGHT;

        ImageButton imageButton = new ImageButton(
                button_x, button_y, BUTTON_WIDTH, BUTTON_HEIGHT, new WidgetSprites(BUTTON, BUTTON_PRESSED),
                button -> {
                    assert Minecraft.getInstance().player != null;
//                    InvitationC2SPacket packet = new InvitationC2SPacket(Minecraft.getInstance().player.getPlainTextName(), name);
//                    ClientPacketDistributor.sendToServer(packet);
//                    this.onClose();
                    Minecraft.getInstance().setScreen(new InvitationCheckScreen(name));
                });
        addRenderableWidget(imageButton);

        box_x = (this.width - BOX_WIDTH) / 2;
        box_y = y + 32;

        EditBox nameField = new EditBox(
                this.font,
                box_x + 2, box_y + 4,
                BOX_WIDTH, BOX_HEIGHT,
                Component.empty()
        );
        nameField.setMaxLength(16);
        nameField.setBordered(false);
        nameField.setValue("");
        nameField.setResponder(text -> name = text);
        addRenderableWidget(nameField);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        graphics.blit(
                RenderPipelines.GUI_TEXTURED, TEXTURE, x, y,
                0.0F, 0.0F, DRAW_WIDTH, DRAW_HEIGHT,
                DRAW_WIDTH, DRAW_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT
        );

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, TEXT_BOX, box_x, box_y, BOX_WIDTH, BOX_HEIGHT);

        super.render(graphics, mouseX, mouseY, partialTicks);

        graphics.drawString(this.font, this.title, x + 8, y + 8, 0xFF000000, false);

        graphics.drawString(this.font, Component.translatable("gui.jwnsinvitationmod.invitaiton_card.button"),
            (this.width - this.font.width(Component.translatable("gui.jwnsinvitationmod.invitaiton_card.button"))) / 2,
                button_y + 3, 0xFF000000, false);
    }
}
