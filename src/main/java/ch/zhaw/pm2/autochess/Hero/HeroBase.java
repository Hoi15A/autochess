package ch.zhaw.pm2.autochess.Hero;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Hero.exceptions.*;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Abstract class to act as base for all concrete hero classes.
 */
public abstract class HeroBase {

    /**
     * Static method to generate concrete hero objects from a given {@link Config.HeroType}.
     * @param heroType Enum value to match with hero
     * @return Concrete hero corresponding to the given value
     * @throws InvalidHeroTypeException thrown if {@link Config} enum value is invalid
     * @throws InvalidHeroAttributeException thrown if {@link Config} parameter values are invalid
     */
    public static HeroBase getHeroFromType(Config.HeroType heroType) throws InvalidHeroTypeException, InvalidHeroAttributeException {
        switch (heroType) {
            case ALIEN:
                return new HeroAlien();
            case ENGINEER:
                return new HeroEngineer();
            case SPACE_MARINE:
                return new HeroSpaceMarine();
            default:
                throw new InvalidHeroTypeException("Given HeroType does not exist");
        }
    }

    private ArrayList<MinionBase> minionList = new ArrayList<>();

    private static final int MAX_LEGAL_VALUE = 1000;
    private static int heroCounter = 1;
    private final int heroId;
    private final int maxHealth;
    private final Config.HeroType heroType;
    private int health;
    private int funds;
    protected boolean abilityAvailable = true;

    /**
     * Constructor for {@link HeroBase}.
     * @param health Hero health
     * @param funds Hero funds
     * @param heroType {@link Config} enum value matching the concrete object
     * @throws InvalidHeroTypeException thrown if {@link Config} enum value is invalid
     * @throws InvalidHeroAttributeException thrown if {@link Config} parameter values are invalid
     */
    public HeroBase(int health, int funds, Config.HeroType heroType) throws InvalidHeroTypeException, InvalidHeroAttributeException {
        validateHeroParameters(health, funds);
        validateHeroType(heroType);
        this.heroId = heroCounter;
        heroCounter++;
        this.maxHealth = health;
        this.health = maxHealth;
        this.funds = funds;
        this.heroType = heroType;
    }

    private void validateHeroParameters(int health, int funds) throws InvalidHeroAttributeException {
        if (health > Config.MAX_HERO_HEALTH || health < Config.MIN_HERO_HEALTH) {
            throw new InvalidHeroAttributeException("Illegal hero health: " + health + ". Valid between: " + Config.MIN_HERO_HEALTH + " and " + Config.MAX_HERO_HEALTH);
        }
        if (funds > Config.MAX_FUNDS || funds < Config.MIN_FUNDS) {
            throw new InvalidHeroAttributeException("Illegal hero funds: " + funds + ". Valid between: " + Config.MIN_FUNDS + " and " + Config.MAX_FUNDS);

        }
    }

    private void validateHeroType(Config.HeroType heroTypeToCheck) throws InvalidHeroTypeException {
        boolean searching = true;
        for (Config.HeroType heroType : Config.HeroType.values()) {
            if (heroTypeToCheck.equals(heroType)) {
                searching = false;
            }
        }
        if (searching) {
            throw new InvalidHeroTypeException("Invalid hero type " + heroTypeToCheck);
        }
    }

    //****************
    //Hero methods
    //****************

    /**
     * Getter method for integer health attribute value.
     * @return int health attribute value
     */
    public int getHealth() {
        return health;
    }

    /**
     * Getter method for integer funds attribute value.
     * @return int funds attribute value
     */
    public int getFunds() {
        return funds;
    }

    /**
     * Getter method for unique integer ID attribute value.
     * @return int ID attribute value
     */
    public int getId() {
        return heroId;
    }

    /**
     * Getter method for {@link Config.HeroType} attribute matching the concrete hero.
     * @return {@link Config.HeroType} attribute value
     */
    public Config.HeroType getHeroType() {
        return heroType;
    }

