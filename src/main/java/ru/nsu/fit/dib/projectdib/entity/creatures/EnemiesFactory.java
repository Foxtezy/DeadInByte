package ru.nsu.fit.dib.projectdib.entity.creatures;

import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

import java.util.ArrayList;
import java.util.List;

public class EnemiesFactory {
	public static Creature getEnemy(EnemyType enemyType){
		List<Integer> specsList = RandomSystem.generateCreatureSpecs(123);
		return switch (enemyType) {
			case Ogre -> new Creature.CreatureBuilder(CreatureRarity.special, "Ogre")
				.setID(0)
				.setSpecs(RandomSystem.d(10),
					specsList.get(0), specsList.get(4), specsList.get(1),
					specsList.get(2), specsList.get(5), specsList.get(3))
				.setStartWeapon(WeaponFactory.Weapons.Hand)
				.setSkill(WeaponType.melee, 5)
				.build();
			case ZOMBIE -> new Creature.CreatureBuilder(CreatureRarity.special, "Zombie")
				.setID(1)
				.setSpecs(RandomSystem.d(5),
					specsList.get(2), specsList.get(3), specsList.get(1),
					specsList.get(4), specsList.get(5), specsList.get(5))
				.setStartWeapon(Weapons.Sword)
				.setSkill(WeaponType.shooting, 2)
				.build();
			case SKELETON -> new Creature.CreatureBuilder(CreatureRarity.special, "Zombie")
				.setID(2)
				.setSpecs(RandomSystem.d(5),
					specsList.get(2), specsList.get(3), specsList.get(1),
					specsList.get(4), specsList.get(5), specsList.get(5))
				.setStartWeapon(WeaponFactory.Weapons.Bow)
				.setSkill(WeaponType.shooting, 3)
				.build();
			default -> throw new NullPointerException("Undeclared enemy type");
		};
	}
	
	
	public enum EnemyType {
		Ogre(CreatureRarity.special),
		SKELETON(CreatureRarity.masterful),
		ZOMBIE(CreatureRarity.ordinary);
		
		private final CreatureRarity creatureRarity;
		
		EnemyType(CreatureRarity creatureRarity) {
			this.creatureRarity = creatureRarity;
		}
		public static List<EnemyType> getAll(CreatureRarity rarity){
			List<EnemyType> enemyTypes = new ArrayList<>();
			for(EnemyType enemyType : EnemyType.values()){
				if(enemyType.getCreatureRarity() == rarity){
					enemyTypes.add(enemyType);
				}
			}
			return enemyTypes;
		}
		public CreatureRarity getCreatureRarity() {return creatureRarity;}
	}
}
