package Game.Log;

public class Logger {

    public static void Log(String message, LogType logType) {
        String header;
        switch (logType) {
            case Error:
                header = "[-]";
                break;

            case Info:
                header = "[?]";
                break;

            case Warning:
                header = "[!]";
                break;

            case None:
            default:
                header = "";
                break;
        }
        System.out.println(header + " " + message);
    }
}
