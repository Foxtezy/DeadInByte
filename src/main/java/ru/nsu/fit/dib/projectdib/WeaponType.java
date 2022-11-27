package ru.nsu.fit.dib.projectdib;

import javax.swing.text.html.parser.Entity;

/**
 * enumeration of Entities.
 */
public enum WeaponType {
        BOW("player", "arrow", 3),
        AK("ak", "bullet",1);

        private final String name;
        private final String projectile;
        private final int damage;
        WeaponType(String name, String projectile, int damage) {
                this.name = name;
                this.projectile = projectile;
                this.damage = damage;

        }

        public String getName() {
                return name;
        }
        public String getProjectile() {return projectile;}
        public int getDamage(){return damage;}
}
