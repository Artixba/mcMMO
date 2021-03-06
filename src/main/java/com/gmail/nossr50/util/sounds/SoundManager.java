package com.gmail.nossr50.util.sounds;

import com.gmail.nossr50.config.SoundConfig;
import com.gmail.nossr50.util.Misc;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SoundManager {
    /**
     * Sends a sound to the player
     * @param soundType the type of sound
     */
    public static void sendSound(Player player, Location location, SoundType soundType)
    {
        player.playSound(location, getSound(soundType), getVolume(soundType), getPitch(soundType));
    }

    public static void worldSendSound(World world, Location location, SoundType soundType)
    {
        world.playSound(location, getSound(soundType), getVolume(soundType), getPitch(soundType));
    }

    /**
     * All volume is multiplied by the master volume to get its final value
     * @param soundType target soundtype
     * @return the volume for this soundtype
     */
    private static float getVolume(SoundType soundType)
    {
        return SoundConfig.getInstance().getVolume(soundType) * SoundConfig.getInstance().getMasterVolume();
    }

    private static float getPitch(SoundType soundType)
    {
        if(soundType == SoundType.FIZZ)
            return getFizzPitch();
        else if (soundType == SoundType.POP)
            return getPopPitch();
        else if (soundType == SoundType.KRAKEN)
            return getKrakenPitch();
        else
            return SoundConfig.getInstance().getPitch(soundType);
    }

    private static Sound getSound(SoundType soundType)
    {
        switch(soundType)
        {
            case ANVIL:
                return Sound.BLOCK_ANVIL_PLACE;
            case ITEM_BREAK:
                return Sound.ENTITY_ITEM_BREAK;
            case POP:
                return Sound.ENTITY_ITEM_PICKUP;
            case KRAKEN:
                return Sound.ENTITY_GHAST_SCREAM;
            case CHIMAERA_WING:
                return Sound.ENTITY_BAT_TAKEOFF;
            case LEVEL_UP:
                return Sound.ENTITY_PLAYER_LEVELUP;
            case FIZZ:
                return Sound.BLOCK_FIRE_EXTINGUISH;
            default:
                return null;
        }
    }

    public static float getFizzPitch() {
        return 2.6F + (Misc.getRandom().nextFloat() - Misc.getRandom().nextFloat()) * 0.8F;
    }

    public static float getPopPitch() {
        return ((Misc.getRandom().nextFloat() - Misc.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F;
    }

    public static float getKrakenPitch() {
        return (Misc.getRandom().nextFloat() - Misc.getRandom().nextFloat()) * 0.2F + 1.0F;
    }
}
