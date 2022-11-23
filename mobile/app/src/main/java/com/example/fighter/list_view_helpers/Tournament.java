package com.example.fighter.list_view_helpers;

public class Tournament {
    public String Name;
    public String Description;
    public String Status;
    public Tournament(String name, String description, String status){
        Name = name;
        Description = description;
        switch (status) {
            case "0":
                Status = "Проведено";
                break;
            case "1":
                Status = "Идет сейчас";
                break;
            case "2":
                Status = "Запланировано";
                break;
        }
    }
}