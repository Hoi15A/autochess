package ch.zhaw.pm2.autochess.Hero;

public abstract class HeroBase {

    public enum HeroTyp {
        ALIEN, ENGINEER, SPACE_MARINE
    }

    private static final int MAX_HEALTH = 10;
    private static final int START_FUNDS = 10;
    private static final int MAX_FUNDS = 100;
    private static int counterId = 0;

    public static int getCounterId() {
        return counterId;
    }

    public int getHealth() {
        return health;
    }

    public int getAvailableFunds() {
        return availableFunds;
    }

    private final int heroId;
    private int health;
    private int roundFunds;
    private int availableFunds;

    public HeroBase() {
        heroId = counterId;
        counterId++;
        health = MAX_HEALTH;
        roundFunds = START_FUNDS;
        availableFunds = roundFunds;
    }

    public int getHeroId() { return heroId;}

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

    public void increaseAvailableFunds(int value) throws IllegalArgumentException{
        if(value > 0 && value < MAX_FUNDS*10) {
            int newFunds = availableFunds + value;
            if(newFunds <= roundFunds) {
                availableFunds = newFunds;
            } else {
                availableFunds = roundFunds;
            }
        } else {
            throw new IllegalArgumentException("Given value invalid: Negative or greater allowed max");
        }
    }

    public void decreaseAvailableFunds(int value) throws IllegalArgumentException{
        if(value > 0 && value < MAX_FUNDS*10) {
            int newFunds = availableFunds - value;
            if(newFunds >= 0) {
                availableFunds = newFunds;
            } else {
                throw new IllegalArgumentException("Funds not available");
            }
        } else {
            throw new IllegalArgumentException("Given value invalid: Negative or greater allowed max");
        }
    }

    public void setRoundFunds(int value) {
        if(value > 0 && value < MAX_FUNDS*10) {
            if(value <= MAX_FUNDS) {
                roundFunds = value;
            } else {
                roundFunds = MAX_FUNDS;
            }
        } else {
            throw new IllegalArgumentException("Given value invalid: Negative or greater allowed max");
        }
    }

    public void resetRoundFunds() {
        availableFunds = roundFunds;
    }




}
