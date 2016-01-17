/*
 * Copyright 2016 Animesh Shaw ( a.k.a. Psycho_Coder).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.letshackit.chromeforensics.gui;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Utils {

    private static final int[] SQLITE_MAGIC_HEADER = {
            0x53, 0x51, 0x4c, 0x69, 0x74, 0x65, 0x20, 0x66, 0x6f, 0x72, 0x6d, 0x61, 0x74, 0x20, 0x33, 0x00
    };

    public Utils() {
    }

    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    public static ImageIcon createImageIcon(String path, String description) {
        URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static boolean checkIfSQLiteDb(File dbPath) {
        try (FileInputStream fis = new FileInputStream(dbPath)) {
            for (int i : SQLITE_MAGIC_HEADER) {
                if (fis.read() != i) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Method to Convert Bytes to More Readable file size attribute.
     *
     * @param size Size of a file in Bytes
     * @return Size in readable Bi/KiB/Mib/Gib/ format
     */
    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }

        final String[] units = new String[]{"Bi", "KiB", "MiB", "GiB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static Map<String, String> getFileMetadata(Path fileLoc) {
        Map<String, String> linkedHashMap = new LinkedHashMap<>();

        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(fileLoc, BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return linkedHashMap;
    }

}
