package ch.zhaw.pm2.autochess;

public class HeroBase {



    public int getHeroId() {
        return 0;
    }

    public void decreaseAvailableFunds(int amount) throws IllegalArgumentException{

    }

    public int getFunds() {
        return 0;
    }

    public enum HERO_TYP {
        ALIEN;

        public static HeroBase getHeroFromType(HERO_TYP hero_typ) throws IllegalArgumentException{
            return new Alien();
        }
    }
}
