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
        switch(heroType) {
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

    private static int heroCounter = 1;
    private final int heroId;
    private int health;
    private final int maxHealth;
    private int funds;
    private Config.HeroType heroType;
    protected boolean abilityAvailable = true;

    public HeroBase(int health, int funds, Config.HeroType heroType) throws IllegalHeroValueException {
        if(areValidParameters(health, funds)) {
            this.heroId = heroCounter;
            heroCounter++;
            maxHealth = health;
            this.health = maxHealth;
            this.funds = funds;
            this.heroType = heroType;
        }else {
            throw new IllegalHeroValueException("Not valid hero parameters");
        }
    }

    private boolean areValidParameters(int health, int funds) {
        boolean isValid = true;
        if(health > Config.MAX_HERO_HEALTH || health < Config.MIN_HERO_HEALTH) { isValid = false;}
        if(funds > Config.MAX_FUNDS || funds < Config.MIN_FUNDS) {isValid = false;}
        return isValid;
    }

    public HeroBase() {
        heroId = heroCounter;
        heroCounter++;
        maxHealth = Config.MAX_HERO_HEALTH;
        health = Config.MAX_HERO_HEALTH;
        funds = Config.DEFAULT_START_FUNDS;
    }

    //****************
    //Hero methods
    //****************
    public static int getCounterId() {
        return heroCounter;
    }

    public int getHealth() {
        return health;
    }

    public int getFunds() {
        return funds;
    }

    public int getId() { return heroId;}

    public Config.HeroType getHeroType() {
        return heroType;
    }

    public boolean isAbilityAvailable() {
        return abilityAvailable;
    }


    public void increaseHealth(int value) throws IllegalHeroValueException{
        if(value >= 0 && value < Config.MAX_HERO_HEALTH*10) {
            int newHealth = health + value;
            if(newHealth <= maxHealth) {
                health = newHealth;
            } else {
                health = maxHealth;
            }
        } else {
            throw new IllegalHeroValueException("Illegal value: Negative or greater allowed max");
        }
    }

    public void decreaseHealth(int value) throws IllegalHeroValueException{
        if(value >= 0 && value < Config.MAX_HERO_HEALTH*10) {
            int newHealth = health - value;
            if(newHealth >= 0) {
                health = newHealth;
            } else {
                health = 0;
            }
        } else {
            throw new IllegalHeroValueException("Illegal value: Negative or greater allowed max");
        }
    }

    public void increaseFunds(int value) throws IllegalHeroValueException{
        if(value >= 0 && value < Config.MAX_FUNDS*10) {
            int newFunds = funds + value;
            if(newFunds <= Config.MAX_FUNDS) {
                funds = newFunds;
            } else {
                funds = Config.MAX_FUNDS;
            }
        } else {
            throw new IllegalHeroValueException("Illegal value: Negative or greater allowed max. Value: " + value);
        }
    }

    public void decreaseFunds(int value) throws IllegalHeroValueException, IllegalFundsStateException{
        if(value >= 0 && value < Config.MAX_FUNDS*10) {
            int newFunds = funds - value;
            if(newFunds >= 0) {
                funds = newFunds;
            } else {
                throw new IllegalFundsStateException("Funds not available");
            }
        } else {
            throw new IllegalHeroValueException("Illegal value: Negative or greater allowed max");
        }
    }

    public static void resetCounter() {
        heroCounter = 0;
    }

    public abstract void doAbility() throws InvalidMinionAttributeModifierException, IllegalHeroValueException;

    public abstract void buffMinion(MinionBase minion) throws InvalidMinionAttributeModifierException;

    //*********************
    //Minion Methods
    //*********************

    public void buyMinion(Config.MinionType minionType) throws IllegalHeroValueException, IllegalFundsStateException, MinionException {
        if(isValidMinionType(minionType)) {
            MinionBase minion = MinionBase.newMinionFromType(minionType, heroId);
            decreaseFunds(minion.getPrice());
            buffMinion(minion);
            minionList.add(minion);
        }else {
            throw new InvalidMinionTypeException("Not a valid Minion type");
        }
    }

    private boolean isValidMinionType(Config.MinionType minionType) {
        for(Config.MinionType minType : Config.MinionType.values()) {
            if(minType.equals(minionType)) {
                return true;
            }
        }
        return false;
    }

    public void sellMinion(int minionId) throws IllegalHeroValueException, InvalidMinionIDException{
        if(isValidId(minionId)) {
            int price = getMinion(minionId).getPrice();
            removeMinion(minionId);
            increaseFunds(price);
        } else {
            throw new InvalidMinionIDException("Not a valid Minion ID");
        }
    }

    public MinionBase getMinion(int minionId) throws InvalidMinionIDException{
        if(isValidId(minionId)) {
            for(MinionBase minion : minionList) {
                if(minion.getId() == minionId) {
                    return minion;
                }
            }
            return null;
        } else {
            throw new InvalidMinionIDException("Not a valid Minion ID");
        }
    }

    private boolean isValidId(int minionId) {
        for(MinionBase minion : minionList) {
            if(minion.getId() == minionId) {
                return true;
            }
        }
        return false;
    }

    private void removeMinion(int minionId) throws InvalidMinionIDException{
        if(isValidId(minionId)) {
            for (Iterator<MinionBase> it = minionList.iterator(); it.hasNext(); ) {
                MinionBase minion = it.next();
                if (minion.getId() == minionId) {
                    it.remove();
                }
            }
        }else {
            throw new InvalidMinionIDException("Not a valid Minion ID");
        }
    }

    public Set<Integer> getAllMinionIds() {
        Set<Integer> minionIdSet = new HashSet<>();
        for(MinionBase minion : minionList) {
            minionIdSet.add(minion.getId());
        }
        return minionIdSet;
    }

    public Config.MinionType getMinionType(int minionId) throws InvalidMinionIDException {
        if(isValidId(minionId)) {
             return getMinion(minionId).getType();
        } else {
            throw new InvalidMinionIDException("Not a valid Minion ID");
        }
    }

    public int getMinionLevel(int minionId) throws InvalidMinionIDException {
        if (isValidId(minionId)) {
            return getMinion(minionId).getLevel();
        } else {
            throw new InvalidMinionIDException("Not a valid Minion ID");
        }
    }

    public void increaseMinionLevel(int minionId) throws InvalidMinionIDException {
        if(isValidId(minionId)) {
            getMinion(minionId).modifyLevel(1);
            //todo: decreaseFunds();
        } else {
            throw new InvalidMinionIDException("Not a valid Minion ID");
        }
    }

    public void decreaseMinionLevel(int minionId) throws InvalidMinionIDException {
        if(isValidId(minionId)) {
            getMinion(minionId).modifyLevel(-1);
            //todo: increaseFunds();
        } else {
            throw new InvalidMinionIDException("Not a valid Minion ID");
        }
    }

    public ArrayList<MinionBase> getMinionList() {
        return minionList;
    }

    public String getMinionInfoAsString(int minionId) throws InvalidMinionIDException {
        if(isValidId(minionId)) {
            return getMinion(minionId).getInfoAsString();
        } else {
            throw new InvalidMinionIDException("Not a valid Minion ID");
        }
    }

    public String getAllInfoAsString() {
        StringBuilder sb = new StringBuilder();
        for(MinionBase minion : minionList) {
            sb.append(minion.getInfoAsString() + "\n");
        }
        return sb.toString();
    }
}
