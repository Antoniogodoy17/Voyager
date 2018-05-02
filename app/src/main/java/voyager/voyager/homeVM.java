package voyager.voyager;

import android.arch.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class homeVM extends ViewModel {
    private FirebaseDatabase database;
    private DatabaseReference usersDatabase;
//    ,activityDatabase;
    private Query activityDatabase;
    private FirebaseUser fbUser;
    private FirebaseAuth firebaseAuth;
    private User user;
    private ArrayList<String> activitiesId = new ArrayList<>();
    private ArrayList<Actividad> activities;
    private Map<String, Object> activitiesMap = new HashMap<>();

    public homeVM(){
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        usersDatabase = database.getReference("User");
        Query activityDatabase = database.getReference("Actividades");
        activities = new ArrayList<Actividad>();
        fbUser = firebaseAuth.getCurrentUser();

        usersDatabase.orderByChild("email").startAt(fbUser.getEmail()).endAt(fbUser.getEmail() + "\uf8ff").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user = dataSnapshot.getValue(User.class);


            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });


        activities = new ArrayList<Actividad>();
        activityDatabase.orderByChild("id").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               // activitiesId.add(dataSnapshot.getKey());
                activities.add(dataSnapshot.getValue(Actividad.class));
                System.out.println("---------> "+ activities.size());
//                System.out.println(activitiesId.size());

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        for(int i = 0; i < 13; i++){
            //System.out.println("---------> "+ activities.size());
//            activityDatabase.orderByChild(activitiesId.get(i)).startAt(activitiesId.get(i)).endAt(activitiesId.get(i)+ "\uf8ff").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    int count = 0;
//                    activities.add(dataSnapshot.getValue(Actividad.class));
//                    System.out.println("---------> "+ activities.get(count).titulo);
//                    count++;
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
        }




    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public FirebaseUser getFbUser() {
        return fbUser;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public DatabaseReference getUsersDatabase() {
        return usersDatabase;
    }

    public User getUser() {
        return user;
    }

    public void setFbUser(FirebaseUser fbUser) {
        this.fbUser = fbUser;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setActivities(ArrayList<Actividad> acts){
        this.activities = acts;
    }


    public ArrayList<Actividad> getActivitiesMap(){ return activities; }

}
