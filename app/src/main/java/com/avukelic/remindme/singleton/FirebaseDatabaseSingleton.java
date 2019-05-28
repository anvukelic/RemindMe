package com.avukelic.remindme.singleton;

import com.avukelic.remindme.RemindMeApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseSingleton {

    private static FirebaseDatabaseSingleton ourInstance;

    private DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("remindMe");

    public static FirebaseDatabaseSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new FirebaseDatabaseSingleton();
        }
        return ourInstance;
    }

    public DatabaseReference getReminderDatabaseReference() {
        DatabaseReference databaseReference = userReference.child(UserSingleton.getInstance().getUser().getId());
        databaseReference.keepSynced(true);
        return databaseReference;
    }

}
