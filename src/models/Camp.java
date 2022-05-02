package models;

import java.time.LocalDate;

public record Camp(
        int id,
        String name, 
        String kind, 
        String description, 
        String location,
        LocalDate startDate,
        LocalDate endDate,
        int minAge,
        int maxAge) {}
