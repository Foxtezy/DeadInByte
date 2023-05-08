package ru.nsu.fit.dib.projectdib.entity.creatures;

import ru.nsu.fit.dib.projectdib.EntityType;
import ru.nsu.fit.dib.projectdib.data.Projectiles;
import ru.nsu.fit.dib.projectdib.entity.creatures.EnemiesFactory.EnemyType;
import ru.nsu.fit.dib.projectdib.entity.creatures.HeroesFactory.HeroType;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;

public class TypeChooser {
  public static EntityType getTypeByString(String str){
    HeroType heroType = HeroType.getByName(str);
    EnemyType enemyType = EnemyType.getByName(str);
    Weapons weapons = Weapons.getByName(str);
    Projectiles projectiles = Projectiles.getByName(str);
    if (projectiles!=null){return EntityType.PROJECTILE;}
    if (heroType!=null){return EntityType.PLAYER;}
    if (enemyType!=null){return EntityType.ENEMY;}
    if (weapons!=null){return EntityType.WEAPON;}

    throw new IllegalArgumentException("Type not found: "+str);
  }
}
