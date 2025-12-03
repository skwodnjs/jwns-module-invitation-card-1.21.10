package net.jwn.jwnsinvitationmod.screen;

import net.jwn.jwnsinvitationmod.JWNsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class InvitationScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "textures/gui/invitation.png");
    private static final ResourceLocation BUTTON = ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "button01");
    private static final ResourceLocation BUTTON_PRESSED = ResourceLocation.fromNamespaceAndPath(JWNsMod.MOD_ID, "button02");

    private static String name = "";

    private static final int IMAGE_WIDTH = 256;
    private static final int IMAGE_HEIGHT = 256;
    private static final int DRAW_WIDTH = 137;
    private static final int DRAW_HEIGHT = 76;

    private static final int BUTTON_WIDTH = 52;
    private static final int BUTTON_HEIGHT = 16;

    private static final int BOX_WIDTH = 113;
    private static final int BOX_HEIGHT = 16;

    public InvitationScreen() {
        super(Component.translatable("item.jwnsinvitationmod.invitation_card"));
    }

    @Override
    protected void init() {
        int button_x = (this.width - BUTTON_WIDTH) / 2;
        int button_y = (this.height - DRAW_HEIGHT) / 2 + 42;

        WidgetSprites widgetSprites = new WidgetSprites(BUTTON, BUTTON_PRESSED);

        ImageButton imageButton = new ImageButton(
                button_x, button_y, BUTTON_WIDTH, BUTTON_HEIGHT, widgetSprites,
                button -> {
                    assert Minecraft.getInstance().player != null;
                    Minecraft.getInstance().player.displayClientMessage(Component.literal(name), false);
                    this.onClose();
                });

        int box_x = (this.width - BOX_WIDTH) / 2 + 4;
        int box_y = (this.height - DRAW_HEIGHT) / 2 + 26;

        EditBox nameField = new EditBox(
                this.font,
                box_x, box_y,
                BOX_WIDTH, BOX_HEIGHT,
                Component.empty()
        );
        nameField.setMaxLength(16);
        nameField.setBordered(false);
        nameField.setValue("");
        nameField.setResponder(text -> name = text);
        this.addRenderableWidget(nameField);

        addRenderableWidget(imageButton);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int x = (this.width - DRAW_WIDTH) / 2;
        int y = (this.height - DRAW_HEIGHT) / 2;

        graphics.blit(
                RenderPipelines.GUI_TEXTURED, TEXTURE, x, y,
                0.0F, 0.0F, DRAW_WIDTH, DRAW_HEIGHT,
                DRAW_WIDTH, DRAW_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT
        );

        super.render(graphics, mouseX, mouseY, partialTicks);

        graphics.drawString(this.font, this.title, x + 8, y + 8, 0xFF000000, false);

        graphics.drawString(this.font, Component.translatable("gui.jwnsinvitationmod.invitaiton_card.button"),
            (this.width - this.font.width(Component.translatable("gui.jwnsinvitationmod.invitaiton_card.button"))) / 2,
                (this.height - DRAW_HEIGHT) / 2 + 46, 0xFF000000, false);
    }
}
