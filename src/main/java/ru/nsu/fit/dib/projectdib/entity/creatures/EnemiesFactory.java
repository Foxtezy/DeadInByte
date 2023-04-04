package ru.nsu.fit.dib.projectdib.entity.creatures;

import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

import java.util.ArrayList;
import java.util.List;

public class EnemiesFactory {
	public static Creature newEnemy(EnemyType enemyType, Integer seed){
		List<Integer> specsList = RandomSystem.generateCreatureSpecs(123);
		return switch (enemyType) {
			case Isaac -> new Creature.CreatureBuilder(CreatureRarity.special, "Isaac")
				.setID(0)
				.setSpecs(RandomSystem.d(10),
					specsList.get(0), specsList.get(4), specsList.get(1),
					specsList.get(2), specsList.get(5), specsList.get(3))
				.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Hand))
				.setSkill(WeaponType.melee, 5)
				.build();
			case EvilIsaac -> new Creature.CreatureBuilder(CreatureRarity.special, "EvilIsaac")
				.setID(1)
				.setSpecs(RandomSystem.d(5),
					specsList.get(2), specsList.get(3), specsList.get(1),
					specsList.get(4), specsList.get(5), specsList.get(5))
				.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Sword))
				.setSkill(WeaponType.shooting, 2)
				.build();
			case LightMan -> new Creature.CreatureBuilder(CreatureRarity.special, "LightMan")
				.setID(2)
				.setSpecs(RandomSystem.d(5),
					specsList.get(2), specsList.get(3), specsList.get(1),
					specsList.get(4), specsList.get(5), specsList.get(5))
				.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Bow))
				.setSkill(WeaponType.shooting, 3)
				.build();
			case Mud -> new Creature.CreatureBuilder(CreatureRarity.special, "Mud")
					.setID(3)
					.setSpecs(RandomSystem.d(5),
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Hand))
					.setSkill(WeaponType.melee, 3)
					.build();
			case LongHead -> new Creature.CreatureBuilder(CreatureRarity.special, "LongHead")
					.setID(5)
					.setSpecs(RandomSystem.d(5),
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Bow))
					.setSkill(WeaponType.fencing, 3)
					.build();
			case Mask -> new Creature.CreatureBuilder(CreatureRarity.special, "Mask")
					.setID(6)
					.setSpecs(RandomSystem.d(5),
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Bow))
					.setSkill(WeaponType.fencing, 3)
					.build();
			case Boar -> new Creature.CreatureBuilder(CreatureRarity.special, "Boar")
					.setID(7)
					.setSpecs(RandomSystem.d(5),
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Bow))
					.setSkill(WeaponType.melee, 3)
					.build();
			case Shaman -> new Creature.CreatureBuilder(CreatureRarity.special, "Shaman")
					.setID(8)
					.setSpecs(RandomSystem.d(5),
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Bow))
					.setSkill(WeaponType.fencing, 3)
					.build();
			case Invisible -> new Creature.CreatureBuilder(CreatureRarity.special, "Invisible")
					.setID(9)
					.setSpecs(RandomSystem.d(5),
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Bow))
					.setSkill(WeaponType.magic, 3)
					.build();
			case Chel -> new Creature.CreatureBuilder(CreatureRarity.special, "Chel")
					.setID(10)
					.setSpecs(RandomSystem.d(5),
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Bow))
					.setSkill(WeaponType.melee, 3)
					.build();
			case Devil -> new Creature.CreatureBuilder(CreatureRarity.special, "Devil")
					.setID(11)
					.setSpecs(RandomSystem.d(5),
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Bow))
					.setSkill(WeaponType.fencing, 3)
					.build();
			case Angel -> new Creature.CreatureBuilder(CreatureRarity.special, "Angel")
					.setID(12)
					.setSpecs(RandomSystem.d(5),
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Bow))
					.setSkill(WeaponType.magic, 3)
					.build();
			case GreenIsaac -> new Creature.CreatureBuilder(CreatureRarity.special, "GreenIsaac")
					.setID(13)
					.setSpecs(RandomSystem.d(5),
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setStartWeapon(WeaponFactory.getWeapon(WeaponFactory.Weapons.Bow))
					.setSkill(WeaponType.melee, 3)
					.build();

			default -> throw new NullPointerException("Undeclared enemy type");
		};
	}
	
	// ordinary = masterful = 0.45
	// mediocre = 0.3
	// special = 0.2
	// legendary = 0.05
	public enum EnemyType {
		Isaac(CreatureRarity.masterful),
		EvilIsaac(CreatureRarity.mediocre),
		LightMan(CreatureRarity.ordinary),
		Mud(CreatureRarity.special),
		LongHead(CreatureRarity.masterful),
		Mask(CreatureRarity.special),
		Boar(CreatureRarity.ordinary),
		Shaman(CreatureRarity.special),
		Invisible(CreatureRarity.mediocre),
		Chel(CreatureRarity.special),
		Devil(CreatureRarity.mediocre),
		Angel(CreatureRarity.special),
		GreenIsaac(CreatureRarity.special);
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
