package ru.nsu.fit.dib.projectdib.environment.levelLoader

import com.almasb.fxgl.entity.GameWorld
import com.almasb.fxgl.entity.level.Level

/**
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
interface LevelLoader {

    fun load(path: String, world: GameWorld): Level
}