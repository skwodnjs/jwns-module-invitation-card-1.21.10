package net.jwn.jwnsinvitationmod.setting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jwn.jwnsinvitationmod.JWNsMod;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class InvitationSettingsData extends SavedData {
    // 월드 저장 파일 이름 (data/<IDENTIFIER>.dat)
    public static final String IDENTIFIER = JWNsMod.MOD_ID + "/invitation_settings";

    // boolean 하나만 저장하는 SavedDataType
    public static final SavedDataType<InvitationSettingsData> TYPE =
            new SavedDataType<>(
                    IDENTIFIER,
                    // 기본 생성자
                    InvitationSettingsData::new,
                    // 저장/로드용 Codec
                    RecordCodecBuilder.create(instance -> instance.group(
                            Codec.BOOL.fieldOf("allow_invite").forGetter(sd -> sd.allowInvite)
                    ).apply(instance, InvitationSettingsData::new))
            );

    private boolean allowInvite = true;

    public InvitationSettingsData() {
    }

    public InvitationSettingsData(boolean allowInvite) {
        this.allowInvite = allowInvite;
    }

    public boolean isAllowInvite() {
        return allowInvite;
    }

    public void setAllowInvite(boolean value) {
        if (this.allowInvite != value) {
            this.allowInvite = value;
            this.setDirty();
        }
    }

    private static InvitationSettingsData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public static InvitationSettingsData get(MinecraftServer server) {
        return get(server.overworld());
    }
}
