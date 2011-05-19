package cook;

import cook.core.Cook;
import org.fusesource.jansi.AnsiConsole;

public class Main {

    public static void main(String[] args) throws Exception {
        AnsiConsole.systemInstall();
        Cook c = new Cook(args);
        c.start();
    }
}
