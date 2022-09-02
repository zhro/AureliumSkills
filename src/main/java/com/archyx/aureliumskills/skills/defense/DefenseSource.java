package com.archyx.aureliumskills.skills.defense;

import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.skills.Skills;
import com.archyx.aureliumskills.source.Source;
import com.archyx.aureliumskills.util.item.ItemUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum DefenseSource implements Source {

    MOB_DAMAGE("ZOMBIE_HEAD"),
    PLAYER_DAMAGE("PLAYER_HEAD");

    private final String material;

    DefenseSource(String material) {
        this.material = material;
    }

    @Override
    public @NotNull Skill getSkill() {
        return Skills.DEFENSE;
    }

    @Override
    public @NotNull String getUnitName() {
        return "damage";
    }

    @Override
    public @Nullable ItemStack getMenuItem() {
        return ItemUtils.parseItem(material);
    }
}
