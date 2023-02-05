/*
    Author: .GuTeK <dev@gutcode.pl>
    Project: TNT-AUTH [MINECRAFT PLUGIN PASS]
    Price: MINECRAFT PLUGIN PASS - $$$
    Resources: 2/1600
    Data: 05.02.2023
    Contact Discord: .GuTeK#0001
    Contact e-mail: dev@gutcode.pl
    Our websites: https://tntnetwork.pl / https://gutcode.pl
    ⓒ 2023 by .GuTeK | ALL RIGHTS RESERVED |
*/

package GuTeK.activations.registeries;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogSupport {
    private static boolean debug;
    private static Logger logger;
    private static final Level level;

    private LogSupport() {
    }

    public static void log(final String msg) {
        if (LogSupport.debug) {
            System.out.println(msg);
        }
        LogSupport.logger.log(LogSupport.level, msg);
    }

    public static void log(final String msg, final Throwable t) {
        if (LogSupport.debug) {
            System.out.println(msg + "; Exception: " + t);
        }
        LogSupport.logger.log(LogSupport.level, msg, t);
    }

    public static boolean isLoggable() {
        return LogSupport.debug || LogSupport.logger.isLoggable(LogSupport.level);
    }

    static {
        LogSupport.debug = false;
        level = Level.FINE;
        try {
            LogSupport.debug = Boolean.getBoolean(name:"javax.activation.debug");
        }
        catch (Throwable t) {}
        LogSupport.logger = Logger.getLogger(name:"javax.acivation");
    }
}
