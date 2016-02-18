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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ChromeForensics {

    /**
     * Essential System environment variables.
     */
    private final String USER_HOME;
    private final String OS;
    private final String WINDOWS_VISTAUP_CHROME_DATAPATH;
    private final String WINDOWS_XP_CHROME_DATAPATH;
    private final String LINUX_CHROMIUM_DATAPATH;
    private final String LINUX_CHROME_DATAPATH;

    /**
     * Paths to Forensically important SQLite DB files or other files.
     */
    private final String HISTORY_PATH;
    private final String LOGINDATA_PATH;
    private final String COOKIES_PATH;
    private final String WEBDATA_PATH;
    private final String TOPSITES_PATH;
    private final String EXTENSIONCOOKIES_PATH;
    private final String FAVICONS_PATH;
    private final String BOOKMARKS_PATH;
    private final String BOOKMARKSBAK_PATH;
    private final String PREFERENCES_PATH;
    private final String SECPREFERENCES_PATH;
    private final String VISITED_LINKS_PATH;

    final static Logger logger = LogManager.getLogger(ChromeForensics.class);
    
    public ChromeForensics() {
        USER_HOME = System.getProperty("user.home");
        OS = System.getProperty("os.name");
        WINDOWS_VISTAUP_CHROME_DATAPATH = "AppData/Local/Google/Chrome/User Data/Default";
        WINDOWS_XP_CHROME_DATAPATH = "/Local Settings/Application Data/Google/Chrome/User Data/Default";
        LINUX_CHROMIUM_DATAPATH = ".config/chromium/Default";
        LINUX_CHROME_DATAPATH = ".config/google-chrome/Default";

        assert getChromeDataPath() != null;

        HISTORY_PATH = Paths.get(getChromeDataPath().toString(), "History").toString();
        LOGINDATA_PATH = Paths.get(getChromeDataPath().toString(), "History").toString();
        COOKIES_PATH = Paths.get(getChromeDataPath().toString(), "Cookies").toString();
        WEBDATA_PATH = Paths.get(getChromeDataPath().toString(), "Web Data").toString();
        TOPSITES_PATH = Paths.get(getChromeDataPath().toString(), "Top Sites").toString();
        EXTENSIONCOOKIES_PATH = Paths.get(getChromeDataPath().toString(), "Extension Cookies").toString();
        FAVICONS_PATH = Paths.get(getChromeDataPath().toString(), "Favicons").toString();
        BOOKMARKS_PATH = Paths.get(getChromeDataPath().toString(), "Bookmarks").toString();
        BOOKMARKSBAK_PATH = Paths.get(getChromeDataPath().toString(), "Bookmarks.bak").toString();
        PREFERENCES_PATH = Paths.get(getChromeDataPath().toString(), "Preferences").toString();
        SECPREFERENCES_PATH = Paths.get(getChromeDataPath().toString(), "Secure Preferences").toString();
        VISITED_LINKS_PATH = Paths.get(getChromeDataPath().toString(), "Visited Links").toString();
    }

    /**
     * Returns default User Home depending on OS.
     *
     * @return returns User Home default to Operating System.
     */
    public String getUserHome() {
        return USER_HOME;
    }

    /**
     * Returns the type of OS. Its type is
     * {@code net.letshackit.chromeforensics.core.OSType}
     *
     * @see net.letshackit.chromeforensics.core.OSType
     * @return Type of OS.
     */
    public OSType getOSType() {
        switch (OS) {
            case "Windows 10":
                return OSType.WINDOWS_10;
            case "Windows 8.1":
                return OSType.WINDOWS_81;
            case "Windows 8":
                return OSType.WINDOWS_8;
            case "Windows 7":
                return OSType.WINDOWS_7;
            case "Windows Vista":
                return OSType.WINDOWS_VISTA;
            case "Windows XP":
                return OSType.WINDOWS_XP;
            case "Linux":
                return OSType.LINUX;
            default:
                return OSType.NOT_SUPPORTED;
        }
    }

    /**
     * Needs update
     *
     * @return
     */
    public Path getChromeDataPath() {
        Path path;
        switch (getOSType()) {
            case WINDOWS_10:
            case WINDOWS_81:
            case WINDOWS_8:
            case WINDOWS_7:
            case WINDOWS_VISTA:
                path = Paths.get(USER_HOME, WINDOWS_VISTAUP_CHROME_DATAPATH);
                assert Files.exists(path) && Files.isDirectory(path);
                return path;
            case WINDOWS_XP:
                path = Paths.get(USER_HOME, WINDOWS_XP_CHROME_DATAPATH);
                assert Files.exists(path) && Files.isDirectory(path);
                return path;
            case LINUX:
                if (isChromeInstalled(OSType.LINUX)) {
                    path = Paths.get(USER_HOME, LINUX_CHROME_DATAPATH);
                    return path;
                }
                path = Paths.get(USER_HOME, LINUX_CHROMIUM_DATAPATH);
                assert Files.exists(path) && Files.isDirectory(path);
                return path;
            case NOT_SUPPORTED:
                break;
        }
        return null;
    }

    /**
     *
     * @param os
     * @return
     */
    public boolean isChromeInstalled(OSType os) {
        if (os == OSType.LINUX) {
            return Files.exists(Paths.get(USER_HOME, LINUX_CHROME_DATAPATH));
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getHistoryDbPath() {
        return HISTORY_PATH;
    }

}