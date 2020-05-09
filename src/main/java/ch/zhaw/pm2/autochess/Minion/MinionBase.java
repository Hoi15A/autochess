package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Minion.strategy.MoveStrategy;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;
import ch.zhaw.pm2.autochess.PositionVector;

/**
 * Represents the base Minion that every other Minion extends from
 */
public abstract class MinionBase {

    private static int idCount = 0;
    private final int minionId;
    private final MinionType type;
    private final MoveStrategy strategy;

    private static final int MAX_MINION_HEALTH = 100;
    private static final int MAX_MINION_ATTACK = 100;
    private static final int MIN_MINION_ATTACK = -100;
    private static final int MAX_MINION_DEFENSE = 100;
    private static final int MIN_MINION_DEFENSE = -100;
    private static final int MAX_MINION_MOVEMENT_RANGE = 10;
    private static final int MAX_MINION_ATTACK_RANGE = 10;
    private static final int MAX_MINION_AGILITY = 100;
    private static final int MAX_MINION_LEVEL = 3;
    private static final int MAX_MINION_PRICE = 10;

    private final int maxHealth;
    private final int baseAttack;
    private final int baseDefense;
    private final int baseAttackRange;
    private final int baseMovementRange;
    private final int baseAgility;
    private final int heroId;
    private final int price;
    private int health;
    private int level = 1;
    private int attackModifier = 0;
    private int defenseModifier = 0;
    private int attackRangeModifier = 0;
    private int movementRangeModifier = 0;
    private int agilityModifier = 0;

    public enum MinionType {
        WARRIOR, RANGER, TANK,
    }

    public static MinionBase newMinionFromType(MinionType minionType, int heroId) throws InvalidMinionTypeException, MinionException {
        MinionBase newMinion;

        switch (minionType) {
            case WARRIOR:
                newMinion = new Warrior(heroId);
                break;
            case RANGER:
                newMinion = new Ranger(heroId);
                break;
            case TANK:
                newMinion = new Tank(heroId);
                break;
            default:
                throw new InvalidMinionTypeException("Invalid minionType");
        }
        return newMinion;
    }

    /**
     * Creates a new minion
     * @param type The type the minion should have
     * @param health The maximal amount of hit points this minion can have
     * @param attack The amount of damage the minion can do
     * @param defense The amount by which damage is reduced
     * @param attackRange The attackRange the minion can move in
     * @param agility The priority at which the minion can make its move
     */
    public MinionBase(MinionType type, MoveStrategy strategy, int price, int health, int attack, int defense, int movementRange, int attackRange, int agility, int heroId) throws MinionException {
        if (type == null) throw new InvalidMinionTypeException("MinionType may not be null");
        if (strategy == null) throw new InvalidMinionAttributeException("Strategy may not be null");
        if (health > MAX_MINION_HEALTH || health <= 0) throw new InvalidMinionAttributeException("Health must be between 0 and " + MAX_MINION_HEALTH);
        if (attack > MAX_MINION_ATTACK || attack < MIN_MINION_ATTACK) throw new InvalidMinionAttributeException("Attack must be between " + MIN_MINION_ATTACK + " and " + MAX_MINION_ATTACK);
        if (defense > MAX_MINION_DEFENSE || defense < MIN_MINION_DEFENSE) throw new InvalidMinionAttributeException("Defense must be between " + MIN_MINION_DEFENSE + " and " + MAX_MINION_DEFENSE);
        if (movementRange > MAX_MINION_MOVEMENT_RANGE || movementRange < 0) throw new InvalidMinionAttributeException("Range must be between 0 and " + MAX_MINION_MOVEMENT_RANGE);
        if (attackRange > MAX_MINION_ATTACK_RANGE || attackRange < 0) throw new InvalidMinionAttributeException("Range must be between 0 and " + MAX_MINION_ATTACK_RANGE);
        if (agility > MAX_MINION_AGILITY || agility < 0) throw new InvalidMinionAttributeException("Agility must be between 0 and " + MAX_MINION_AGILITY);
        if (price > MAX_MINION_PRICE || price < 0) throw new InvalidMinionAttributeException("Price must be between 0 and " + MAX_MINION_PRICE);

        this.minionId = idCount++;
        this.type = type;
        this.strategy = strategy;
        this.maxHealth = health;
        this.health = health;
        this.baseAttack = attack;
        this.baseDefense = defense;
        this.baseAttackRange = attackRange;
        this.baseMovementRange = movementRange;
        this.baseAgility = agility;
        this.heroId = heroId;
        this.price = price;
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

    public int getPrice() {return price;}

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
    public void setAttackModifier(int attackModifier) throws InvalidMinionAttributeModifierException {
        if (baseAttack + attackModifier < MIN_MINION_ATTACK || baseAttack + attackModifier > MAX_MINION_ATTACK) throw new InvalidMinionAttributeModifierException("Modifier caused attack to be too high/low");
        this.attackModifier = attackModifier;
    }

    /**
     * Change the modifier used to calculate defense
     * @param defenseModifier modifier
     */
    public void setDefenseModifier(int defenseModifier) throws InvalidMinionAttributeModifierException {
        if (baseDefense + defenseModifier < MIN_MINION_DEFENSE || baseDefense + defenseModifier > MAX_MINION_DEFENSE) throw new InvalidMinionAttributeModifierException("Modifier caused defense to be too high/low");
        this.defenseModifier = defenseModifier;
    }

    /**
     * Change the modifier used to calculate range
     * @param attackRangeModifier modifier
     */
    public void setAttackRangeModifier(int attackRangeModifier) throws InvalidMinionAttributeModifierException {
        if (baseAttackRange + attackRangeModifier < 0 || baseAttackRange + attackRangeModifier > MAX_MINION_ATTACK_RANGE) throw new InvalidMinionAttributeModifierException("Modifier caused range to be too high/low");
        this.attackRangeModifier = attackRangeModifier;

    }

    public void setMovementRangeModifier(int rangeMovementModifier) throws InvalidMinionAttributeModifierException {
        if (baseMovementRange + rangeMovementModifier < 0 || baseMovementRange + rangeMovementModifier > MAX_MINION_MOVEMENT_RANGE) throw new InvalidMinionAttributeModifierException("Modifier caused range to be too high/low");
        this.movementRangeModifier = rangeMovementModifier;

    }

    /**
     * Change the modifier used to calculate agility
     * @param agilityModifier modifier
     */
    public void setAgilityModifier(int agilityModifier) throws InvalidMinionAttributeModifierException {
        if (baseAgility + agilityModifier < 0 || baseAgility + agilityModifier > MAX_MINION_AGILITY) throw new InvalidMinionAttributeModifierException("Modifier caused agility to be too high/low");
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
        sb.append("Health " + health + "| ");

        System.out.println(sb.toString());
    }
  
    /**
     * Resets ID counter, should not be used outside of tests.
     */
    public static void resetMinionIdCounter() {
        idCount = 0;
    }

    public PositionVector move(MinionBase[][] board, PositionVector currentPos) {
        return strategy.move(board, currentPos, getMovementRange());
    }

    public PositionVector attack(MinionBase[][] board, PositionVector currentPos) {
        return strategy.move(board, currentPos, getAttackRange());
    }
}
