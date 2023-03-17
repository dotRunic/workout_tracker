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

    public static void writeStatisticToFile(List<Person> people, List<Workout> workouts) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PATH_TO_STATISTIC))) {
            for (Person person : people) {
                List<Workout> personWorkouts = new ArrayList<>();
                for (Workout workout : workouts) {
                    if (workout.getPersonId() == person.getId()) {
                        personWorkouts.add(workout);
                    }
                }
                writer.println("Name: " + person.getFirstName() + " (" + person.getAge() + ")");
                writer.println("Total workouts: " + personWorkouts.size());
                writer.println("Total distance: " + getAverageDistance(personWorkouts));
                writer.println("Average distance (Biking): " + getAverageDistanceByWorkout(personWorkouts, BikingWorkout.class));
                writer.println("Average distance (Swimming): " + getAverageDistanceByWorkout(personWorkouts, SwimmingWorkout.class));
                writer.println();
            }
        }
    }

    private static double getAverageDistanceByWorkout(List<Workout> workouts, Class<?> workout) {
        if (workouts != null && !workouts.isEmpty() && workout != null) {
            return getAverageDistanceByWorkout(workouts.stream().filter(workout::isInstance).toList(), workout);
        }
        return 0.0;
    }

    private static double getAverageDurationByWorkout(List<Workout> workouts, Class<?> workout) {
        if (workouts != null && !workouts.isEmpty() && workout != null) {
            return getAverageDurationByWorkout(workouts.stream().filter(workout::isInstance).toList(), workout);
        }
        return 0.0;
    }

    private static int getNumberOfBikingWorkoutByType(List<Workout> workouts, BikingType type) {
        if (workouts != null && !workouts.isEmpty() && type != null) {
            return workouts.stream()
                    .filter(BikingWorkout.class::isInstance)
                    .map(BikingWorkout.class::cast)
                    .filter(p -> type == p.getType())
                    .toList().size();
        }
        return 0;
    }

    private static int getNumberOfSwimmingWorkoutByType(List<Workout> workouts, SwimmingType type) {
        if (workouts != null && !workouts.isEmpty() && type != null) {
            return workouts.stream()
                    .filter(SwimmingWorkout.class::isInstance)
                    .map(SwimmingWorkout.class::cast)
                    .filter(p -> type == p.getType())
                    .toList().size();
        }
        return 0;
    }

    private static double getAverageDurationOfWorkoutByPerson(Long personId, List<Workout> workoutList) {
        if (personId != null && workoutList != null && !workoutList.isEmpty()) {
            return getAverageDurationOfWorkoutByPerson(personId, workoutList.stream()
                    .filter(p -> p.getPersonId().equals(personId))
                    .toList());
        }
        return 0;
    }

    private static int getNumberOfBikingWorkoutByPerson(Long personId, List<Workout> workoutList) {
        if (personId != null && workoutList != null && !workoutList.isEmpty()) {
            return workoutList.stream()
                    .filter(p -> p.getPersonId().equals(personId))
                    .filter(BikingWorkout.class::isInstance)
                    .toList().size();
        }
        return 0;
    }

    private static int getNumberOfSwimmingWorkoutByPerson(Long personId, List<Workout> workoutList) {
        if (personId != null && workoutList != null && !workoutList.isEmpty()) {
            return workoutList.stream()
                    .filter(p -> p.getPersonId().equals(personId))
                    .filter(SwimmingWorkout.class::isInstance)
                    .toList().size();
        }
        return 0;
    }

    private static double getAverageDistance(List<? extends Workout> workouts) {
        if (workouts != null && !workouts.isEmpty()) {
            return workouts.stream()

                    .mapToDouble(Workout::getDistance)
                    .average().orElse(0.0);
        }
        return 0.0;
    }

    private static double getAverageDuration(List<? extends Workout> workouts) {
        if (workouts != null && !workouts.isEmpty()) {
            return workouts.stream()
                    .mapToDouble(Workout::getDuration)
                    .average().orElse(0.0);
        }
        return 0.0;
    }

}