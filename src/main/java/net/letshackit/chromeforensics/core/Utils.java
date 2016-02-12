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
package net.letshackit.chromeforensics.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import javax.swing.ImageIcon;

public final class Utils {

    private static final int[] SQLITE_MAGIC_HEADER = {
        0x53, 0x51, 0x4c, 0x69, 0x74, 0x65, 0x20, 0x66, 0x6f, 0x72, 0x6d, 0x61, 0x74, 0x20, 0x33, 0x00
    };

    public Utils() {
    }

    public static String getOsName() {
        return System.getProperty("os.name");
    }

    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    public static ImageIcon createImageIcon(String path, String description) {
        URL imgURL = Utils.class.getClassLoader().getResource(path);
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

    public static String getDateTime(FileTime fileTime) {
        String DEF_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
        return getDateTime(fileTime, DEF_FORMAT);
    }

    public static String getDateTime(FileTime fileTime, String format) {
        String DEF_FORMAT = null;

        if (format.isEmpty()) {
            DEF_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
        } else {
            DEF_FORMAT = format;
        }

        SimpleDateFormat df = new SimpleDateFormat(DEF_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(fileTime.toMillis());
    }

    public static String getDateTime(long millies) {
        String DEF_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
        return getDateTime(millies, DEF_FORMAT);
    }

    public static String getDateTime(long millies, String format) {
        String DEF_FORMAT = null;

        if (format.isEmpty()) {
            DEF_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
        } else {
            DEF_FORMAT = format;
        }

        SimpleDateFormat df = new SimpleDateFormat(DEF_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(millies);
    }

    public static Map<String, String> getFileMetadata(Path fileLoc) {
        Map<String, String> linkedHashMap = new LinkedHashMap<>();

        try {
            DosFileAttributes dosFileAttr = Files.readAttributes(fileLoc, DosFileAttributes.class);

            linkedHashMap.put("File Name", fileLoc.getFileName().toString());
            linkedHashMap.put("File Location", fileLoc.toString());
            linkedHashMap.put("File Size", readableFileSize(dosFileAttr.size()));
            linkedHashMap.put("Creation Time", getDateTime(dosFileAttr.creationTime()));
            linkedHashMap.put("Last Accessed Time", getDateTime(dosFileAttr.lastAccessTime()));
            linkedHashMap.put("Last Modified Time", getDateTime(dosFileAttr.lastModifiedTime()));
            linkedHashMap.put("Is Directory?", dosFileAttr.isDirectory() ? "True" : "False");
            linkedHashMap.put("Is Regular File?", dosFileAttr.isRegularFile() ? "True" : "False");
            linkedHashMap.put("Is Symbolic Link?", dosFileAttr.isSymbolicLink() ? "True" : "False");
            linkedHashMap.put("Is Archive?", dosFileAttr.isArchive() ? "True" : "False");
            linkedHashMap.put("Is Hidden File?", dosFileAttr.isHidden() ? "True" : "False");
            linkedHashMap.put("Is ReadOnly?", dosFileAttr.isReadOnly() ? "True" : "False");
            linkedHashMap.put("Is System File?", dosFileAttr.isSystem() ? "True" : "False");

            if (getOsName().equals("Linux")) {
                PosixFileAttributes attr = Files.readAttributes(fileLoc, PosixFileAttributes.class);
                String posixPerm = String.format("%s %s %s%n", attr.owner().getName(), attr.group().getName(),
                        PosixFilePermissions.toString(attr.permissions()));

                linkedHashMap.put("Posix", posixPerm);
            }

        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
        }

        return linkedHashMap;
    }

    public static Object[][] to2DObjectArray(Map<String, String> map) {
        Object[][] objects = new Object[map.size()][2];
        Object[] keys = map.keySet().toArray();
        Object[] values = map.values().toArray();

        for (int i = 0; i < map.size(); i++) {
            objects[i][0] = keys[i];
            objects[i][1] = values[i];
        }

        return objects;
    }
}
