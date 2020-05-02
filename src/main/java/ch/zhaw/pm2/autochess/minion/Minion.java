package ch.zhaw.pm2.autochess.minion;

public abstract class Minion {

    private static int idCount = 0;
    private final int minionId;
    private final MinionType type;

    private final int maxHealth;
    private final int baseAttack;
    private final int baseDefense;
    private final int baseRange;
    private final int baseAgility;
    private int health = 0;
    private int attackModifier = 0;
    private int defenseModifier = 0;
    private int rangeModifier = 0;
    private int agilityModifier = 0;

    public Minion(MinionType type, int health, int attack, int defense, int range, int agility) {
        this.minionId = idCount++;
        this.type = type;
        maxHealth = health;
        baseAttack = attack;
        baseDefense = defense;
        baseRange = range;
        baseAgility = agility;
    }

    public int getMinionId() {
        return minionId;
    }

    public MinionType getType() {
        return type;
    }

    public void changeHealth(int modifier) {
        if (health + modifier > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health += modifier;
        }
    }

    public int getHealth() {
        return health;
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