    /**
     * Getter method for boolean attribute if the hero ability is available.
     * @return boolean for ability availability
     */
    public boolean isAbilityAvailable() {
        return abilityAvailable;
    }

    /**
     * Increases int health attribute by the given int value.
     * @param value int to increase health by
     * @throws IllegalValueException thrown if value is negative or greater than a {@link Config} value
     */
    public void increaseHealth(int value) throws IllegalValueException {
        validateLegalHeroValue(value);
        int newHealth = health + value;
        if (newHealth <= maxHealth) {
            health = newHealth;
        } else {
            health = maxHealth;
        }
    }

    /**
     * Decrease int health attribute by the given int value.
     * @param value int to decrease health by
     * @throws IllegalValueException thrown if value is negative or greater than a {@link Config} value
     */
    public void decreaseHealth(int value) throws IllegalValueException {
        validateLegalHeroValue(value);
        int newHealth = health - value;
        if (newHealth >= 0) {
            health = newHealth;
        } else {
            health = 0;
        }
    }

    /**
     * Increases int funds attribute by the given int value.
     * @param value int to increase funds by
     * @throws IllegalValueException thrown if value is negative or greater than a {@link Config} value
     */
    public void increaseFunds(int value) throws IllegalValueException {
        validateLegalHeroValue(value);
        int newFunds = funds + value;
        if (newFunds <= Config.MAX_FUNDS) {
            funds = newFunds;
        } else {
            funds = Config.MAX_FUNDS;
        }
    }

    /**
     * Decrease int funds attribute by the given int value.
     * @param value int to decrease funds by
     * @throws IllegalValueException thrown if value is negative or greater than a {@link Config} value
     * @throws IllegalFundsReductionException thrown if value cannot be subtracted from funds
     */
    public void decreaseFunds(int value) throws IllegalValueException, IllegalFundsReductionException {
        validateLegalHeroValue(value);
        int newFunds = funds - value;
        if (newFunds >= 0) {
            funds = newFunds;
        } else {
            throw new IllegalFundsReductionException("Funds not available");
        }
    }

    private void validateLegalHeroValue(int value) throws IllegalValueException {
        if (value < 0 || value > MAX_LEGAL_VALUE) {
            throw new IllegalValueException("Illegal hero value: " + value + "-> Negative or greater allowed max: " + MAX_LEGAL_VALUE);
        }
    }

    /**
     * Reset the unique ID counter for minions.
     * Used for testing
     */
    public static void resetIdCounter() {
        heroCounter = 0;
    }

    /**
     * Abstract method for hero ability. Abilities can be activated to modify the attributes of the heroes minions.
     * Each concrete hero implements their own specific modifications.
     * @throws InvalidMinionAttributeModifierException thrown if the attempted modification is invalid
     * @throws IllegalValueException thrown if ability cannot be activated
     */
    public abstract void doAbility() throws InvalidMinionAttributeModifierException, IllegalValueException;

    /**
     * Abstract method for minion buff. A buff is applied to the given minion, modifying its attributes.
     * Each concrete hero implements their own specific buff.
     * @param minion Minion to be buffed
     * @throws InvalidMinionAttributeModifierException thrown if the attempted modification is invalid
     */
    public abstract void buffMinion(MinionBase minion) throws InvalidMinionAttributeModifierException;

    //*********************
    //Minion Methods
    //*********************

    private void isValidId(int minionId) throws InvalidMinionIDException {
        boolean searching = true;
        for (MinionBase minion : minionList) {
            if (minion.getId() == minionId) {
                searching = false;
            }
        }
        if (searching) {
            throw new InvalidMinionIDException("Hero " + heroId + " holds no minion with ID " + minionId);
        }
    }

