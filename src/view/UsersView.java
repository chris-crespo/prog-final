package view;

import data.*;
import models.User;

public class UsersView extends ListView<User> {
    public UsersView(Db db) {
        super(db, "Usuarios", UserCard.class, db::fetchUsers);
    }
}
