package ch.zhaw.pm2.autochess.Hero;

import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalFundsStateException;
import ch.zhaw.pm2.autochess.Hero.exceptions.IllegalHeroValueException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidHeroTypeException;
import ch.zhaw.pm2.autochess.Hero.exceptions.InvalidMinionIDException;
import ch.zhaw.pm2.autochess.Minion.MinionBase;
import ch.zhaw.pm2.autochess.Minion.MinionType;
import ch.zhaw.pm2.autochess.Minion.exceptions.InvalidMinionTypeException;

import java.util.*;

public abstract class HeroBase {

    public enum HeroType {
        ALIEN, ENGINEER, SPACE_MARINE;

        public static HeroBase getHeroFromType(HeroType heroType) throws InvalidHeroTypeException{
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
    }

    private static final int MAX_HEALTH = 100;
    private static final int START_FUNDS = 10;
    private static final int MAX_FUNDS = 100;
    private static int counterId = 0;

    private ArrayList<MinionBase> minionList = new ArrayList<>();

    private final int heroId;
    private int health;
    private int funds;

    public HeroBase(int health, int funds) {
        heroId = counterId;
        counterId++;
        this.health = health;
        this.funds = funds;
    }

    public HeroBase() {
        heroId = counterId;
        counterId++;
        health = MAX_HEALTH;
        funds = START_FUNDS;
    }

    //****************
    //Hero methods
    //****************
    public static int getCounterId() {
        return counterId;
    }

    public int getHealth() {
        return health;
    }

    public int getFunds() {
        return funds;
    }

    public int getId() { return heroId;}


    public void increaseHealth(int value) throws IllegalHeroValueException{
        if(value > 0 && value < MAX_HEALTH*10) {
            int newHealth = health + value;
            if(newHealth <= MAX_HEALTH) {
                health = newHealth;
            } else {
                health = MAX_HEALTH;
            }
        } else {
            throw new IllegalHeroValueException("Illegal value: Negative or greater allowed max");
        }
    }

    public void decreaseHealth(int value) throws IllegalHeroValueException{
        if(value > 0 && value < MAX_HEALTH*10) {
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
        if(value > 0 && value < MAX_FUNDS*10) {
            int newFunds = funds + value;
            if(newFunds <= MAX_FUNDS) {
                funds = newFunds;
            } else {
                funds = MAX_FUNDS;
            }
        } else {
            throw new IllegalHeroValueException("Illegal value: Negative or greater allowed max");
        }
    }

    public void decreaseFunds(int value) throws IllegalHeroValueException, IllegalFundsStateException{
        if(value > 0 && value < MAX_FUNDS*10) {
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
        counterId = 0;
    }

    //*********************
    //Minion Methods
    //*********************

    public void buyMinion(MinionType minionType) throws IllegalHeroValueException, IllegalFundsStateException, InvalidMinionTypeException{
        if(isValidMinionType(minionType)) {
            decreaseFunds(minionType.getPrice());
            minionList.add(MinionType.getMinionFromType(minionType));
        }else {
            throw new InvalidMinionTypeException("Not a valid Minion type");
        }
    }

    private boolean isValidMinionType(MinionType minionType) {
        for(MinionType minType : MinionType.values()) {
            if(minType.equals(minionType)) {
                return true;
            }
        }
        return false;
    }

    public void sellMinion(int minionId) throws IllegalHeroValueException, InvalidMinionIDException{
        if(isValidId(minionId)) {
            int price = getMinion(minionId).getType().getPrice();

            removeMinion(minionId);
            increaseFunds(price);
        } else {
            throw new InvalidMinionIDException("Not a valid Minion ID");
        }
    }

    private MinionBase getMinion(int minionId) {
        for(MinionBase minion : minionList) {
            if(minion.getId() == minionId) {
                return minion;
            }
        }
        return null;
    }

    private boolean isValidId(int minionId) {
        boolean foundStatus = false;
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

    public MinionType getMinionType(int minionId) throws InvalidMinionIDException {
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
}
