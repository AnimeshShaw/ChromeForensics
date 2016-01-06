package net.letshackit.chromeforensics.core;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChromeForensics {

    /**
     * Essential System environment variables.
     */
    private final String USER_HOME;
    private final String OS;
    private final String WINDOWS_7UP_CHROME_DATAPATH;
    private final String WINDOWS_XP_CHROME_DATAPATH;

    /**
     * Paths to Forensically important SQLite DB files.
     *
     * --To be Updated--
     */
    private String DOWNLOADS_DB_PATH;
    private String HISTORY_DB_PATH;

    public ChromeForensics() {
        USER_HOME = System.getProperty("user.home");
        OS = System.getProperty("os.name");
        WINDOWS_7UP_CHROME_DATAPATH = "AppData/Local/Google/Chrome/User Data/Default";
        WINDOWS_XP_CHROME_DATAPATH = "/Local Settings/Application Data/Google/Chrome/User Data/Default";
    }

    /**
     *
     * @return
     */
    public String getUserHome() {
        return USER_HOME;
    }

    /**
     *
     * @return
     */
    public OSType getOSType(){
        switch (OS) {
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
     *
     * @return
     */
    private Path getChromeDataPath() {
        Path path = null;
        switch (getOSType()){
            case WINDOWS_81:
            case WINDOWS_8:
            case WINDOWS_7:
            case WINDOWS_VISTA:
                path = Paths.get(USER_HOME, WINDOWS_7UP_CHROME_DATAPATH);
                assert Files.exists(path) && Files.isDirectory(path);
                return path;
            case WINDOWS_XP:
                path = Paths.get(USER_HOME, WINDOWS_XP_CHROME_DATAPATH);
                assert Files.exists(path) && Files.isDirectory(path);
                return path;
            case LINUX:
                break;
            case NOT_SUPPORTED:
                break;
        }
        return path;
    }

    public static void main(String[] args) {
        /**
         * dummy
         */
        System.out.println(System.getenv("APPDATA"));
    }
}
