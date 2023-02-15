package ru.nsu.fit.dib.projectdib.data;

/**
 * Статы персонажа.
 */
public class HeroSpecs {
        private String name;
        private String mainWeapon;
        private String secondWeapon;
        private double speed = 250;
        private String playerImage;

        public HeroSpecs(String name, String firstWeapon, String secondWeapon, Double speed, String playerImage){
                this.name = name;
                this.mainWeapon = firstWeapon;
                this.secondWeapon = secondWeapon;
                this.speed = speed;
                this.playerImage = playerImage;
        }

        public String getPlayerImage() {return playerImage;}
        public Double getSpeed() {return speed;}
        public String getMainWeapon() {
                return mainWeapon;
        }
        public String getSecondWeapon() {
                return secondWeapon;
        }

        public void setMainWeapon(String newWeapon) {
                mainWeapon = newWeapon;
        }
        public void setSecondWeapon(String newWeapon) {
                secondWeapon = newWeapon;
        }

}
