package main.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameConfig {

    // Config dosyasındaki bütün sayısal ayarları bellekte tutar.
    private static final Map<String, Double> configData = new HashMap<>();

    public static void loadConfig(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Boş satırları atla.
                if (line.trim().isEmpty()) continue;

                // "key: value" formatındaki satırı anahtar ve değere böler.
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    double value = Double.parseDouble(parts[1].trim());
                    configData.put(key, value);
                }
            }
            System.out.println("Konfigürasyon dosyası başarıyla yüklendi!");
        } catch (IOException e) {
            System.err.println("HATA: config.txt okunamadı! Yol doğru mu? " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("HATA: config.txt içindeki bir değer sayıya çevrilemedi!");
        }
    }

    // Tam sayı beklenen ayarları çağırmak için kullanılır.
    public static int getValue(String key) {
        return (int)Math.round(configData.getOrDefault(key, 0.0));
    }

    // Düşman hızı gibi ondalıklı kullanılabilecek ayarları çağırmak için kullanılır.
    public static double getDouble(String key) {
        return configData.getOrDefault(key, 0.0);
    }
}
