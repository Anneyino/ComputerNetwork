package web.entity;

public class MIME {
    public static String HTML = "HTML";
    public static String JSON = "JSON";
    public static String TXT = "TXT";

    public static String toString(String type) {
        switch (type) {
            case "JSON":
                return "Content-Type: application/json";
            case "HTM":
            case "HTML":
                return "Content-Type: text/html";
            case "TXT":
                return "Content-type: text/plain";
            default:
                return null;
        }
    }
}
