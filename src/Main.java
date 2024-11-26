
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        /* 1.Написать функцию, создающую резервную копию всех
        файлов в директории(без поддиректорий) во вновь созданную папку ./backup */
        backup();

        /* 2. Предположить, что числа в исходном массиве из 9 элементов имеют диапазон
        [0,3], и представляют собой, например, состояния ячеек поля для игры в крестикинолики,
        где 0 – это пустое поле, 1 – это поле с крестиком, 2 – это поле с ноликом, 3– резервное значение.
         Такое предположение позволит хранить в одном числе типа int всё поле 3х3. Записать в файл
         9 значений так, чтобы они заняли три байта.*/
        saveToFile("array.out");

        /* 3. Прочитать числа из файла, полученного в результате выполнения задания 2.*/
        readFromFile("array.out");
    }

    private static void backup(){
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();
        try {
            Files.createDirectory(Path.of("./backup"));
        } catch (IOException e) {
            System.out.println("Папка уже создана, содержимое будет заменено");
        }
        Path destDir = Paths.get("./backup");
        if (listOfFiles != null)
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    try {
                        Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        System.out.println("Копирование (из главной дериктории, без учета поддерикторий, в папку 'Backup') завершено успешно");
    }

    private static void saveToFile(String path) {
        int[] ar2 = {0,1,3,1,2,1,0,1,3};
        try {
            FileOutputStream fos = new FileOutputStream(path);
            for (int b = 0; b < 3; b++) {
                byte wr = 0;
                for (int v = 0; v < 3; v++) {
                    wr += (byte) (ar2[3 * b + v] << (v * 2));
                }
                fos.write(wr);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void readFromFile(String path){
        int[] array = new int[9];
        try {
            FileInputStream inputStream = new FileInputStream(path);
            int b;
            int i = 0;
            while ((b = inputStream.read()) != -1) {
                for (int v = 0; v < 3; ++v) {
                    array[i++] = b >> (v * 2) & 0x3;
                }
            }
            inputStream.close();
            System.out.println(Arrays.toString(array));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}