    private boolean isValidMinionType(Config.MinionType minionType) {
        boolean found = false;
        for (Config.MinionType minType : Config.MinionType.values()) {
            if (minType.equals(minionType)) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Buy a minion of the given {@link Config.MinionType}. Creates a minion object from the given MinionType.
     * Attempts to decrease the int price value of the minion from the heroes funds. If successful,
     * applies the hero specific modifications to the minion and adds the minion to the heroes minion list.
     * @param minionType
     * @throws IllegalValueException thrown if the attempted modification is invalid
     * @throws IllegalFundsReductionException thrown if value cannot be subtracted from funds
     * @throws InvalidMinionTypeException thrown if {@link Config} enum value is invalid
     * @throws InvalidMinionAttributeException thrown if {@link Config} minion value is invalid
     * @throws InvalidMinionAttributeModifierException thrown if {@link Config} modifier value is invalid
     */
    public void buyMinion(Config.MinionType minionType) throws IllegalValueException, IllegalFundsReductionException, InvalidMinionTypeException, InvalidMinionAttributeException, InvalidMinionAttributeModifierException {
        if (isValidMinionType(minionType)) {
            MinionBase minion = MinionBase.newMinionFromType(minionType, heroId);
            Objects.requireNonNull(minion, "Attempted add minion null");
            decreaseFunds(minion.getPrice());
            buffMinion(minion);
            minionList.add(minion);
        } else {
            throw new InvalidMinionTypeException("Not a valid Minion type");
        }
    }

    /**
     * Sell the minion matching the given int minion ID value. Attempts to remove the minion matching the ID.
     * If successful, increases the funds of the hero by the price of the minion removed.
     * @param minionId int ID value matching the minion to be sold
     * @throws IllegalValueException thrown if value is negative or greater than a {@link Config} value
     * @throws InvalidMinionIDException thrown if no minion matches the given minion ID
     */
    public void sellMinion(int minionId) throws IllegalValueException, InvalidMinionIDException {
        isValidId(minionId);
        int price = getMinion(minionId).getPrice();
        removeMinion(minionId);
        increaseFunds(price);
    }

    /**
     * Getter method for the minion matching the given int minion ID value.
     * @param minionId int ID value of minion to be returned
     * @return Minion matching the given ID. Not null.
     * @throws InvalidMinionIDException thrown if no minion matches the given minion ID
     */
    public MinionBase getMinion(int minionId) throws InvalidMinionIDException {
        isValidId(minionId);
        MinionBase foundMinion = null;
        for (MinionBase minion : minionList) {
            if (minion.getId() == minionId) {
                foundMinion = minion;
            }
        }
        Objects.requireNonNull(foundMinion, "Minion corresponding to valid ID not found");
        return foundMinion;
    }

    private void removeMinion(int minionId) throws InvalidMinionIDException {
        isValidId(minionId);
        minionList.removeIf(minion -> minion.getId() == minionId);
    }

    /**
     * Generates set of int IDs of all the minions in this heroes minion list.
     * @return Set of int ID values
     */
    public Set<Integer> getAllMinionIds() {
        Set<Integer> minionIdSet = new HashSet<>();
        for(MinionBase minion : minionList) {
            minionIdSet.add(minion.getId());
        }
        return minionIdSet;
    }

    /**
     * Getter method for the {@link Config.MinionType} attribute of the minion matching the given ID.
     * @param minionId int ID value of minion
     * @return {@link Config.MinionType} enum value
     * @throws InvalidMinionIDException thrown if no minion matches the given minion ID
     */
    public Config.MinionType getMinionType(int minionId) throws InvalidMinionIDException {
        isValidId(minionId);
        return getMinion(minionId).getType();
    }

    /**
     * Getter method for the minion list this hero holds
     * @return minion list
     */
    public ArrayList<MinionBase> getMinionList() {
        return minionList;
    }

    /**
     * Getter method for info String of the minion matching the given ID.
     * @param minionId int ID value of minion
     * @return String info of minion
     * @throws InvalidMinionIDException thrown if no minion matches the given minion ID
     */
    public String getMinionInfoAsString(int minionId) throws InvalidMinionIDException {
        isValidId(minionId);
        return getMinion(minionId).getInfoAsString();
    }
}
