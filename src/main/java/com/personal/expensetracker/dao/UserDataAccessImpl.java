package com.personal.expensetracker.dao;

import com.personal.expensetracker.model.ExpenseReportPojo;
import com.personal.expensetracker.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.time.LocalDate;

@Repository("userDai")
public class UserDataAccessImpl implements UserDao{

    private static List<User> DB = new ArrayList<>();

    static Firestore DB_FS = FirestoreClient.getFirestore();

    @Override
    public String insertUser(String id, User user) throws ExecutionException, InterruptedException {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("password",user.getPassword());
        userDetails.put("name",user.getName());
        String month = LocalDate.now().getMonth().toString().toLowerCase() + LocalDate.now().getYear();
        ApiFuture<WriteResult> collectionsApiFuture = DB_FS.collection("users").document(id).set(user);

        //TO-DO: instead of path being username_password, make it user_id
        DB_FS.collection("expenseReports").document(user.getName()+"_"+user.getPassword())
                .set(new ExpenseReportPojo(month));

        DB.add(new User(id,user.getName()));
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    @Override
    public List<User> selectAllUsers() {
         List<User> users = new ArrayList<>();
         DB_FS.collection("users").listDocuments().forEach(documentReference -> {
             Map<String, Object> data= null;
             try {
                 data = documentReference.get().get().getData();
             } catch (InterruptedException | ExecutionException e) {
                 e.printStackTrace();
             }
             if(data != null);
             {

                 //TO-DO: here new User is being created and id is provided for password
                 users.add(new User(documentReference.getId(), (String) data.get("name")));
             }
         });
         return users;
    }

    @Override
    public Optional<User> selectUserById(String id) {
        return DB.stream()
                .filter(user -> user.getPassword().equals(id))
                .findFirst();
    }

    @Override
    public int deleteUserById(String id) {
        Optional<User> userIfExists = selectUserById(id);
        if(userIfExists.isEmpty()){
            return 0;
        }
        DB.remove(userIfExists.get());
        DB_FS.collection("users").document(id).delete();
        return 1;
    }

    @Override
    public int updateUserById(String id, User user) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("password",user.getPassword());
        userDetails.put("name",user.getName());
        return selectUserById(id).map(u->{
            int indexOfPersonToUpdate =  DB.indexOf(u);
            if(indexOfPersonToUpdate>=0){
                DB.set(indexOfPersonToUpdate,new User(id, user.getName()));
                DocumentReference docRef = DB_FS.collection("users").document(id);
                docRef.update(userDetails);
                return 1;
            }
            else
                return 0;
        }).orElse(0);
    }
}
