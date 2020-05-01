package ch.zhaw.pm2.autochess.minion;

public class Minion {

    private final int ownerId;
    private final MinionType type;

    private final int baseHealth;
    private final int baseAttack;
    private final int baseDefense;
    private final int baseRange;
    private final int baseAgility;
    private int healthModifier = 0;
    private int attackModifier = 0;
    private int defenseModifier = 0;
    private int rangeModifier = 0;
    private int agilityModifier = 0;

    public Minion(int ownerId, MinionType type, int health, int attack, int defense, int range, int agility) {
        this.ownerId = ownerId;
        this.type = type;
        baseHealth = health;
        baseAttack = attack;
        baseDefense = defense;
        baseRange = range;
        baseAgility = agility;
    }

    public void setHealthModifier(int healthModifier) {
        this.healthModifier = healthModifier;
    }

    public void setAttackModifier(int attackModifier) {
        this.attackModifier = attackModifier;
    }

    public void setDefenseModifier(int defenseModifier) {
        this.defenseModifier = defenseModifier;
    }

    public void setRangeModifier(int rangeModifier) {
        this.rangeModifier = rangeModifier;
    }

    public void setAgilityModifier(int agilityModifier) {
        this.agilityModifier = agilityModifier;
    }

    public int getHealth() {
        return baseHealth + healthModifier;
    }

    public int getAttack() {
        return baseAttack + attackModifier;
    }

    public int getDefense() {
        return baseDefense + defenseModifier;
    }

    public int getRange() {
        return baseRange + rangeModifier;
    }

    public int getAgility() {
        return baseAgility + agilityModifier;
    }
}
