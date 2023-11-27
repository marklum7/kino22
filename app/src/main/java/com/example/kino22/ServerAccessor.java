package com.example.kino22;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServerAccessor {

    private String serviceAddress;
    public ServerAccessor(String ServiceAddress)
    {
        serviceAddress = ServiceAddress;
    }


    /**
     * получить список тем из списка заметок
     * @param filmList
     * @return
     */
    public ArrayList<String> getStringListFromNoteList(ArrayList<films> filmList) {
        ArrayList<String> stringList = new ArrayList<>();
        for (films film : filmList) {
            stringList.add(film.name);
        }
        return stringList;
    }
    public Map<String, String> getObject(films film){
        HashMap<String, String> infer = new HashMap<>();
        infer.put("name", film.name);
        //System.out.println(film.info);
        infer.put("info", film.info);
        //System.out.println(film.comm);
        infer.put("comm", film.comm);
        //System.out.println(film.image);
        infer.put("image", film.image);
        return infer;
    }


    /**
     * Получить данные с сервера
     * @return список заисей с сервера
     * @throws IOException
     */
    public ArrayList<films> getData() throws IOException {
        return  Parse(GetContent());
    }


    /**
     * Разобрать содержимое json в данные
     * вместо этого можно использовать десериализацию
     * @param content вернувшиеся с сервера данные
     * @return список заметок
     */
    private ArrayList<films> Parse(String content)
    {
        ArrayList<films> dataItems;
        try {
            Gson gson = new Gson();
            Type listOfComputers = new TypeToken<ArrayList<films>>() {
            }.getType();
            content = content.substring(21);
            content = content.substring(0, content.length() - 2);
            dataItems = gson.fromJson(content, listOfComputers);
            return dataItems;
        } catch (Exception ex) {
            Log.d("TAG", "Parse: Error: "+ex.getMessage());
        }
        return null;
    }
    /**
     *  метод подключается к серверу и забирает оттуда данные.
     *  может работать долго.
     * @return строка с полученными от серввера данными
     */
    private String GetContent() throws IOException {
        BufferedReader reader = null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(serviceAddress);
            connection = (HttpURLConnection) url.openConnection();
            // Установить метод запроса
            connection.setRequestMethod("GET");
            // Установить время ожидания соединения (мс)
            connection.setConnectTimeout(5000);
            // Установить время ожидания чтения (мс)
            connection.setReadTimeout(5000);

            // получение данных
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            return buf.toString();
        }
        catch (Exception exception){
        }
        finally {
            // независимо, пошло ли что-то не так или все хорошо
            // нужно закрыть соединение и всё прочее
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}