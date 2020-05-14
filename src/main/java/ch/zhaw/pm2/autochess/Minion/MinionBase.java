package ch.zhaw.pm2.autochess.Minion;

import ch.zhaw.pm2.autochess.Config;
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
    private int level = 1;
    private int attackModifier = 0;
    private int defenseModifier = 0;
    private int attackRangeModifier = 0;
    private int movementRangeModifier = 0;
    private int agilityModifier = 0;

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
     * Creates a new minion
     * @param type The type the minion should have
     * @param health The maximal amount of hit points this minion can have
     * @param attack The amount of damage the minion can do
     * @param defense The amount by which damage is reduced
     * @param attackRange The attackRange the minion can move in
     * @param agility The priority at which the minion can make its move
     */
    public MinionBase(Config.MinionType type, MoveStrategy strategy, int price, int health, int attack, int defense, int movementRange, int attackRange, int agility, int heroId) throws InvalidMinionAttributeException, InvalidMinionTypeException {
        if (type == null) throw new InvalidMinionTypeException("MinionType may not be null");
        if (strategy == null) throw new InvalidMinionAttributeException("Strategy may not be null");
        if (health > Config.MAX_MINION_HEALTH || health < Config.MIN_MINION_HEALTH) throw new InvalidMinionAttributeException("Invalid health parameter");
        if (attack > Config.MAX_MINION_ATTACK || attack < Config.MIN_MINION_ATTACK) throw new InvalidMinionAttributeException("Invalid attack parameter");
        if (defense > Config.MAX_MINION_DEFENSE || defense < Config.MIN_MINION_DEFENSE) throw new InvalidMinionAttributeException("Invalid defence parameter");
        if (movementRange > Config.MAX_MINION_MOVEMENT_RANGE || movementRange < Config.MIN_MINION_MOVEMENT_RANGE) throw new InvalidMinionAttributeException("Invalid movement range parameter");
        if (attackRange > Config.MAX_MINION_ATTACK_RANGE || attackRange < Config.MIN_MINION_ATTACK_RANGE) throw new InvalidMinionAttributeException("Invalid attack range parameter");
        if (agility > Config.MAX_MINION_AGILITY || agility < Config.MIN_MINION_AGILITY) throw new InvalidMinionAttributeException("Invalid agility parameter");
        if (price > Config.MAX_MINION_PRICE || price < Config.MIN_MINION_PRICE) throw new InvalidMinionAttributeException("Invalid price parameter");

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
    public Config.MinionType getType() {
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
        if (level + levelDifference > Config.MAX_MINION_LEVEL) {
            level = Config.MAX_MINION_LEVEL;
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
        if (baseAttack + attackModifier < Config.MIN_MINION_ATTACK || baseAttack + attackModifier > Config.MAX_MINION_ATTACK) throw new InvalidMinionAttributeModifierException("Modifier caused attack to be too high/low");
        this.attackModifier = attackModifier;
    }

    /**
     * Change the modifier used to calculate defense
     * @param defenseModifier modifier
     */
    public void setDefenseModifier(int defenseModifier) throws InvalidMinionAttributeModifierException {
        if (baseDefense + defenseModifier < Config.MIN_MINION_DEFENSE || baseDefense + defenseModifier > Config.MAX_MINION_DEFENSE) throw new InvalidMinionAttributeModifierException("Modifier caused defense to be too high/low");
        this.defenseModifier = defenseModifier;
    }

    /**
     * Change the modifier used to calculate range
     * @param attackRangeModifier modifier
     */
    public void setAttackRangeModifier(int attackRangeModifier) throws InvalidMinionAttributeModifierException {
        if (baseAttackRange + attackRangeModifier < Config.MIN_MINION_ATTACK_RANGE || baseAttackRange + attackRangeModifier > Config.MAX_MINION_ATTACK_RANGE) throw new InvalidMinionAttributeModifierException("Modifier caused range to be too high/low");
        this.attackRangeModifier = attackRangeModifier;

    }

    public void setMovementRangeModifier(int rangeMovementModifier) throws InvalidMinionAttributeModifierException {
        if (baseMovementRange + rangeMovementModifier < Config.MIN_MINION_MOVEMENT_RANGE || baseMovementRange + rangeMovementModifier > Config.MAX_MINION_MOVEMENT_RANGE) throw new InvalidMinionAttributeModifierException("Modifier caused range to be too high/low");
        this.movementRangeModifier = rangeMovementModifier;

    }

    /**
     * Change the modifier used to calculate agility
     * @param agilityModifier modifier
     */
    public void setAgilityModifier(int agilityModifier) throws InvalidMinionAttributeModifierException {
        if (baseAgility + agilityModifier < Config.MIN_MINION_AGILITY || baseAgility + agilityModifier > Config.MAX_MINION_AGILITY) throw new InvalidMinionAttributeModifierException("Modifier caused agility to be too high/low");
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
        return strategy.attack(board, currentPos, getAttackRange());
    }

    public String getInfoAsString() {
        String info = "-ID: " + minionId + ", type:" + type + ", Lvl: " + level + ", HP: " + health;
        return info;
    }
}
