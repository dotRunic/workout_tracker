import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtils {
    private final static String PATH_TO_PERSON_IMPORT = "resource/person.csv";
    private final static String PATH_TO_STATISTIC = "resource/statistic.txt";

    public static List<Person> readPersonsFromCsv() throws IOException {
        String line = null;
        List<Person> personList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_PERSON_IMPORT));
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(";");
            String Firstname = data[0];
            String Lastname = data[1];
            int Age = Integer.parseInt(data[2]);
            Person person = new Person(null, Firstname, Lastname, Age);

            personList.add(person);
        }
        bufferedReader.close();
        return personList;  
    }

    public static void writeStatisticsToFile(List<Person> personList) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_STATISTIC));
        for (Person person : personList) {
            writer.write("Name:" + person.getFirstName() + " " + person.getLastName() + " (" + person.getAge() + (") "));
            writer.newLine();
            writer.newLine();
        }
        writer.close();
    }
}