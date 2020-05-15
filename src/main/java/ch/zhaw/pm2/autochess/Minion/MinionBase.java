package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Minion.strategy.MoveStrategy;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.PositionVector;

/**
 * Represents the base Minion that every other Minion extends from
 */
public abstract class MinionBase {

    private static int idCount = 0;
    private final int minionId;
    private final Config.MinionType type;
    private final MoveStrategy strategy;

    private final int maxHealth;
    private final int baseAttack;
    private final int baseDefense;
    private final int baseAttackRange;
    private final int baseMovementRange;
    private final int baseAgility;
    private final int heroId;
    private final int price;
    private int health;
    private int attackModifier = 0;
    private int defenseModifier = 0;
    private int attackRangeModifier = 0;
    private int movementRangeModifier = 0;
    private int agilityModifier = 0;

    /**
     * Creates a new minion
     *
     * @param type        The type the minion should have
     * @param health      The maximal amount of hit points this minion can have
     * @param attack      The amount of damage the minion can do
     * @param defense     The amount by which damage is reduced
     * @param attackRange The attackRange the minion can move in
     * @param agility     The priority at which the minion can make its move
     */
    public MinionBase(Config.MinionType type, MoveStrategy strategy, int price, int health, int attack, int defense, int movementRange, int attackRange, int agility, int heroId) throws InvalidMinionAttributeException, InvalidMinionTypeException {
        if (type == null) throw new InvalidMinionTypeException("MinionType may not be null");
        if (strategy == null) throw new InvalidMinionAttributeException("Strategy may not be null");
        if (health > Config.MAX_MINION_HEALTH || health < Config.MIN_MINION_HEALTH)
            throw new InvalidMinionAttributeException("Invalid health parameter");
        if (attack > Config.MAX_MINION_ATTACK || attack < Config.MIN_MINION_ATTACK)
            throw new InvalidMinionAttributeException("Invalid attack parameter");
        if (defense > Config.MAX_MINION_DEFENSE || defense < Config.MIN_MINION_DEFENSE)
            throw new InvalidMinionAttributeException("Invalid defence parameter");
        if (movementRange > Config.MAX_MINION_MOVEMENT_RANGE || movementRange < Config.MIN_MINION_MOVEMENT_RANGE)
            throw new InvalidMinionAttributeException("Invalid movement range parameter");
        if (attackRange > Config.MAX_MINION_ATTACK_RANGE || attackRange < Config.MIN_MINION_ATTACK_RANGE)
            throw new InvalidMinionAttributeException("Invalid attack range parameter");
        if (agility > Config.MAX_MINION_AGILITY || agility < Config.MIN_MINION_AGILITY)
            throw new InvalidMinionAttributeException("Invalid agility parameter");
        if (price > Config.MAX_MINION_PRICE || price < Config.MIN_MINION_PRICE)
            throw new InvalidMinionAttributeException("Invalid price parameter");

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

    /**
     * Creates a minion based on type
     *
     * @param minionType Type of minion
     * @param heroId     Id of the hero that owns the minion.
     * @return minion
     * @throws InvalidMinionTypeException      Invalid type was passed in the constructor.
     * @throws InvalidMinionAttributeException Invalid attribute was passed in the minion that was attempted to be instantiated.
     */
    public static MinionBase newMinionFromType(Config.MinionType minionType, int heroId) throws InvalidMinionTypeException, InvalidMinionAttributeException {
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
     * Resets ID counter, should not be used outside of tests.
     */
    public static void resetMinionIdCounter() {
        idCount = 0;
    }

    /**
     * Returns the id of the hero that owns the minion
     *
     * @return heroId
     */
    public int getHeroId() {
        return heroId;
    }

    /**
     * Returns the minions unique id
     *
     * @return id
     */
    public int getId() {
        return minionId;
    }

    /**
     * Returns the minions type
     *
     * @return type
     */
    public Config.MinionType getType() {
        return type;
    }

    /**
     * Returns the price of the minion
     *
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Increases the minions health by a set amount.
     * If the increase results in a value above the maximum health it simply cuts off at max.
     *
     * @param amount health to add
     */
    public void increaseHealth(int amount) {
        if (health + amount > maxHealth) {
            health = maxHealth;
        } else {
            health += amount;
        }
    }

    /**
     * Decreases the minions health by a set amount.
     * If the decrease results in a value below 0 it is set to 0.
     *
     * @param amount health to remove
     */
    public void decreaseHealth(int amount) {
        if (health - amount < 0) {
            health = 0;
        } else {
            health -= amount;
        }
    }

    /**
     * Returns the minions health
     *
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the minions health back to full
     */
    public void resetHealth() {
        health = maxHealth;
    }

    /**
     * Change the modifier used to calculate attack
     *
     * @param attackModifier modifier
     * @throws InvalidMinionAttributeModifierException If modifier is outside of the acceptable range
     */
    public void setAttackModifier(int attackModifier) throws InvalidMinionAttributeModifierException {
        if (baseAttack + attackModifier < Config.MIN_MINION_ATTACK || baseAttack + attackModifier > Config.MAX_MINION_ATTACK)
            throw new InvalidMinionAttributeModifierException("Modifier caused attack to be too high/low");
        this.attackModifier = attackModifier;
    }

    /**
     * Change the modifier used to calculate defense
     *
     * @param defenseModifier modifier
     * @throws InvalidMinionAttributeModifierException If modifier is outside of the acceptable range
     */
    public void setDefenseModifier(int defenseModifier) throws InvalidMinionAttributeModifierException {
        if (baseDefense + defenseModifier < Config.MIN_MINION_DEFENSE || baseDefense + defenseModifier > Config.MAX_MINION_DEFENSE)
            throw new InvalidMinionAttributeModifierException("Modifier caused defense to be too high/low");
        this.defenseModifier = defenseModifier;
    }

    /**
     * Change the modifier used to calculate attack range
     *
     * @param attackRangeModifier modifier
     * @throws InvalidMinionAttributeModifierException If modifier is outside of the acceptable range
     */
    public void setAttackRangeModifier(int attackRangeModifier) throws InvalidMinionAttributeModifierException {
        if (baseAttackRange + attackRangeModifier < Config.MIN_MINION_ATTACK_RANGE || baseAttackRange + attackRangeModifier > Config.MAX_MINION_ATTACK_RANGE)
            throw new InvalidMinionAttributeModifierException("Modifier caused range to be too high/low");
        this.attackRangeModifier = attackRangeModifier;

    }

    /**
     * Change the modifier used to calculate the movement range
     *
     * @param rangeMovementModifier modifier
     * @throws InvalidMinionAttributeModifierException If modifier is outside of the acceptable range
     */
    public void setMovementRangeModifier(int rangeMovementModifier) throws InvalidMinionAttributeModifierException {
        if (baseMovementRange + rangeMovementModifier < Config.MIN_MINION_MOVEMENT_RANGE || baseMovementRange + rangeMovementModifier > Config.MAX_MINION_MOVEMENT_RANGE)
            throw new InvalidMinionAttributeModifierException("Modifier caused range to be too high/low");
        this.movementRangeModifier = rangeMovementModifier;

    }

    /**
     * Change the modifier used to calculate agility
     *
     * @param agilityModifier modifier
     * @throws InvalidMinionAttributeModifierException If modifier is outside of the acceptable range
     */
    public void setAgilityModifier(int agilityModifier) throws InvalidMinionAttributeModifierException {
        if (baseAgility + agilityModifier < Config.MIN_MINION_AGILITY || baseAgility + agilityModifier > Config.MAX_MINION_AGILITY)
            throw new InvalidMinionAttributeModifierException("Modifier caused agility to be too high/low");
        this.agilityModifier = agilityModifier;
    }

    /**
     * Returns the attack with the attackModifier applied
     *
     * @return attack
     */
    public int getAttack() {
        return baseAttack + attackModifier;
    }

    /**
     * Returns the defense with the defenseModifier applied
     *
     * @return defense
     */
    public int getDefense() {
        return baseDefense + defenseModifier;
    }

    /**
     * Returns the attack range with the attackRangeModifier applied
     *
     * @return attack range
     */
    public int getAttackRange() {
        return baseAttackRange + attackRangeModifier;
    }

    /**
     * Returns the movement range with the movementRangeModifier applied
     *
     * @return movement range
     */
    public int getMovementRange() {
        return baseMovementRange + movementRangeModifier;
    }

    /**
     * Returns the agility with the agilityModifier applied
     *
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
     * Use the minions strategy to calculate the next move and return it.
     *
     * @param board      The current board state
     * @param currentPos The current position of this minion on the board
     * @return Position to move to
     */
    public PositionVector move(MinionBase[][] board, PositionVector currentPos) {
        return strategy.move(board, currentPos, this);
    }

    /**
     * Use the minions strategy to calculate the next attack and return it.
     *
     * @param board      The current board state
     * @param currentPos The current position of this minion on the board
     * @return Position to attack
     */
    public PositionVector attack(MinionBase[][] board, PositionVector currentPos) {
        return strategy.attack(board, currentPos, this);
    }

    /**
     * Returns some basic information (id, type, health) of the minion in string form.
     *
     * @return minion info
     */
    public String getInfoAsString() {
        return "ID: " + minionId + ", type:" + type + ", HP: " + health;
    }

    @Override
    public String toString() {
        return String.valueOf(minionId);
    }
}
