package xyz.metropants.element.util;

public final class FileNameUtils {

    private FileNameUtils() {}

    public static String formatFileName(String name) {
        return name.replaceAll("\\s+", "-");
    }

}
