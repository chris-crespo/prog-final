package models;

public record Camp(
        int id,
        String name, 
        String kind, 
        String description, 
        String location,
        int minAge,
        int maxAge) {}
