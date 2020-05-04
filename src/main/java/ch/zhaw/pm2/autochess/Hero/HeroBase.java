package ch.zhaw.pm2.autochess.Hero;

import ch.zhaw.pm2.autochess.MINION_TYP_MOCK;

import java.util.*;

public abstract class HeroBase {

    public enum HeroTyp {
        ALIEN, ENGINEER, SPACE_MARINE
    }

    private static final int MAX_HEALTH = 100;
    private static final int START_FUNDS = 10;
    private static final int MAX_FUNDS = 100;
    private static int counterId = 0;

    private ArrayList<MinionMOCK> minionList = new ArrayList<>();

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


    public void increaseHealth(int value) throws IllegalArgumentException{
        if(value > 0 && value < MAX_HEALTH*10) {
            int newHealth = health + value;
            if(newHealth <= MAX_HEALTH) {
                health = newHealth;
            } else {
                health = MAX_HEALTH;
            }
        } else {
            throw new IllegalArgumentException("Given value invalid: Negative or greater allowed max");
        }
    }

    public void decreaseHealth(int value) throws IllegalArgumentException{
        if(value > 0 && value < MAX_HEALTH*10) {
            int newHealth = health - value;
            if(newHealth >= 0) {
                health = newHealth;
            } else {
                health = 0;
            }
        } else {
            throw new IllegalArgumentException("Given value invalid: Negative or greater allowed max");
        }
    }

    public void increaseFunds(int value) throws IllegalArgumentException{
        if(value > 0 && value < MAX_FUNDS*10) {
            int newFunds = funds + value;
            if(newFunds <= MAX_FUNDS) {
                funds = newFunds;
            } else {
                funds = MAX_FUNDS;
            }
        } else {
            throw new IllegalArgumentException("Given value invalid: Negative or greater allowed max");
        }
    }

    public void decreaseFunds(int value) throws IllegalArgumentException{
        if(value > 0 && value < MAX_FUNDS*10) {
            int newFunds = funds - value;
            if(newFunds >= 0) {
                funds = newFunds;
            } else {
                throw new IllegalArgumentException("Funds not available");
            }
        } else {
            throw new IllegalArgumentException("Given value invalid: Negative or greater allowed max");
        }
    }

    //*********************
    //Minion Methods
    //*********************

    public void buyMinion(MINION_TYP_MOCK minion_typMOCK) throws IllegalArgumentException{
        if(minion_typMOCK == null) {
            throw new IllegalArgumentException();
        } else {
            decreaseFunds(minion_typMOCK.getPrice());
            addMinion(minion_typMOCK);
        }
    }

    private void addMinion(MINION_TYP_MOCK minion_typMOCK) throws IllegalArgumentException{
       minionList.add(MINION_TYP_MOCK.getMinion());
    }

    public void sellMinion(int minionId) {
        if(isValidId(minionId)) {
            MINION_TYP_MOCK minionTyp = getMinionTyp(minionId);
            removeMinion(minionId);
            increaseFunds(minionTyp.getPrice());
        } else {
            throw new IllegalArgumentException("Not a valid Id");
        }
    }

    private MinionMOCK getMinion(int minionId) {
        for(MinionMOCK minion : minionList) {
            if(minion.getId() == minionId) {
                return minion;
            }
        }
        return null;
    }

    private boolean isValidId(int minionId) {
        boolean foundStatus = false;
        for(MinionMOCK minion : minionList) {
            if(minion.getId() == minionId) {
                return true;
            }
        }
        return false;
    }

    private void removeMinion(int minionId) throws IllegalArgumentException{
        if(isValidId(minionId)) {
            for (Iterator<MinionMOCK> it = minionList.iterator(); it.hasNext(); ) {
                MinionMOCK minion = it.next();
                if (minion.getId() == minionId) {
                    it.remove();
                }
            }
        }else {
            throw new IllegalArgumentException("not a valid Id");
        }
    }

    public Set<Integer> getAllMinionIds() {
        Set<Integer> minionIdSet = new HashSet<>();
        for(MinionMOCK minion : minionList) {
            minionIdSet.add(minion.getId());
        }
        return minionIdSet;
    }

    public MINION_TYP_MOCK getMinionTyp(int minionId) throws IllegalArgumentException {
        if(isValidId(minionId)) {
            return getMinion(minionId).getMinionTyp();
        } else {
            throw new IllegalArgumentException("no minion found");
        }
    }

    public int getMinionLevel(int minionId) throws IllegalArgumentException {
        if (isValidId(minionId)) {
            return getMinion(minionId).getLevel();
        } else {
            throw new IllegalArgumentException("no minion found");
        }
    }

    public void increaseMinionLevel(int minionId) throws IllegalArgumentException {
        if(isValidId(minionId)) {
            getMinion(minionId).increaseLevel();
            //decreaseFunds();
        } else {
            throw new IllegalArgumentException("no minion found");
        }
    }

    public void decreaseMinionLevel(int minionId) throws IllegalArgumentException {
        if(isValidId(minionId)) {
            getMinion(minionId).decreaseLevel();
            //increaseFunds();
        } else {
            throw new IllegalArgumentException("no minion found");
        }
    }

    public ArrayList<MinionMOCK> getMinionList() {
        return minionList;
    }
}
