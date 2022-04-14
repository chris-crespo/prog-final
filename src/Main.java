import data.*;
import view.Menu;

public class Main {
    public static void main(String[] args) {
        var db = Db.instance();
        db.ifPresentOrElse(Menu::new, () -> System.out.println("Not connected :("));
    }
}
