package it.fi.meucci;

import java.util.HashMap;
import java.util.Map;

public class HashMapConverter {

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

            hashMap.put(keyValue[0], keyValue[1]);

        }

        return hashMap;
    }
}
