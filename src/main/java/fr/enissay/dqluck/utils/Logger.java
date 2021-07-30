package fr.enissay.dqluck.utils;

import java.util.Arrays;
import org.fusesource.jansi.Ansi;

public class Logger
{
    public static void logError(String input) { System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("[ERROR] " + input).reset()); }

    public static void logError(String input, Exception exception) { System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("[ERROR] " + input + " Error: " + exception.getMessage() + " - " + Arrays.toString(exception.getStackTrace())).reset()); }

    public static void logWarning(String input) { System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a("[WARNING] " + input).reset()); }

    public static void logInvalid(String input) { System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("[INVALID] " + input).reset()); }

    public static void logSuccess(String input) { System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("[SUCCESS] " + input).reset()); }

    public static void logInfo(String input) { System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a("[INFO] " + input).reset()); }

    public static void logDebug(String input) {
        System.out.println(Ansi.ansi().fg(Ansi.Color.BLUE).a("[DEBUG] " + input).reset());
    }

    public static String cleanCodes(String input) {
        String output = input;
        output = output.replaceAll("/g/", "" + Ansi.ansi().fgGreen());
        output = output.replaceAll("/r/", "" + Ansi.ansi().fgRed());
        output = output.replaceAll("/b/", "" + Ansi.ansi().fgBlue());
        output = output.replaceAll("/y/", "" + Ansi.ansi().fgYellow());
        output = output.replaceAll("/m/", "" + Ansi.ansi().fgMagenta());
        output = output.replaceAll("/br/", "" + Ansi.ansi().fgBrightRed());
        output = output.replaceAll("/bb/", "" + Ansi.ansi().fgBrightBlue());
        output = output.replaceAll("/c/", "" + Ansi.ansi().fgCyan());
        return output.replaceAll("/rs/", "" + Ansi.ansi().reset());
    }

    public static void testPink(String input) { System.out.println(Ansi.ansi().fgBrightMagenta().a("[SUCCESS] " + input).reset()); }

    public static void logCustom(String input) { System.out.println(cleanCodes(input)); }

    public static void logArt(String input) { System.out.print(Ansi.ansi().fg(Ansi.Color.MAGENTA).a(input).reset()); }

    public static void logInput(String input) { System.out.print(Ansi.ansi().fg(Ansi.Color.MAGENTA).a("[INPUT] " + input).reset()); }
}