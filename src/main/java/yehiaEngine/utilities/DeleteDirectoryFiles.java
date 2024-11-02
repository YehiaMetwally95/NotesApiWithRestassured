package yehiaEngine.utilities;

import java.io.File;

public class DeleteDirectoryFiles {

    public static void deleteFiles(File dirPath) {
        File filesList[] = dirPath.listFiles();
        if (filesList != null) {
            for(File file : filesList) {
                if (!file.getName().equals("file.gitkeep")) {
                    if (file.isFile()) {
                        file.delete();
                    } else {
                        deleteFiles(file);
                    }
                }
            }
        }
    }
}
