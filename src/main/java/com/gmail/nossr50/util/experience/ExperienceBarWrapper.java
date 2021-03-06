package com.gmail.nossr50.util.experience;

import com.gmail.nossr50.config.experience.ExperienceConfig;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.util.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * A visual representation of a players skill level progress for a PrimarySkillType
 */
public class ExperienceBarWrapper {

    private final PrimarySkillType primarySkillType; //Primary Skill
    private BossBar bossBar;
    private final Server server;
    protected final McMMOPlayer mcMMOPlayer;
    private int lastLevelUpdated;

    /*
     * This is stored to help optimize updating the title
     */
    protected String niceSkillName;
    protected String title;

    public ExperienceBarWrapper(PrimarySkillType primarySkillType, McMMOPlayer mcMMOPlayer)
    {
        this.mcMMOPlayer = mcMMOPlayer;
        this.server = mcMMOPlayer.getPlayer().getServer(); //Might not be good for bungee to do this
        this.primarySkillType = primarySkillType;
        title = "";
        lastLevelUpdated = 0;

        //These vars are stored to help reduce operations involving strings
        niceSkillName = StringUtils.getCapitalized(primarySkillType.toString());

        //Create the bar
        initBar();
    }

    private void initBar()
    {
        title = getTitleTemplate();
        createBossBar();
    }

    public void updateTitle() {
        title = getTitleTemplate();
        bossBar.setTitle(title);
    }

    private String getTitleTemplate() {
        return niceSkillName + " Lv." + ChatColor.GOLD + getLevel();
    }

    private int getLevel() {
        return mcMMOPlayer.getSkillLevel(primarySkillType);
    }

    public String getTitle() {
        return bossBar.getTitle();
    }

    public void setTitle(String s) {
        bossBar.setTitle(s);
    }

    public BarColor getColor() {
        return bossBar.getColor();
    }

    public void setColor(BarColor barColor) {
        bossBar.setColor(barColor);
    }

    public BarStyle getStyle() {
        return bossBar.getStyle();
    }

    public void setStyle(BarStyle barStyle) {
        bossBar.setStyle(barStyle);
    }

    public void setProgress(double v) {
        //Clamp Values
        if(v < 0)
            bossBar.setProgress(0.0D);

        else if(v > 1)
            bossBar.setProgress(1.0D);
        else
            bossBar.setProgress(v);

        //Every time progress updates we need to check for a title update
        if(getLevel() != lastLevelUpdated)
        {
            updateTitle();
            lastLevelUpdated = getLevel();
        }
    }

    public double getProgress() {
        return bossBar.getProgress();
    }

    public List<Player> getPlayers() {
        return bossBar.getPlayers();
    }

    public boolean isVisible() {
        return bossBar.isVisible();
    }

    public void hideExperienceBar()
    {
        bossBar.setVisible(false);
    }

    public void showExperienceBar()
    {
        bossBar.setVisible(true);
    }

    /*public NamespacedKey getKey()
    {
        return bossBar
    }*/

    private void createBossBar()
    {
        bossBar = mcMMOPlayer.getPlayer().getServer().createBossBar(title, ExperienceConfig.getInstance().getExperienceBarColor(primarySkillType), ExperienceConfig.getInstance().getExperienceBarStyle(primarySkillType));
        bossBar.addPlayer(mcMMOPlayer.getPlayer());
    }
}
