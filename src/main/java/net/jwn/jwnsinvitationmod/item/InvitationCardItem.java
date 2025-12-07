package net.jwn.jwnsinvitationmod.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class InvitationCardItem extends Item {
    public InvitationCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) ModItemsClientHandler.openInvitationScreen();
        return InteractionResult.SUCCESS;
    }
}
