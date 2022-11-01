/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.dib.projectdib.mapperobjects.GameObjectMapper;

class GameObjectMapperTest {

    @Test
    public void test() throws FileNotFoundException {
        GameObjectMapper gameObjectMapper = new GameObjectMapper();
        File file = new File("src/test/resources/input.txt");
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter("");
        char arr[][] = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                arr[i][j] = scanner.next().charAt(0);
            }
            scanner.next();
        }
        System.out.println(gameObjectMapper.makeWalls(arr));
        arr = gameObjectMapper.markWalls();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
