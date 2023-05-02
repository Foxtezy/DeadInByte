package ru.nsu.fit.dib.projectdib.entity.creatures;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import ru.nsu.fit.dib.projectdib.RandomSystem;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory;
import ru.nsu.fit.dib.projectdib.entity.weapons.WeaponFactory.Weapons;
import ru.nsu.fit.dib.projectdib.entity.weapons.enums.WeaponType;

import java.util.ArrayList;
import java.util.List;

public class EnemiesFactory {
	public static Creature newEnemy(EnemyType enemyType, Integer seed){
		List<Integer> specsList = RandomSystem.generateCreatureSpecs(123);
		return switch (enemyType) {
			case Isaac -> new CreatureBuilder("Isaac",enemyType)
					.setHP(RandomSystem.d(10))
				.setSpecs(
					specsList.get(0), specsList.get(4), specsList.get(1),
					specsList.get(2), specsList.get(5), specsList.get(3))
				.setWeapon(WeaponFactory.Weapons.Hand)
				.setSkill(WeaponType.melee, 5)
				.build();
			case EvilIsaac -> new CreatureBuilder("EvilIsaac",enemyType)
					.setHP(RandomSystem.d(5))
				.setSpecs(
					specsList.get(2), specsList.get(3), specsList.get(1),
					specsList.get(4), specsList.get(5), specsList.get(5))
				.setWeapon(Weapons.Sword)
				.setSkill(WeaponType.shooting, 2)
				.build();
			case LightMan -> new CreatureBuilder("LightMan",enemyType)
					.setHP(RandomSystem.d(5))
				.setSpecs(
					specsList.get(2), specsList.get(3), specsList.get(1),
					specsList.get(4), specsList.get(5), specsList.get(5))
				.setWeapon(WeaponFactory.Weapons.Bow)
				.setSkill(WeaponType.shooting, 3)
				.build();
			case Mud -> new CreatureBuilder( "Mud",enemyType)
					.setHP(RandomSystem.d(5))
					.setSpecs(
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setWeapon(WeaponFactory.Weapons.Hand)
					.setSkill(WeaponType.melee, 3)
					.build();
			case LongHead -> new CreatureBuilder("LongHead",enemyType)
					.setHP(RandomSystem.d(5))
					.setSpecs(
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setWeapon(WeaponFactory.Weapons.Bow)
					.setSkill(WeaponType.fencing, 3)
					.build();
			case Mask -> new CreatureBuilder("Mask",enemyType)
					.setHP(RandomSystem.d(5))
					.setSpecs(
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setWeapon(WeaponFactory.Weapons.Bow)
					.setSkill(WeaponType.fencing, 3)
					.build();
			case Boar -> new CreatureBuilder("Boar",enemyType)
					.setHP(RandomSystem.d(5))
					.setSpecs(
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setWeapon(WeaponFactory.Weapons.Bow)
					.setSkill(WeaponType.melee, 3)
					.build();
			case Shaman -> new CreatureBuilder("Shaman",enemyType)
					.setHP(RandomSystem.d(5))
					.setSpecs(
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setWeapon(WeaponFactory.Weapons.Bow)
					.setSkill(WeaponType.fencing, 3)
					.build();
			case Invisible -> new CreatureBuilder( "Invisible",enemyType)
					.setHP(RandomSystem.d(5))
					.setSpecs(
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setWeapon(WeaponFactory.Weapons.Bow)
					.setSkill(WeaponType.magic, 3)
					.build();
			case Chel -> new CreatureBuilder("Chel",enemyType)
					.setHP(RandomSystem.d(5))
					.setSpecs(
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setWeapon(WeaponFactory.Weapons.Bow)
					.setSkill(WeaponType.melee, 3)
					.build();
			case Devil -> new CreatureBuilder("Devil",enemyType)
					.setHP(RandomSystem.d(5))
					.setSpecs(
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setWeapon(WeaponFactory.Weapons.Hand)
					.setSkill(WeaponType.melee, 3)
					.build();
			case Angel -> new CreatureBuilder("Angel",enemyType)
					.setHP(RandomSystem.d(5))
					.setSpecs(
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setWeapon(WeaponFactory.Weapons.Staff)
					.setSkill(WeaponType.magic, 3)
					.build();
			case GreenIsaac -> new CreatureBuilder("GreenIsaac",enemyType)
					.setHP(RandomSystem.d(5))
					.setSpecs(
							specsList.get(2), specsList.get(3), specsList.get(1),
							specsList.get(4), specsList.get(5), specsList.get(5))
					.setWeapon(WeaponFactory.Weapons.Bow)
					.setSkill(WeaponType.melee, 3)
					.build();
			default -> throw new NullPointerException("Undeclared enemy type");
		};
	}
	
	// ordinary = masterful = 0.45
	// mediocre = 0.3
	// special = 0.2
	// legendary = 0.05
	public enum EnemyType implements CreatureType{
		Isaac(CreatureRarity.masterful,0),
		EvilIsaac(CreatureRarity.mediocre,1),
		LightMan(CreatureRarity.ordinary,2),
		Mud(CreatureRarity.special,3),
		LongHead(CreatureRarity.masterful,4),
		Mask(CreatureRarity.special,5),
		Boar(CreatureRarity.ordinary,6),
		Shaman(CreatureRarity.special,7),
		Invisible(CreatureRarity.mediocre,8),
		Chel(CreatureRarity.special,9),
		Devil(CreatureRarity.mediocre,10),
		Angel(CreatureRarity.special,11),
		GreenIsaac(CreatureRarity.special,12);
		private final CreatureRarity creatureRarity;

		public int getID() {
			return id;
		}

		@Override
		public String getSubclass() {
			return "enemy";
		}

		private final int id;

		public String getName() {
			return name;
		}

		private String name;

		EnemyType(CreatureRarity creatureRarity,int id) {
			this.creatureRarity = creatureRarity;
			name=this.toString();
			this.id = id;
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
		private static final Map<String, EnemyType> map = Arrays.stream(values())
				.collect(
						Collectors.toMap(orderStatus -> orderStatus.name, orderStatus -> orderStatus));
		public static EnemyType getByName(String entityType) {
			return map.get(entityType);
		}

		public CreatureRarity getCreatureRarity() {return creatureRarity;}
	}
}
