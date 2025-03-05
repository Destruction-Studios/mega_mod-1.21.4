package net.ds.megamod.util;

import net.ds.megamod.MegaMod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    public static String tickToString(int tick) {
        return String.format("%.1f", (float) tick / 20);
    }

    public static void getOrCreateConfigFolder() {
        File folder = new File("config/megamod");
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (success) {
                MegaMod.LOGGER.info("Created folder config/megamod");
            } else {
                MegaMod.LOGGER.error("Unable to create folder config/megamod");
            }
        }
    }

    private static String getOrCreateFile(String path) {
        File configFile = new File(path);
        String result = "null";

        try {
            Scanner fileReader = new Scanner(configFile);
            if(fileReader.hasNextLine()) {
                StringBuilder stringBuilder = new StringBuilder();
                while (fileReader.hasNextLine()) {
                    stringBuilder.append(fileReader.nextLine());
                    if (fileReader.hasNextLine()) {
                        stringBuilder.append("\n");
                    }
                }
                result = stringBuilder.toString();
            } else {
                MegaMod.LOGGER.warn("{} is empty, please fill out.", path);
            }

            fileReader.close();
        } catch (FileNotFoundException exception) {
            MegaMod.LOGGER.info("Cannot find file {}, creating...", path);

            try {
                boolean created = configFile.createNewFile();
                if (created) {
                    MegaMod.LOGGER.info("Succesfully created file {}", path);
//                    FileWriter writer = new FileWriter(configFile);
//                    writer.write();
//                    writer.close();
                } else {
                    MegaMod.LOGGER.error("Unable to create file {}", path);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return result;
    }

    public static String arrayListToString(ArrayList<String> list) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[\n");
        for (String str : list) {
            stringBuilder.append("\t\t\"").append(str).append("\",\n");
        }
        stringBuilder.append("\t]");

        return stringBuilder.toString();
    }

    public static String reloadWhitelist() { return getOrCreateFile("config/megamod/whitelist.txt");}
    public static String reloadRules() {
        return getOrCreateFile("config/megamod/rules.txt");
    }
    public static String reloadMOTD() {
        return getOrCreateFile("config/megamod/motd.txt");
    }
}
