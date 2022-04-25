package com.hms.hmsfx.data;

public class SystemData {
    private static String username;

    private static boolean roomStatus;
    private static int roomCreatedBy;



    public static String getUsername() {
        return username;
    }

    public void SystemData() {
    }






    public static void setUsername(String username) {
        SystemData.username = username;
    }
}
