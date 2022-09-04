package com.archyx.aureliumskills.rewards.builder;

import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.rewards.PermissionReward;
import com.archyx.aureliumskills.rewards.Reward;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PermissionRewardBuilder extends MessagedRewardBuilder {

    private String permission;
    private boolean value;

    public PermissionRewardBuilder(@NotNull AureliumSkills plugin) {
        super(plugin);
        this.value = true;
    }

    public @NotNull PermissionRewardBuilder permission(@Nullable String permission) {
        this.permission = permission;
        return this;
    }
    
    public @NotNull PermissionRewardBuilder value(boolean value) {
        this.value = value;
        return this;
    }

    @Override
    public @NotNull Reward build() {
        Objects.requireNonNull(permission, "You must specify a permission");
        return new PermissionReward(plugin, menuMessage, chatMessage, permission, value);
    }
    

}
