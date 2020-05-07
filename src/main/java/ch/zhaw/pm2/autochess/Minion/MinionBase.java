package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Board.MoveStrategy;

/**
 * Represents the base Minion that every other Minion extends from
 */
public abstract class MinionBase {

    private static int idCount = 0;
    private final int minionId;
    private final MinionType type;
    private final MoveStrategy.StrategyType strategyType;

    private final int maxHealth;
    private final int baseAttack;
    private final int baseDefense;
    private final int baseAttackRange;
    private final int baseMovementRange;
    private final int baseAgility;
    private final int heroId;
    private int health = 0;
    private int level = 1;
    private int attackModifier = 0;
    private int defenseModifier = 0;
    private int attackRangeModifier = 0;
    private int movementRangeModifier = 0;
    private int agilityModifier = 0;

    /**
     * Creates a new minion
     * @param type The type the minion should have
     * @param health The maximal amount of hit points this minion can have
     * @param attack The amount of damage the minion can do
     * @param defense The amount by which damage is reduced
     * @param attackRange The attackRange the minion can move in
     * @param agility The priority at which the minion can make its move
     */
    public MinionBase(MinionType type, MoveStrategy.StrategyType strategyType, int health, int attack, int defense, int attackRange, int movementRange, int agility, int heroId) {
        // TODO: Verify args
        this.minionId = idCount++;
        this.type = type;
        this.strategyType = strategyType;
        maxHealth = health;
        baseAttack = attack;
        baseDefense = defense;
        baseAttackRange = attackRange;
        baseMovementRange = movementRange;
        baseAgility = agility;
        this.heroId = heroId;
    }

    public int getHeroId() {
        return heroId;
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

    public MoveStrategy.StrategyType getStrategyType() {
        return strategyType;
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
        this.level += levelDifference;
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
     * @param attackRangeModifier modifier
     */
    public void setAttackRangeModifier(int attackRangeModifier) {
        this.attackRangeModifier = attackRangeModifier;
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
    public int getAttackRange() {
        return baseAttackRange + attackRangeModifier;
    }

    public int getMovementRange() { return baseMovementRange + movementRangeModifier;}

    /**
     * Returns the agility with the agilityModifier applied
     * @return agility
     */
    public int getAgility() {
        return baseAgility + agilityModifier;
    }

    public void printInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Minion | ");
        sb.append("ID: " + minionId + "| ");
        sb.append("Type: " + type.toString() + "| ");
        sb.append("Hero " + heroId + "| ");

        System.out.println(sb.toString());
    }
}
