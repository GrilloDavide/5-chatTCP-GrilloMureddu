package it.fi.meucci;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapHandler {


    public static String getIdByName(String name, HashMap<String, String> hashmap){
        String id = "";
        for(Map.Entry<String, String> entry : hashmap.entrySet()){
            if(Objects.equals(name, entry.getValue()))
                return entry.getKey();
        }

        return id;
    }

    public static String getNameById(String id, HashMap<String, String> hashmap){
        String name = "";
        for(Map.Entry<String, String> entry : hashmap.entrySet()){
            if(Objects.equals(id, entry.getKey()))
                return entry.getValue();
        }

        return name;
    }

    public static int getPositionById(String id, HashMap<String, String> hashmap){
        int i = 0;
        for(Map.Entry<String, String> entry : hashmap.entrySet()){

            if(Objects.equals(id, entry.getKey()))
                return i;
            i++;
        }

        return -1;
    }


    public static String hashMapToString(Map<String, String> hashMap) {
        StringBuilder stringOfMap = new StringBuilder();

        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            stringOfMap.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append(",");
        }

        if (!hashMap.isEmpty()) {
            stringOfMap = new StringBuilder(stringOfMap.substring(0, stringOfMap.length() - 1));
        }

        return stringOfMap.toString();
    }

    public static HashMap<String, String> stringToHashMap(String hashMapToConvert) {
        HashMap<String, String> hashMap = new HashMap<>();

        String[] pairs = hashMapToConvert.split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            try {
                hashMap.put(keyValue[0], keyValue[1]);
            } catch (Exception e) {

            }

        }

        return hashMap;
    }
}
