package view;

import data.Db;
import models.Camp;

public class CampsView extends ListView<Camp> {
    public CampsView(Db db) {
        super(db, "Campamentos", CampCard.class, db::fetchCamps, e -> {
            db.fetchCampKinds()
                    .ifOk(kinds -> new NewCampForm(db, kinds.toArray(new String[] {})))
                    .ifError(exn -> new CampKindFetchFailure());
        });
    }
}
