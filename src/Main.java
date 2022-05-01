import data.*;
import view.Menu;
import view.ConnectionFailure;

public class Main {
    public static void main(String[] args) {
        var db = Db.instance();
        db.ifPresentOrElse(Menu::new, ConnectionFailure::new);
    }
}
