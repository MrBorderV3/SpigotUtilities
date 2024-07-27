package me.border.spigotutilities.mojang.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.border.spigotutilities.utils.URLHandler;
import me.border.utilities.utils.WebRequest;

import java.util.UUID;
import java.util.regex.Pattern;

public class MojangWebManager {

    public static String getUsername(UUID uuid) {
        String response = WebRequest.sendGet(URLHandler.formatAPI(URLHandler.USERNAME_GETTER, uuid.toString().replace("-", "")));
        if (response.isEmpty()) {
            return null;
        }
        JsonElement usernameJsonEle = JsonParser.parseString(response);
        if (isRequestLimit(usernameJsonEle)) {
            return null;
        }
        JsonArray usernameJsonArr = usernameJsonEle.getAsJsonArray();
        JsonObject usernameJsonObj = usernameJsonArr.get(usernameJsonArr.size()-1).getAsJsonObject();
        String username = usernameJsonObj.get("name").getAsString();

        MojangCacheManager.updateCache(uuid, username);
        return username;
    }

    public static UUID getUUID(String username) {
        String response = WebRequest.sendGet(URLHandler.formatAPI(URLHandler.UUID_GETTER, username));
        if (response.isEmpty()) {
            return null;
        }
        JsonElement uuidJsonEle = JsonParser.parseString(response);
        if (isRequestLimit(uuidJsonEle)) {
            return null;
        }
        JsonObject uuidJsonObj = uuidJsonEle.getAsJsonObject();
        String uuidString = uuidJsonObj.get("id").getAsString();
        UUID uuid = parseUUID(uuidString);

        MojangCacheManager.updateCache(uuid, username);
        return uuid;
    }

    private static UUID parseUUID(String uuidString){
        Pattern pattern = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
        return UUID.fromString(pattern.matcher(uuidString).replaceAll("$1-$2-$3-$4-$5"));
    }

    private static boolean isRequestLimit(JsonElement json) {
        return json.toString().contains("error");
    }

}
