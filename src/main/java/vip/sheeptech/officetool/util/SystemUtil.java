package vip.sheeptech.officetool.util;

import java.io.IOException;

public class SystemUtil {
    /**
     * 使用系统默认资源管理器打开指定路径
     * @param path 资源路径
     */
    public static void openFileLocation(String path) {
        try {
            if (SystemUtil.isWindows()) {
                path = path.replaceAll("/", "\\\\");
                Runtime.getRuntime().exec(new String[]{"explorer.exe", path});
            } else if (SystemUtil.isMac()) {
                Runtime.getRuntime().exec(new String[]{"open -R", path});
            } else if (SystemUtil.isLinux()) {
                Runtime.getRuntime().exec(new String[]{"xdg-open", path});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 是否为Windows系统
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    /**
     * @return 是否为macOS系统
     */
    public static boolean isMac() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    /**
     * @return 是否为Linux系统
     */
    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }
}
