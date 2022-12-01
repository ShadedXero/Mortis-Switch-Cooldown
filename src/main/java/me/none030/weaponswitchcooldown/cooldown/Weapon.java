package me.none030.weaponswitchcooldown.cooldown;

public class Weapon {

    private final String name;
    private final long cooldown;

    public Weapon(String name, long cooldown) {
        this.name = name;
        this.cooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public long getCooldown() {
        return cooldown;
    }
}
