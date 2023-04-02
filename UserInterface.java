//Team 14
package com.usf.hackathon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UserInterface {
	
	// read the file and categories it into all emails into a hashmap while storing the email and subject into an arraylist
    public static Map<String, List<String>> categorize(String filename) throws IOException {
        EmailCategorizer categorizer = new EmailCategorizer();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        reader.readLine();
        String line;
        Map<String, List<String>> Map = new HashMap<>();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String email = parts[0];
            String subject = parts[1];
            String category = categorizer.categorizeEmail(subject);
            if (category == "Other") {
                category = categorizer.categorizeEmail(email);
            }
            // Add the email and subject to the list by category
            List<String> emailList = Map.getOrDefault(category, new ArrayList<>());
            emailList.add(email + ": " + subject);
            Map.put(category, emailList);
        }
        reader.close();
        return Map;
    }
    
    //Traverse through the map and recategorized by the subject or email and return the new map
    public Map<String, List<String>> categorizeEmails(Map<String, List<String>> emailMap) {
        EmailCategorizer categorizer = new EmailCategorizer();
        Map<String, List<String>> categorizedEmails = new HashMap<>();
        Map<String, List<String>> emailsByCategory = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : emailMap.entrySet()) {
            List<String> emails = entry.getValue();

            for (String email : emails) {
                String[] parts = email.split(": ");
                String emailAddress = parts[0];
                String subject = parts[1];
                String emailCategory = categorizer.categorizeEmail(subject);
                if (emailCategory.equals("Other")) {
                    emailCategory = categorizer.categorizeEmail(emailAddress);
                }

                if (!emailsByCategory.containsKey(emailCategory)) {
                    emailsByCategory.put(emailCategory, new ArrayList<>());
                }
                emailsByCategory.get(emailCategory).add(emailAddress + ": " + subject);
            }
        }


        // Loop through the categories and add the emails to the categorizedEmails map
        for (Map.Entry<String, List<String>> entry : emailsByCategory.entrySet()) {
            String category = entry.getKey();
            List<String> emails = entry.getValue();

            categorizedEmails.put(category, emails);
        }


        return categorizedEmails;
    }

    //prints map in the categorized format
    public static void printCategorize(Map<String, List<String>> Map) {
        for (String category : Map.keySet()) {
            System.out.println(category + ":");
            for (String emailSubject : Map.get(category)) {
                System.out.println(emailSubject);
            }
            System.out.println();
        }
    }
    
    // deletes emails with specified address or key words in the subject 
    public static Map<String, List<String>> delete(Map<String, List<String>> Map, String search_value, String type) throws IOException {
        Map<String, List<String>> NewMap;
        if (type.equals("type1")) {
            NewMap = deleteEmailAndKeywords(Map, search_value, null);
        } else {
            NewMap = deleteEmailAndKeywords(Map, null, search_value);
        }
        return NewMap;
    }
    
    // prints all elements
    public static void printDelete(Map<String, List<String>> Map) {
        for (String category : Map.keySet()) {
            for (String emailSubject : Map.get(category)) {
                System.out.println(emailSubject);
            }
        }
        System.out.println();
    }
    
    // checks if the wanted keyword or email matches and doesnt add it to the new map to "delete" 
    public static Map<String, List<String>> deleteEmailAndKeywords(Map<String, List<String>> map, String emailAddress, String keyword) {
        Map<String, List<String>> updatedMap = new HashMap<>();
        for (String category : map.keySet()) {
            List<String> emailSubjectList = new ArrayList<>();
            for (String emailSubject : map.get(category)) {
                String email = emailSubject.split(":")[0].trim();
                if (emailAddress != null && email.equals(emailAddress)) {
                    continue;
                }
                if (keyword != null && emailSubject.toLowerCase().contains(keyword.toLowerCase())) {
                    continue;
                }
                emailSubjectList.add(emailSubject);
            }
            updatedMap.put(category, emailSubjectList);
        }
        return updatedMap;
    }


    public static void main(String args[]) throws IOException {
        UserInterface foo = new UserInterface();


        String filename = "Inbox.csv";
        String type1 = "email";
        String type2 = "keywords";

        Map<String, List<String>> Map = foo.categorize(filename);
        System.out.println("Welcome to the Email Plow!");

        while (true) { // while the user input is not "EXIT" continue to ask for user input
            System.out.println("Enter 1 to DELETE emails");
            System.out.println("      2 to CATEGORIZE all emails");
            System.out.println("      3 to EXIT the program: ");

            Scanner sc = new Scanner(System.in);

            String input = sc.nextLine();

            //if use input is "EXIT" end loop
            if (input.equals("3")) {
                System.out.println("Thanks for using the Email Plow!");
                System.exit(0);
            }
            //if user input is not "EXIT" continue with the program
            else {

                if (input.equals("1")) {
                    System.out.println("Enter 1 to delete by EMAIL ADDRESS");
                    System.out.println("      2 to delete by KEYWORD");
                    System.out.println("      3 to delete by CATEGORY");
                    input = sc.nextLine();

                    if (input.equals("1")) {
                        System.out.println("Enter email address: ");
                        input = sc.nextLine();

                        // calls function with filename and input --> for each input compares it with the elems in the file and deletes them
                        Map = foo.delete(Map, input, type1);
                        foo.printDelete(Map); // prints the map

                    } else if (input.equals("2")) {
                        System.out.println("Enter keyword: ");
                        input = sc.nextLine();

                        // calls function with filename and input --> for each input compares it with the elems in the file and deletes them
                        Map = foo.delete(Map, input, type2);
                        foo.printDelete(Map); // prints the map

                    } else if( input.equals("3")) {
                    	System.out.println("Enter category: ");
                        input = sc.nextLine();
                        
                    	Map.remove(input); // remove the category
                    	foo.printCategorize(Map);
                    	
                    	
                    }
                    else {
                        System.out.println("Invalid input. Try again.");
                    }

                } else if (input.equals("2")) {

                    System.out.println("Sorting into categories... ");
                    if (Map.equals(foo.categorize(filename))) {
                        foo.printCategorize(Map);
                    } else {
                        Map = foo.categorizeEmails(Map);// calls function that searches and sorts all emails into categories

                        foo.printCategorize(Map);
                    }
                  

                } else {
                    System.out.println("Invalid input. Try again.");
                }
            }
        }

    }


}