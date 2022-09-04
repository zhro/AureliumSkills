package com.archyx.aureliumskills.ability;

import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.configuration.Option;
import com.archyx.aureliumskills.configuration.OptionL;
import com.archyx.aureliumskills.data.PlayerData;
import com.archyx.aureliumskills.mana.MAbility;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.source.Source;
import com.archyx.aureliumskills.source.SourceTag;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public abstract class AbilityProvider {

    public final @NotNull AureliumSkills plugin;
    protected final @NotNull Skill skill;
    private final @NotNull String skillName;

    public AbilityProvider(@NotNull AureliumSkills plugin, @NotNull Skill skill) {
        this.plugin = plugin;
        this.skill = skill;
        this.skillName = skill.toString().toLowerCase(Locale.ENGLISH);
    }

    public boolean blockAbility(@NotNull Player player) {
        if (plugin.getWorldManager().isInDisabledWorld(player.getLocation())) {
            return true;
        }
        if (!player.hasPermission("aureliumskills." + skillName)) {
            return true;
        }
        if (OptionL.getBoolean(Option.DISABLE_IN_CREATIVE_MODE)) {
            return player.getGameMode().equals(GameMode.CREATIVE);
        }
        return false;
    }

    public boolean blockDisabled(@NotNull Ability ability) {
        if (!OptionL.isEnabled(ability.getSkill())) {
            return true;
        }
        return !plugin.getAbilityManager().isEnabled(ability);
    }

    public boolean blockDisabled(@NotNull MAbility ability) {
        if (!OptionL.isEnabled(ability.getSkill())) {
            return true;
        }
        return !plugin.getAbilityManager().isEnabled(ability);
    }

    public double getXp(@NotNull Player player, @NotNull Source source, @NotNull Ability ability) {
        @Nullable PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
        if (playerData != null) {
            double output = plugin.getSourceManager().getXp(source);
            if (plugin.getAbilityManager().isEnabled(ability)) {
                double modifier = 1;
                modifier += plugin.getAbilityManager().getValue(ability, playerData.getAbilityLevel(ability)) / 100;
                output *= modifier;
            }
            return output;
        }
        return 0.0;
    }

    public boolean isEnabled(@NotNull Ability ability) {
        return plugin.getAbilityManager().isEnabled(ability);
    }

    public double getValue(@NotNull Ability ability, @NotNull PlayerData playerData) {
        return plugin.getAbilityManager().getValue(ability, playerData.getAbilityLevel(ability));
    }

    public double getValue2(@NotNull Ability ability, @NotNull PlayerData playerData) {
        return plugin.getAbilityManager().getValue2(ability, playerData.getAbilityLevel(ability));
    }

    public double getValue(@NotNull MAbility mability, @NotNull PlayerData playerData) {
        return plugin.getManaAbilityManager().getValue(mability, playerData.getManaAbilityLevel(mability));
    }

    public double getManaCost(@NotNull MAbility mability, @NotNull PlayerData playerData) {
        return plugin.getManaAbilityManager().getManaCost(mability, playerData);
    }

    public boolean hasTag(Source source, SourceTag tag) {
        for (Source sourceWithTag : plugin.getSourceManager().getTag(tag)) {
            if (source == sourceWithTag) {
                return true;
            }
        }
        return false;
    }

}
