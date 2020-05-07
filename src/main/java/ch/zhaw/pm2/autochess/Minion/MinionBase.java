package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

/**
 * Represents the base Minion that every other Minion extends from
 */
public abstract class MinionBase {

    private static int idCount = 0;
    private final int minionId;
    private final MinionType type;

    private static final int MAX_MINION_HEALTH = 100;
    private static final int MAX_MINION_ATTACK = 100;
    private static final int MIN_MINION_ATTACK = -100;
    private static final int MAX_MINION_DEFENSE = 100;
    private static final int MIN_MINION_DEFENSE = -100;
    private static final int MAX_MINION_RANGE = 10;
    private static final int MAX_MINION_AGILITY = 100;
    private static final int MAX_MINION_LEVEL = 3;

    private final int maxHealth;
    private final int baseAttack;
    private final int baseDefense;
    private final int baseRange;
    private final int baseAgility;
    private int health;
    private int level = 1;
    private int attackModifier = 0;
    private int defenseModifier = 0;
    private int rangeModifier = 0;
    private int agilityModifier = 0;

    /**
     * Creates a new minion
     * @param type The type the minion should have
     * @param health The maximal amount of hit points this minion can have
     * @param attack The amount of damage the minion can do
     * @param defense The amount by which damage is reduced
     * @param range The range the minion can move in
     * @param agility The priority at which the minion can make its move
     */
    public MinionBase(MinionType type, int health, int attack, int defense, int range, int agility) throws MinionException {
        if (type == null) throw new InvalidMinionTypeException("MinionType may not be null");
        if (health > MAX_MINION_HEALTH || health <= 0) throw new InvalidMinionAttributeException("Health must be between 0 and " + MAX_MINION_HEALTH);
        if (attack > MAX_MINION_ATTACK || attack < MIN_MINION_ATTACK) throw new InvalidMinionAttributeException("Attack must be between " + MIN_MINION_ATTACK + " and " + MAX_MINION_ATTACK);
        if (defense > MAX_MINION_DEFENSE || defense < MIN_MINION_DEFENSE) throw new InvalidMinionAttributeException("Defense must be between " + MIN_MINION_DEFENSE + " and " + MAX_MINION_DEFENSE);
        if (range > MAX_MINION_RANGE || range < 0) throw new InvalidMinionAttributeException("Range must be between 0 and " + MAX_MINION_RANGE);
        if (agility > MAX_MINION_AGILITY || agility < 0) throw new InvalidMinionAttributeException("Agility must be between 0 and " + MAX_MINION_AGILITY);

        this.minionId = idCount++;
        this.type = type;
        maxHealth = health;
        this.health = health;
        baseAttack = attack;
        baseDefense = defense;
        baseRange = range;
        baseAgility = agility;
    }

    /**
     * Returns the minions unique id
     * @return id
     */
    public int getId() {
        return minionId;
    }

    /**
     * Returns the minions type
     * @return type
     */
    public MinionType getType() {
        return type;
    }

    /**
     * Alter the amount of health a minion has.
     * The modifier can be any positive or negative integer which will be added to the health.
     * If the calculation goes outside the range of [0, maxHealth] then it will be set to either 0 or max.
     * @param modifier amount by which health should be altered
     */
    public void changeHealth(int modifier) {
        if (health + modifier > maxHealth) {
            this.health = maxHealth;
        } else if (health + modifier < 0) {
            this.health = 0;
        } else {
            this.health += modifier;
        }
    }

    /**
     * Returns the minions health
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the minions level
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Alter the minions level
     * @param levelDifference The change in level to apply
     */
    public void modifyLevel(int levelDifference) {
        if (level + levelDifference > MAX_MINION_LEVEL) {
            level = MAX_MINION_LEVEL;
        } else if (level + levelDifference < 1) {
            level = 1;
        } else {
            level += levelDifference;
        }
    }

    /**
     * Change the modifier used to calculate attack
     * @param attackModifier modifier
     */
    public void setAttackModifier(int attackModifier) {
        this.attackModifier = attackModifier;
    }

    /**
     * Change the modifier used to calculate defense
     * @param defenseModifier modifier
     */
    public void setDefenseModifier(int defenseModifier) {
        this.defenseModifier = defenseModifier;
    }

    /**
     * Change the modifier used to calculate range
     * @param rangeModifier modifier
     */
    public void setRangeModifier(int rangeModifier) {
        this.rangeModifier = rangeModifier;
    }

    /**
     * Change the modifier used to calculate agility
     * @param agilityModifier modifier
     */
    public void setAgilityModifier(int agilityModifier) {
        this.agilityModifier = agilityModifier;
    }

    /**
     * Returns the attack with the attackModifier applied
     * @return attack
     */
    public int getAttack() {
        return baseAttack + attackModifier;
    }

    /**
     * Returns the defense with the defenseModifier applied
     * @return defense
     */
    public int getDefense() {
        return baseDefense + defenseModifier;
    }

    /**
     * Returns the range with the rangeModifier applied
     * @return range
     */
    public int getRange() {
        return baseRange + rangeModifier;
    }

    /**
     * Returns the agility with the agilityModifier applied
     * @return agility
     */
    public int getAgility() {
        return baseAgility + agilityModifier;
    }
}
