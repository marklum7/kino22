package com.example.kino22;

import android.content.Intent;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class postReq extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        try {

            // Создаем URL-объект с адресом сервера
            URL url = new URL(params[0]);
            // Создаем подключение HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            // Создаем JSON объект и добавляем в него данные
            JSONObject requestData = new JSONObject();
            try {
                requestData.put("name", params[1]);
                requestData.put("info", params[2]);
                requestData.put("comm", params[3]);
                requestData.put("image", params[4]);
                // Добавьте сюда свои данные
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Получаем OutputStream и пишем в него данные
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestData.toString().getBytes());
            outputStream.flush();
            outputStream.close();
            // Получаем ответ от сервера
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            // Закрываем подключение
            connection.disconnect();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected String DELETE(String... params) {
        try {

            // Создаем URL-объект с адресом сервера
            URL url = new URL(params[0]+"/"+params[1]);
            // Создаем подключение HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            // Установить время ожидания соединения (мс)
            connection.setConnectTimeout(5000);
            // Установить время ожидания чтения (мс)
            connection.setReadTimeout(5000);
            // Создаем JSON объект и добавляем в него данные
            InputStream stream = connection.getInputStream();
            // Получаем ответ от сервера
            // Закрываем подключение
            connection.disconnect();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}