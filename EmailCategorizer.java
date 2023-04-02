package com.usf.hackathon;

import java.util.HashMap;
import java.util.Map;

public class EmailCategorizer {

    private static final String Shopping_Category = "Shopping";
    private static final String Subscriptions_Category = "Subscriptions";
    private static final String Health_Category = "Health";
    private static final String Work_Category = "Work";
    private static final String School_Category = "School";
    private static final String Entertainment_Category = "Entertainment";
    private static final String Social_Category = "Social";
    private static final String Urgent_Category = "Urgent";
    private static final String Finances_Category = "Finances";
    private static final String Promotions_Category = "Promotions";
    private static final String Phishing_Category = "Phishing";
    
    private static final String[] Shopping_Values = {"amazon", "sale", "sales", "wholefoods", "shopping cart", "new brands", "clothes", "products", "in stock", "new items", "order", "order confirmation", "new styles"};
    private static final String[] Subscriptions_Values = {"expired", "monthly billing", "billing", "weekly billing", "amount due", "balance"};
    private static final String[] Health_Values = {"healthy", "covid 19", "covid", "corona virus", "clean food", "clean foods", "vitamin", "protein powder", "health, wellness", "stress, workout", "pilates", "gym", "doctor", "coronavirus", "flu", "virus", "vegetarian", "vegan", "plant-based", "skin"};
    private static final String[] Work_Values = {"apply", "timesheet", "intern", "application", "job posting", "linked in (check email)", "opportunities", "job opportunity", "internship", "fellowship"};
    private static final String[] School_Values = {"student", "canvas", "assignments", "class", "labs", "review", "piazza", "learning", "skill", "github", "classroom"};
    private static final String[] Entertainment_Values = {"concert", "social", "social events", "socials", "concerts", "music festival"};
    private static final String[] Social_Values = {"instagram", "twitter", "snapchat", "youtube", "creators", "post", "posts", "message"};
    private static final String[] Urgent_Values = {"last day", "change","invitation","meeting", "last chance", "appointment","now", "reminder", "reminders", "confirmation", "urgent", "important", "action required", "alert", "deadline", "upcoming", "act fast", "act now", "time to", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "tomorrow", "next week", "next month", "order is ready", "update"};
    private static final String[] Finances_Values = {"bank", "banks", "money", "deposit", "statement", "receipt"};
    private static final String[] Promotions_Values = {"% off", "off", "rewards", "coupon"};
    private static final String[] Phishing_Values = {"Important! Your password is about to expire", "Suspicious Outlook activity, Need urgent help!", "Verification required!", "credit card offer", "lawsuit claims", "car insurance", "its your lucky day", "confirm your purchase"};
    
    private Map<String, String[]> categories;

    public EmailCategorizer() {
    	
    	
        categories = new HashMap<>();
        categories.put(Urgent_Category, Urgent_Values);
        categories.put(Shopping_Category, Shopping_Values);
        categories.put(Subscriptions_Category, Subscriptions_Values);
        categories.put(Health_Category, Health_Values);
        categories.put(Work_Category, Work_Values);
        categories.put(School_Category, School_Values);
        categories.put(Entertainment_Category, Entertainment_Values);
        categories.put(Social_Category, Social_Values);
        categories.put(Finances_Category, Finances_Values);
        categories.put(Promotions_Category, Promotions_Values);
        categories.put(Phishing_Category, Phishing_Values);
    }

    public String categorizeEmail(String subject) {
        for (Map.Entry<String, String[]> entry : categories.entrySet()) {
            String category = entry.getKey();
            String[] values = entry.getValue();
            for (String value : values) {
                if (subject.toLowerCase().contains(value.toLowerCase())) {
                    return category;
                }
            }
        }
        return "Other";
    }
    
    
}
