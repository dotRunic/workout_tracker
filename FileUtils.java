import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

    private final static String PATH_TO_PERSON_IMPORT = "resource/person.csv";
    private final static String PATH_TO_STATISTIC = "resource/statistic.txt";

    public FileUtils() {
    }

    private int getNumberOfSwimmingWorkoutsByType(List<SwimmingWorkout> swimmingWorkouts, SwimmingType swimmingType) {
        int count = 0;
        for (SwimmingWorkout workout : swimmingWorkouts) {
            SwimmingType type = workout.getSwimmingType();
            if (type != null && type.equals(swimmingType)) {
                count++;
            }
        }
        return count;
    }

    private int getNumberOfSwimmingWorkoutsByPerson(Long personId, List<Workout> workoutList) {
        int count = 0;
        for (Workout workout : workoutList) {
            if (workout.getPersonId() == personId && workout instanceof SwimmingWorkout) {
                count++;
            }
        }
        return count;
    }

    private int getNumberOfBikingWorkoutsByType(List<BikingWorkout> bikingWorkouts, BikingType bikingType) {
        int count = 0;
        for (BikingWorkout workout : bikingWorkouts) {
            BikingType type = workout.getBikingType();
            if (type != null && type.equals(bikingType)) {
                count++;
            }
        }
        return count;
    }
    
    private int getNumberOfBikingWorkoutsByPerson(Long personId, List<Workout> workoutList) {
        int count = 0;
        for (Workout workout : workoutList) {
            if (workout.getPersonId() == personId && workout instanceof BikingWorkout) {
                count++;
            }
        }
        return count;
    }


    private double getAverageDistance(List<? extends Workout> workouts) {
        int totalDistance = 0;
        int totalWorkouts = workouts.size();
    
        for (Workout workout : workouts) {
            totalDistance += workout.getDistance();
        }
    
        if (totalWorkouts == 0) {
            return 0;
        }
    
        return (double) totalDistance / totalWorkouts;
    }
    
    private double getAverageDuration(List<? extends Workout> workouts) {
        if (workouts.isEmpty()) {
            return 0.0;
        }
    
        double totalDuration = 0.0;
        int count = 0;
    
        for (Workout workout : workouts) {
            if (workout != null) {
                totalDuration += workout.getDuration();
                count++;
            }
        }
    
        return totalDuration / (double) count;
    }
    
    private double getAverageDurationOfWorkoutByPerson(Long id, List<Workout> workoutList,
            Class<? extends Workout> workoutClass) {
        double sum = 0;
        int count = 0;
        for (Workout workout : workoutList) {
            sum += workout.getDuration();
            count++;
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }
    
    public List<Person> readPersonsFromCSV() throws IOException {
        List<Person> persons = new ArrayList<>();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(PATH_TO_PERSON_IMPORT);
            bufferedReader = new BufferedReader(fileReader);
            String line;

            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] personLine = line.split(";");
                Long id = Long.parseLong(personLine[0]);
                String firstName = personLine[1];
                String lastName = personLine[2];
                int age = Integer.parseInt(personLine[3]);
                Person person = new Person(id, firstName, lastName, age);
                persons.add(person);
            }
        } catch (IOException e) {
            throw new IOException();
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return persons;
    }

    public void writeStatisticsToFile(List<Person> persons, List<Workout> workouts) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(PATH_TO_STATISTIC))) {
            for (Person person : persons) {
                List<Workout> personWorkouts = workouts.stream()
                        .filter(workout -> workout.getPersonId() == person.getId())
                        .collect(Collectors.toList());

                writer.write("- - - - - Person ( " + person.getId() + " ) - - - - -\n");
                writer.write(
                        "Name: " + person.getFirstName() + " " + person.getLastName() + " (" + person.getAge() + ")\n");
                writer.write("Number of Biking Workouts: "
                        + getNumberOfBikingWorkoutsByPerson(person.getId(), personWorkouts) + "\n");
                writer.write("Number of Swimming Workouts: "
                        + getNumberOfSwimmingWorkoutsByPerson(person.getId(), personWorkouts) + "\n");
                writer.write("Average duration: "
                        + String.format("%.0f",
                                getAverageDurationOfWorkoutByPerson(person.getId(), personWorkouts, Workout.class))
                        + "min\n");

                writer.newLine();
            }

            writer.write("- - - - - Statistics - - - - - - \n");
            writer.write("- - - - - Biking - - - - - - - - \n");
            List<BikingWorkout> bikingWorkouts = workouts.stream()
                    .filter(workout -> workout instanceof BikingWorkout)
                    .map(workout -> (BikingWorkout) workout)
                    .collect(Collectors.toList());

            writer.write("Average duration: " + String.format("%.0f", getAverageDuration(bikingWorkouts)) + "min\n");
            writer.write("Average distance: " + String.format("%.0f", getAverageDistance(bikingWorkouts)) + "m\n");
            writer.write("- MOUNTAIN: " + getNumberOfBikingWorkoutsByType(bikingWorkouts, BikingType.MOUNTAIN) + "\n");
            writer.write("- ROAD: " + getNumberOfBikingWorkoutsByType(bikingWorkouts, BikingType.ROAD) + "\n\n");

            writer.write("- - - - - Swimming - - - - - - \n");
            List<SwimmingWorkout> swimmingWorkouts = workouts.stream()
                    .filter(workout -> workout instanceof SwimmingWorkout)
                    .map(workout -> (SwimmingWorkout) workout)
                    .collect(Collectors.toList());

            writer.write("Average duration: " + String.format("%.0f", getAverageDuration(swimmingWorkouts)) + "min\n");
            writer.write("Average distance: " + String.format("%.0f", getAverageDistance(swimmingWorkouts)) + "m\n");
            writer.write("- BACKSTROKE: " + getNumberOfSwimmingWorkoutsByType(swimmingWorkouts, SwimmingType.BACKSTROKE)
                    + "\n");
            writer.write("- BUTTERFLY: " + getNumberOfSwimmingWorkoutsByType(swimmingWorkouts, SwimmingType.BUTTERFLY)
                    + "\n");
        } catch (IOException e) {
            throw new IOException("Failed to write statistics file: " + e.getMessage());
        }
    }

}
