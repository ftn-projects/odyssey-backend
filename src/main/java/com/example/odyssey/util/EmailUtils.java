package com.example.odyssey.util;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

public class EmailUtils {
    public static void sendConfirmation(String email, String name, Long id){
        RestTemplate restTemplate = new RestTemplate();
        JSONObject body = new JSONObject();
        try {
            JSONObject from = new JSONObject();
            from.put("Email", "dimitrije.gasic.02@gmail.com");
            from.put("Name", "Odyssey");

            JSONArray to = new JSONArray();
            JSONObject receiver = new JSONObject();
            receiver.put("Email", email);
            receiver.put("Name", name);
            to.put(receiver);

            JSONObject message = new JSONObject();
            message.put("From", from);
            message.put("To", to);
            message.put("Subject","Welcome!");
            message.put("HTMLPart", "You tried to make an Odyssey account, before you can proceed you need to confirm your email. Click <a href=\"http://localhost:4200/emailConfirmation/" + id +"\">here</a> in the next 24 hours to activate your account.");

            JSONArray messages = new JSONArray();
            messages.put(message);
            body.put("Messages", messages);
        } catch (JSONException e) {
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("83d495f8f6605e1f533246a0d4f4e0bc","f4e667570681829f7a08d9df9da641e3");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.mailjet.com/v3.1/send");

        HttpEntity<?> entity = new HttpEntity<>(body.toString(),headers);


        restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                String.class);
    }
}
