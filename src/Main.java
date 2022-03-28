import data.Db;

public class Main {
    public static void main(String[] args) {
        var db = Db.instance();
        db.ifPresentOrElse(
                x -> System.out.println("Connected!"),
                () -> System.out.println("Could not connect"));
    }
}
