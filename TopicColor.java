public enum TopicColor {
    RED("\u001B[31m"),
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m");

    public final String code;

    TopicColor(String code) {
        this.code = code;
    }

    public String colorize(String text, boolean useColors) {
        return useColors ? code + text + "\u001B[0m" : text;
    }
}
