
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
        /* 1.�������� �������, ��������� ��������� ����� ����
        ������ � ����������(��� �������������) �� ����� ��������� ����� ./backup */
        backup();

        /* 2. ������������, ��� ����� � �������� ������� �� 9 ��������� ����� ��������
        [0,3], � ������������ �����, ��������, ��������� ����� ���� ��� ���� � ��������������,
        ��� 0 � ��� ������ ����, 1 � ��� ���� � ���������, 2 � ��� ���� � �������, 3� ��������� ��������.
         ����� ������������� �������� ������� � ����� ����� ���� int �� ���� 3�3. �������� � ����
         9 �������� ���, ����� ��� ������ ��� �����.*/
        saveToFile("array.out");

        /* 3. ��������� ����� �� �����, ����������� � ���������� ���������� ������� 2.*/
        readFromFile("array.out");
    }

    private static void backup(){
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();
        try {
            Files.createDirectory(Path.of("./backup"));
        } catch (IOException e) {
            System.out.println("����� ��� �������, ���������� ����� ��������");
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
        System.out.println("����������� (�� ������� ����������, ��� ����� �������������, � ����� 'Backup') ��������� �������");
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