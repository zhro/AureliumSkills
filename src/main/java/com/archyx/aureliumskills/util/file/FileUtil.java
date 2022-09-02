package com.archyx.aureliumskills.util.file;

import com.archyx.aureliumskills.util.math.NumberUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class FileUtil {

    @Nullable
    public static String renameNoDuplicates(@NotNull File file, @NotNull String resultName, @NotNull File directory) {
        // Count duplicates
        int duplicates = 0;
        File[] subFiles = directory.listFiles();
        if (subFiles != null) {
            for (File subFile : subFiles) {
                if (subFile.getName().equals(resultName)) {
                    if (1 > duplicates) {
                        duplicates = 1;
                    }
                    break;
                } else {
                    String baseName = getBaseName(resultName);
                    if (subFile.getName().startsWith(baseName + " (")) {
                        int fileNameNumber = NumberUtil.toInt(subFile.getName().substring(baseName.length() + 2, baseName.length() + 3)) + 1;
                        if (fileNameNumber > duplicates) {
                            duplicates = fileNameNumber;
                        }
                    }
                }
            }
        }
        // Rename old file
        String renamedName;
        if (duplicates == 0) {
            renamedName = resultName;
        } else {
            String resultBase = getBaseName(resultName);
            String resultExtension = getExtension(resultName);
            renamedName = resultBase + " (" + duplicates + ")." + resultExtension;
        }
        if (file.renameTo(new File(file.getParent(), renamedName))) {
            return renamedName;
        } else {
            return null;
        }
    }

    public static String getBaseName(@NotNull String fileName) {
        return fileName.split("\\.(?=[^.]+$)")[0];
    }

    public static String getExtension(@NotNull String fileName) {
        try {
            return fileName.split("\\.(?=[^.]+$)")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return fileName;
        }
    }

}
