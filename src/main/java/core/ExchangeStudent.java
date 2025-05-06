package core;

public class ExchangeStudent extends Student {
    private String homeCountry;
    private int durationMonths;

    public ExchangeStudent(String id, String name, int age, double grade, 
                         String homeCountry, int durationMonths) {
        super(id, name, age, grade);
        this.homeCountry = homeCountry;
        this.durationMonths = durationMonths;
    }

    // Getters and setters
    @Override
    public String getDetails() {
        return String.format("[Exchange] %s (ID: %s) - From: %s, %d months, Grade: %.1f",
                name, id, homeCountry, durationMonths, grade);
    }
}