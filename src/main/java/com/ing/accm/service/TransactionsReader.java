package com.ing.accm.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ing.accm.model.Transaction;
import com.ing.accm.util.ZonedDateTimeAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsReader {

    @Autowired
    ResourceLoader resourceLoader;

    @SuppressWarnings("unchecked")
    public List<Transaction> readTransactionsJson() {
        List<Transaction> transactions = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:transactions.json");

        try {
            JSONArray transactionsJsonArray = (JSONArray) new JSONParser().parse(new InputStreamReader(resource.getInputStream()));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
                    .create();

            transactionsJsonArray.forEach(t -> {
                Object transactionObject = ((JSONObject) t).get("transaction");
                Transaction transaction = gson.fromJson(((JSONObject) transactionObject).toString(), Transaction.class);
                transactions.add(transaction);
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return transactions;

    }

}
