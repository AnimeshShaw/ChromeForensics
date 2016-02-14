/*
 * Copyright (C) 2016 Psycho_Coder <Animesh Shaw>.
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
package net.letshackit.chromeforensics.core.artefacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import net.letshackit.chromeforensics.core.Utils;

/**
 * Class that parses the
 * <pre>Visited Links</pre> file and gathers the information contained
 *
 * @author Psycho_Coder
 */
public class VisitedLinkParser {

    private final File vLnkFile;

    private final HashSet<String> fingerprints;
    private final byte[] VLNK_MAGIC_HEADER = "VLnk".getBytes();
    private byte[] salt;

    private final int HEADER_SALT_OFFSET = 0x10;
    private final int HEADER_SALT_LENGTH = 8;
    private final int URL_FINGERPRINT_LENGTH = 8;

    public VisitedLinkParser(File vLnkFile) {
        this.vLnkFile = vLnkFile;
        salt = null;
        fingerprints = new HashSet<>();
    }

    public int parse() {
        if (Utils.verifyFileHeader(vLnkFile, VLNK_MAGIC_HEADER)) {
            salt = new byte[HEADER_SALT_LENGTH];
            byte[] bytes = new byte[URL_FINGERPRINT_LENGTH];
            try (RandomAccessFile raf = new RandomAccessFile(vLnkFile, "r")) {
                int val;
                raf.seek(HEADER_SALT_OFFSET);
                raf.read(salt);
                while ((val = raf.read()) != -1) {
                    if (val != 0) {
                        raf.seek(raf.getFilePointer() - 1);
                        raf.read(bytes, 0, URL_FINGERPRINT_LENGTH);
                        fingerprints.add(Utils.byteArrayToHex(bytes));
                    }
                }
            } catch (FileNotFoundException ex) {
                System.err.println(ex.getMessage());
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        } else {
            return -1;
        }
        return 1;
    }

    public String getUrlFingerprint(byte[] salt, byte[] data) {
        byte[] mdBytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            md.update(data);
            mdBytes = md.digest();
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Couldn't determine the hashing algorithm." + ex.
                    getMessage());
        }

        return Utils.byteArrayToHex(Arrays.copyOf(mdBytes, URL_FINGERPRINT_LENGTH));
    }

    public boolean isVisited(String url) {
        return fingerprints.contains(getUrlFingerprint(salt, url.getBytes()));
    }

    public byte[] getSalt() {
        return salt;
    }

    public HashSet<String> getVisitedFingerprints() {
        return fingerprints;
    }
}
