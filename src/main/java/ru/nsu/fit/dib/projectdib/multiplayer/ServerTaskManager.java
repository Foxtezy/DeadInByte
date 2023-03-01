package ru.nsu.fit.dib.projectdib.multiplayer;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.IDComponent;
import javafx.geometry.Point2D;
import ru.nsu.fit.dib.projectdib.entity.moving.components.PlayerMovingComponent;
import ru.nsu.fit.dib.projectdib.multiplayer.data.EntityState;
import ru.nsu.fit.dib.projectdib.multiplayer.data.GameStatePacket;
import ru.nsu.fit.dib.projectdib.multiplayer.data.NewEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class ServerTaskManager {
	private Map<Integer, Entity> idServerHashTable;
	private List<NewEntity> listNewEntities;
	private List<EntityState> listEntityState;
	
	public ServerTaskManager() {
		idServerHashTable = new HashMap<>();
	}
	
	public void mySpawn(String name, Point2D pos) {
		SpawnData spawnData = new SpawnData(pos);
		Entity spawned = spawn(name, pos);
		int newId = spawned.getComponent(IDComponent.class).getId();
		NewEntity spawnedNewEntity = new NewEntity(newId, name, spawnData);
		listNewEntities.add(spawnedNewEntity);
		EntityState spawnedEntityState = new EntityState(newId, spawned.getPosition(), spawned.getComponent(PlayerMovingComponent.class).getMouseVelocity());
		listEntityState.add(spawnedEntityState);
		idServerHashTable.put(newId, spawned);
	}
	
	private void updateListEntityState() {
		listEntityState.parallelStream().forEach(entState -> {
			int id = entState.getId();
			entState.setCoordinate(idServerHashTable.get(id).getPosition());
			entState.setAngle(idServerHashTable.get(id).getComponent(PlayerMovingComponent.class).getMouseVelocity());
		});
	}
	
	public GameStatePacket makePacket() {
		this.updateListEntityState();
		GameStatePacket packet = new GameStatePacket(listNewEntities, listEntityState);
		listNewEntities.clear();
		return packet;
	}
}
