package net.jwn.jwnsinvitationmod.item;

import net.jwn.jwnsinvitationmod.JWNsMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(JWNsMod.MOD_ID);

    public static final DeferredItem<Item> INVITATION_CARD = ITEMS.registerItem("invitation_card",
            p -> new InvitationCardItem(p
                    .stacksTo(16)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
