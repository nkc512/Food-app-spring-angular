package com.example.demo.ses;

public enum SESFrom {

    ATTA("cafeteria.notification@gmail.com", "Cafeteria.Notification"),
    NO_REPLY("cafeteria.notification@gmail.com", "Cafeteria.Notification"),
    SUPPORT("cafeteria.notification@gmail.com", "Cafeteria.Notification");

    private final String email;
    private final String name;

    private SESFrom(String email, String name) {
        this.email =email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}