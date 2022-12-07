package ru.nsu.fit.dib.projectdib.data;

public enum Projectiles {
        ARROW("arrow", 250, 3),
        BULLET("bullet", 500, 1);



        private final String name;
        private final Integer speed;
        private final Integer damage;
        Projectiles(String name, Integer speed, Integer damage) {
                this.name = name;
                this.speed = speed;
                this.damage = damage;
        }
        public String getName() {return name;}
        public Integer getSpeed() {return speed;}
        public Integer getDamage() {return damage;}
        public Projectiles getProjectile(String name){
                switch (name){
                        case("bullet"):
                                return Projectiles.BULLET;
                        case("arrow"):
                                return Projectiles.ARROW;
                }

                return Projectiles.ARROW;
        }
}
