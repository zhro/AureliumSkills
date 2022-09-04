package com.archyx.aureliumskills.menus.levelprogression;

import com.archyx.aureliumskills.AureliumSkills;
import com.archyx.aureliumskills.lang.Lang;
import com.archyx.aureliumskills.lang.MenuMessage;
import com.archyx.aureliumskills.menus.common.AbstractItem;
import com.archyx.aureliumskills.skills.Skill;
import com.archyx.aureliumskills.util.math.NumberUtil;
import com.archyx.aureliumskills.util.text.TextUtil;
import com.archyx.slate.item.provider.PlaceholderType;
import com.archyx.slate.item.provider.SingleItemProvider;
import com.archyx.slate.menu.ActiveMenu;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;

public class RankItem extends AbstractItem implements SingleItemProvider {

    public RankItem(AureliumSkills plugin) {
        super(plugin);
    }

    @Override
    public @NotNull String onPlaceholderReplace(@NotNull String placeholder, @NotNull Player player, @NotNull ActiveMenu activeMenu, @NotNull PlaceholderType type) {
        @Nullable Locale locale = plugin.getLang().getLocale(player);
        Skill skill = getSkill(activeMenu);
        switch (placeholder) {
            case "your_ranking":
                return Lang.getMessage(MenuMessage.YOUR_RANKING, locale);
            case "out_of":
                int rank = getRank(skill, player);
                int size = getSize(skill, player);
                return TextUtil.replace(Lang.getMessage(MenuMessage.RANK_OUT_OF, locale),
                        "{rank}", String.valueOf(rank),
                        "{total}", String.valueOf(size));
            case "percent":
                double percent = getPercent(skill, player);
                if (percent > 1) {
                    return TextUtil.replace(Lang.getMessage(MenuMessage.RANK_PERCENT, locale),
                            "{percent}", String.valueOf(Math.round(percent)));
                } else {
                    return TextUtil.replace(Lang.getMessage(MenuMessage.RANK_PERCENT, locale),
                            "{percent}", NumberUtil.format2(percent));
                }
            case "leaderboard_click":
                return TextUtil.replace(Lang.getMessage(MenuMessage.LEADERBOARD_CLICK, locale),
                        "{skill}", skill.getDisplayName(locale));
        }
        return placeholder;
    }

    @Override
    public void onClick(@NotNull Player player, @NotNull InventoryClickEvent event, @NotNull ItemStack item, @NotNull SlotPos pos, @NotNull ActiveMenu activeMenu) {
        Map<String, Object> properties = activeMenu.getProperties();
        properties.put("previous_menu", "level_progression");
        plugin.getMenuManager().openMenu(player, "leaderboard", properties);
    }

    private double getPercent(@NotNull Skill skill, @NotNull Player player) {
        int rank = getRank(skill, player);
        int size = getSize(skill, player);
        return (double) rank / (double) size * 100;
    }

    private int getRank(@NotNull Skill skill, @NotNull Player player) {
        return plugin.getLeaderboardManager().getSkillRank(skill, player.getUniqueId());
    }

    private int getSize(@NotNull Skill skill, @NotNull Player player) {
        return plugin.getLeaderboardManager().getLeaderboard(skill).size();
    }

    private @NotNull Skill getSkill(@NotNull ActiveMenu activeMenu) {
        @Nullable Object property = activeMenu.getProperty("skill");
        if (!(property instanceof Skill)) {
            throw new IllegalArgumentException("Could not get menu skill property");
        }
        return (Skill) property;
    }

}
