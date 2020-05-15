package ch.zhaw.pm2.autochess;

public final class Config {

    //Game
    public static final int HERO_ID_1 = 1;
    public static final int HERO_ID_2 = 2;

    //BoardManager
    public static final int BOARD_HEIGHT = 8;
    public static final int BOARD_WIDTH =8;
    public static final int MAX_MINIONS_ON_BOARD = 8;
    public static final int MAX_BATTLE_LOOPS = 20;
    public static final int PLACE_ZONE_HEIGHT = 2;

    //HeroBase
    public enum HeroType {
        ALIEN, ENGINEER, SPACE_MARINE;
    }
    public static final int MAX_HERO_HEALTH = 100;
    public static final int MIN_HERO_HEALTH = 1;
    public static final int DEFAULT_START_FUNDS = 100;
    public static final int MAX_FUNDS = 100;
    public static final int MIN_FUNDS = 0;

    //HeroAlien
    public static final int ALIEN_HEALTH = 5;
    public static final int ALIEN_START_FUNDS = 100;
    public static final int ALIEN_ABILITY_MOVE_RANGE = 0;
    public static final int ALIEN_BUFF_ATTACK = 0;
    public static final int ALIEN_BUFF_DEF = 0;

    //HeroEngineer
    public static final int ENG_HEALTH = 5;
    public static final int ENG_START_FUNDS = 100;
    public static final int ENG_ABILITY_DEF = 0;
    public static final int ENG_BUFF_DEF = 0;
    public static final int ENG_BUFF_HP = 0;

    //HeroSpaceMarine
    public static final int MARINE_HEALTH = 5;
    public static final int MARINE_START_FUNDS = 100;
    public static final int MARINE_ABILITY_ATTACK = 0;
    public static final int MARINE_BUFF_MOVE_RANGE = 0;
    public static final int MARINE_BUFF_ATTACK_RANGE = 0;

    //MinionBase

    public enum MinionType {
        WARRIOR, RANGER, TANK,
    }

    public static final int MAX_MINION_HEALTH = 100;
    public static final int MIN_MINION_HEALTH = 0;
    public static final int MAX_MINION_ATTACK = 30;
    public static final int MIN_MINION_ATTACK = 0;
    public static final int MAX_MINION_DEFENSE = 10;
    public static final int MIN_MINION_DEFENSE = 0;
    public static final int MAX_MINION_MOVEMENT_RANGE = 10;
    public static final int MIN_MINION_MOVEMENT_RANGE = 1;
    public static final int MAX_MINION_ATTACK_RANGE = 10;
    public static final int MIN_MINION_ATTACK_RANGE = 1;
    public static final int MAX_MINION_AGILITY = 10;
    public static final int MIN_MINION_AGILITY = 0;
    public static final int MAX_MINION_LEVEL = 3;
    public static final int MAX_MINION_PRICE = 50;
    public static final int MIN_MINION_PRICE = 0;

    //MinionWarrior
    public static final int WARRIOR_PRICE = 10;
    public static final int WARRIOR_HEALTH = 70;
    public static final int WARRIOR_ATTACK = 10;
    public static final int WARRIOR_DEFENCE = 3;
    public static final int WARRIOR_MOVEMENT_RANGE = 4;
    public static final int WARRIOR_ATTACK_RANGE = 1;
    public static final int WARRIOR_AGILITY = 7;

    //MinionTank
    public static final int TANK_PRICE = 15;
    public static final int TANK_HEALTH = 100;
    public static final int TANK_ATTACK = 5;
    public static final int TANK_DEFENCE = 8;
    public static final int TANK_MOVEMENT_RANGE = 2;
    public static final int TANK_ATTACK_RANGE = 1;
    public static final int TANK_AGILITY = 5;

    //MinionTank
    public static final int RANGER_PRICE = 20;
    public static final int RANGER_HEALTH = 40;
    public static final int RANGER_ATTACK = 20;
    public static final int RANGER_DEFENCE = 0;
    public static final int RANGER_MOVEMENT_RANGE = 3;
    public static final int RANGER_ATTACK_RANGE = 4;
    public static final int RANGER_AGILITY = 3;
}
