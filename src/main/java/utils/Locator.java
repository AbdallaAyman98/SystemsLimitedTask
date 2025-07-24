package utils;

public class Locator {
    private String type;
    private String value;

    public Locator(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
