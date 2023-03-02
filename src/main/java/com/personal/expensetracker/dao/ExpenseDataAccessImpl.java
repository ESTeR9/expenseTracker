package com.personal.expensetracker.dao;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.personal.expensetracker.model.User;
import com.google.cloud.firestore.DocumentReference;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.nonNull;

@Repository("expenseReportDai")
public class ExpenseDataAccessImpl implements ExpenseDao{

    private static final Firestore DB_FS = FirestoreClient.getFirestore();

    @Override
    public String insertExpense(String username, String month, String expenseType, Double expenseAmount) throws ExecutionException,
            InterruptedException {
        DocumentReference expenseReportDocumentReference= DB_FS.collection("expenseReports")
                .document(username).get().get().getReference();
        Map<String,Object> expenseReportsData= expenseReportDocumentReference.get().get().getData();
        Map<String, Map<String, Double>> expenseReportsMap = ((Map<String, Map<String, Double>>) expenseReportsData.get("reports"));
        Map<String, Double> expenseReportMap = expenseReportsMap.get(month);
        if (nonNull(expenseReportMap)){
            if(nonNull(expenseReportMap.get(expenseType))){
                Double currentValue = expenseReportMap.get(expenseType);
                expenseReportMap.put(expenseType,currentValue+expenseAmount);
            }
            else{
                expenseReportMap.put(expenseType,expenseAmount);
            }
            Double currentTotal = expenseReportMap.get("total");
            expenseReportMap.put("total",currentTotal+expenseAmount);
            expenseReportsMap.put(month,expenseReportMap);
        }
        else{
            Map<String,Double> newExpenseReportMap= new HashMap<>();
            newExpenseReportMap.put("total",expenseAmount);
            newExpenseReportMap.put(expenseType,expenseAmount);
            expenseReportsMap.put(month,newExpenseReportMap);
        }
        return expenseReportDocumentReference.update("reports",expenseReportsMap)
                .get().getUpdateTime().toString();
    }

    @Override
    public Map<String, Double> selectExpenseReportByUsernameAndMonth(String username, String month){
        try{
            return ((Map<String,Map<String,Double>>) DB_FS.collection("expenseReports").document(username).get()
                    .get().getReference().get().get().getData().get("reports")).get(month);
        }
        catch (Exception e) {
            return null;
        }
    }
}
