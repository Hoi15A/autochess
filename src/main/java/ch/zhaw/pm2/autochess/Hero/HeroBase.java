package ch.zhaw.pm2.autochess.Hero;

import ch.zhaw.pm2.autochess.Config;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalFundsStateException;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalHeroValueException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidHeroTypeException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidMinionIDException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionAttributeModifierException;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;
import ch.zhaw.pm2.autochess.Minion.exceptions.MinionException;

import java.util.*;

public abstract class HeroBase {

    public static HeroBase getHeroFromType(Config.HeroType heroType) throws InvalidHeroTypeException, IllegalHeroValueException {
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

    public HeroBase(int health, int funds, Config.HeroType heroType) throws IllegalHeroValueException, InvalidHeroTypeException {
        validateHeroParameters(health, funds);
        validateHeroType(heroType);
        this.heroId = heroCounter;
        heroCounter++;
        this.maxHealth = health;
        this.health = maxHealth;
        this.funds = funds;
        this.heroType = heroType;
    }

    private void validateHeroParameters(int health, int funds) throws IllegalHeroValueException {
        if (health > Config.MAX_HERO_HEALTH || health < Config.MIN_HERO_HEALTH) {
            throw new IllegalHeroValueException("Illegal hero health: " + health + ". Valid between: " + Config.MIN_HERO_HEALTH + " and " + Config.MAX_HERO_HEALTH);
        }
        if (funds > Config.MAX_FUNDS || funds < Config.MIN_FUNDS) {
            throw new IllegalHeroValueException("Illegal hero funds: " + funds + ". Valid between: " + Config.MIN_FUNDS + " and " + Config.MAX_FUNDS);

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

    public int getHealth() {
        return health;
    }

    public int getFunds() {
        return funds;
    }

    public int getId() {
        return heroId;
    }

    public Config.HeroType getHeroType() {
        return heroType;
    }

    public boolean isAbilityAvailable() {
        return abilityAvailable;
    }

    public void increaseHealth(int value) throws IllegalHeroValueException {
        validateLegalHeroValue(value);
        int newHealth = health + value;
        if (newHealth <= maxHealth) {
            health = newHealth;
        } else {
            health = maxHealth;
        }
    }

    public void decreaseHealth(int value) throws IllegalHeroValueException {
        validateLegalHeroValue(value);
        int newHealth = health - value;
        if (newHealth >= 0) {
            health = newHealth;
        } else {
            health = 0;
        }
    }

    public void increaseFunds(int value) throws IllegalHeroValueException {
        validateLegalHeroValue(value);
        int newFunds = funds + value;
        if (newFunds <= Config.MAX_FUNDS) {
            funds = newFunds;
        } else {
            funds = Config.MAX_FUNDS;
        }
    }

    public void decreaseFunds(int value) throws IllegalHeroValueException, IllegalFundsStateException {
        validateLegalHeroValue(value);
        int newFunds = funds - value;
        if (newFunds >= 0) {
            funds = newFunds;
        } else {
            throw new IllegalFundsStateException("Funds not available");
        }
    }

    private void validateLegalHeroValue(int value) throws IllegalHeroValueException {
        if (value < 0 || value > MAX_LEGAL_VALUE) {
            throw new IllegalHeroValueException("Illegal hero value: " + value + "-> Negative or greater allowed max: " + MAX_LEGAL_VALUE);
        }
    }

    public static void resetIdCounter() {
        heroCounter = 0;
    }

    public abstract void doAbility() throws InvalidMinionAttributeModifierException, IllegalHeroValueException;

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

    public void buyMinion(Config.MinionType minionType) throws IllegalHeroValueException, IllegalFundsStateException, MinionException {
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

    public void sellMinion(int minionId) throws IllegalHeroValueException, InvalidMinionIDException {
        isValidId(minionId);
        int price = getMinion(minionId).getPrice();
        removeMinion(minionId);
        increaseFunds(price);
    }

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

    public Set<Integer> getAllMinionIds() {
        Set<Integer> minionIdSet = new HashSet<>();
        for(MinionBase minion : minionList) {
            minionIdSet.add(minion.getId());
        }
        return minionIdSet;
    }

    public Config.MinionType getMinionType(int minionId) throws InvalidMinionIDException {
        isValidId(minionId);
        return getMinion(minionId).getType();
    }

    public ArrayList<MinionBase> getMinionList() {
        return minionList;
    }

    public String getMinionInfoAsString(int minionId) throws InvalidMinionIDException {
        isValidId(minionId);
        return getMinion(minionId).getInfoAsString();
    }

    public String getAllInfoAsString() {
        StringBuilder sb = new StringBuilder();
        for(MinionBase minion : minionList) {
            sb.append(minion.getInfoAsString() + "\n");
        }
        return sb.toString();
    }
}
