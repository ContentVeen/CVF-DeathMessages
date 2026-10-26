package dev.mrshawn.deathmessages.utils;

import dev.mrshawn.deathmessages.DeathMessages;
import dev.mrshawn.deathmessages.nms.NMSProvider;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlatformUtil {

    public static final boolean IS_PAPER = Util.doesClassExists("io.papermc.paper.configuration.GlobalConfiguration");
    public static final boolean IS_FOLIA = DeathMessages.getInstance().foliaLib.isFolia();

    public static void init() {
        NMSProvider.initNMS();
    }

     /*
        Sakamoto Util
        Cute Sakamoto helps you to process versions =w=
     */
    private static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)(?:\\.(\\d+))?(?!\\d)");
    private final static String[] serverVersion = getServerVersion();
    private final static int majorVersion = Integer.parseInt(serverVersion[0]);
    private final static int minorVersion = Integer.parseInt(serverVersion[1]);
    private final static int patchVersion = serverVersion.length == 3 ? Integer.parseInt(serverVersion[2]) : 0;

    // > (major, minor, patch)
    public static boolean isNewerThan(int major, int minor, int patch) {
        return compare(major, minor, patch) > 0;
    }

    // == (major, minor, patch)
    public static boolean isEqualTo(int major, int minor, int patch) {
        return compare(major, minor, patch) == 0;
    }

    // < (major, minor, patch)
    public static boolean isOlderThan(int major, int minor, int patch) {
        return compare(major, minor, patch) < 0;
    }

    // >= (major, minor, patch)
    public static boolean isNewerAndEqual(int major, int minor, int patch) {
        return compare(major, minor, patch) >= 0;
    }

    // <= (major, minor, patch)
    public static boolean isOlderAndEqual(int major, int minor, int patch) {
        return compare(major, minor, patch) <= 0;
    }

    // Redirections for old version schema

    // > (major, minor)
    public static boolean isNewerThan(int major, int minor) {
        return isNewerThan(1, major, minor);
    }

    // == (major, minor)
    public static boolean isEqualTo(int major, int minor) {
        return isEqualTo(1, major, minor);
    }

    // < (major, minor)
    public static boolean isOlderThan(int major, int minor) {
        return isOlderThan(1, major, minor);
    }

    // >= (major, minor)
    public static boolean isNewerAndEqual(int major, int minor) {
        return isNewerAndEqual(1, major, minor);
    }

    // <= (major, minor)
    public static boolean isOlderAndEqual(int major, int minor) {
        return isOlderAndEqual(1, major, minor);
    }

    private static int compare(int major, int minor, int patch) {
        if (majorVersion != major) {
            return Integer.compare(majorVersion, major);
        }

        if (minorVersion != minor) {
            return Integer.compare(minorVersion, minor);
        }

        return Integer.compare(patchVersion, patch);
    }

    // New server version schema
    // Paper:
    // 26.1.local-SNAPSHOT -> {"26","1"}
    // 26.1.build.9-alpha -> {"26","1"}
    // 26.2.1.build.5-stable -> {"26","2","1"}
    // Spigot:
    // 26.1-R0.1-SNAPSHOT -> {"26","1"}
    // Old server version schema
    // Paper / Spigot:
    // 1.20.2-R0.1-SNAPSHOT -> {"1","20","2"}
    private static String[] getServerVersion() {
        final String version = Bukkit.getServer().getBukkitVersion();
        // Only take the leading "major.minor[.patch]" part, ignore everything after it (e.g. ".build.x", "-R0.1-SNAPSHOT")
        final Matcher matcher = VERSION_PATTERN.matcher(version);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid version format: [" + version + "]!");
        }

        return matcher.group(3) != null
                ? new String[]{matcher.group(1), matcher.group(2), matcher.group(3)}
                : new String[]{matcher.group(1), matcher.group(2)};
    }
}
