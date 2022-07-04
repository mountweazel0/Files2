import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress[] games = new GameProgress[]{
                new GameProgress(99, 10, 5, 87),
                new GameProgress(78, 8, 7, 50),
                new GameProgress(75, 7, 9, 30)
        };

        String gamePathSave = "D:\\Games\\savegames\\save";
        String gamePathSaveZIP = "D:\\Games\\savegames\\save.zip";

        saveGame(gamePathSave, games);
        File dir = new File("D:\\Games\\savegames");

        File[] fileList = null;

        if (dir.isDirectory()) {
            fileList = dir.listFiles();
        }

        zipFiles(gamePathSaveZIP, fileList);

    }

    static void saveGame(String path, GameProgress[] games) {
        for (int i = 0; i < games.length; i++) {
            try (FileOutputStream fileSave = new FileOutputStream(path + i + ".dat");
                 ObjectOutputStream obj = new ObjectOutputStream(fileSave);) {

                obj.writeObject(games[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static void zipFiles(String gamePathSaveZIP, File[] fileList) {

        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(gamePathSaveZIP))) {
            for (File file : fileList) {
                FileInputStream fis = new FileInputStream(file.getPath());
                ZipEntry zentry = new ZipEntry(file.getName());
                zip.putNextEntry(zentry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zip.write(buffer);
                zip.closeEntry();
                fis.close();
            }
        } catch (IOException e) {
            e.getMessage();
        }

        for (File path : fileList) {
            path.delete();
        }

    }

}