package me.border.spigotutilities.utils;

public class URLHandler {
    public static final String UUID_GETTER = "https://api.mojang.com/users/profiles/minecraft/%s";
    public static final String USERNAME_GETTER = "https://sessionserver.mojang.com/session/minecraft/profile/%s";

    public static String formatAPI(String API, String value) {
        return String.format(API, value);
    }
}
