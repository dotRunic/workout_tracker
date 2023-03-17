import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class WorkoutTracker {
    public static void main(String[] args) {
        try {
            List<Person> personList = FileUtils.readPersonsFromCsv();
            FileUtils.writeStatisticsToFile(personList);
            System.out.println("personList csv:");
            for (Person person : personList) {
                System.out.println("Name:" + person.getFirstName() + " " + person.getLastName() + " (" + person.getAge() + (") "));
            }
        } catch (IOException e) {
            System.out.println("Error reading persons from CSV: " + e.getMessage());
        }
    }
}