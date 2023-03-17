import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
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
            Long id = Long.parseLong(data[0]);
            String firstName = data[1];
            String lastName = data[2];
            int Age = Integer.parseInt(data[3]);
            Person person = new Person(id, firstName, lastName, Age);

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
                writer.println(
                        "Name: " + person.getFirstName() + " " + person.getLastName() + " (" + person.getAge() + ")");
                writer.println("Total workouts: " + personWorkouts.size());
                writer.println("Total distance: " + getAverageDistance(personWorkouts));
                writer.println("Average distance (Biking): "
                        + getAverageDistanceByWorkout(personWorkouts, BikingWorkout.class));
                writer.println("Average distance (Swimming): "
                        + getAverageDistanceByWorkout(personWorkouts, SwimmingWorkout.class));
                writer.println();
            }
        }
    }

    private static double getAverageDistanceByWorkout(List<Workout> workouts, Class<?> workoutType) {
        double sum = 0.0;
        int count = 0;

        for (Workout workout : workouts) {
            if (workoutType.isInstance(workout)) {
                sum += workout.getDistance();
                count++;
            }
        }

        if (count > 0) {
            return sum / count;
        } else {
            return 0.0;
        }
    }

    private static double getAverageDurationByWorkout(List<Workout> workouts, Class<?> workoutType) {
        double sum = 0.0;
        int count = 0;

        for (Workout workout : workouts) {
            if (workoutType.isInstance(workout)) {
                sum += workout.getDuration();
                count++;
            }
        }

        if (count > 0) {
            return sum / count;
        } else {
            return 0.0;
        }
    }

    private static int getNumberOfBikingWorkoutByType(List<Workout> workouts, BikingType type) {
        int count = 0;

        for (Workout workout : workouts) {
            if (workout instanceof BikingWorkout) {
                BikingWorkout bikingWorkout = (BikingWorkout) workout;
                if (bikingWorkout.getType() == type) {
                    count++;
                }
            }
        }

        return count;
    }

    private static int getNumberOfSwimmingWorkoutByType(List<Workout> workouts, SwimmingType type) {
        int count = 0;

        for (Workout workout : workouts) {
            if (workout instanceof SwimmingWorkout) {
                SwimmingWorkout swimmingWorkout = (SwimmingWorkout) workout;
                if (swimmingWorkout.getSwimmingType() == type) {
                    count++;
                }
            }
        }

        return count;
    }

    private static double getAverageDurationOfWorkoutByPerson(Long personId, List<Workout> workoutList) {
        double totalDuration = 0;
        int count = 0;

        for (Workout workout : workoutList) {
            if (workout.getPersonId().equals(personId)) {
                totalDuration += workout.getDuration();
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        } else {
            return totalDuration / count;
        }
    }

    public static int getNumberOfBikingWorkoutsForPerson(Long personId, List<Workout> workouts) {
        int count = 0;
        for (Workout workout : workouts) {
            if (workout instanceof BikingWorkout && workout.getPersonId().equals(personId)) {
                count++;
            }
        }
        return count;
    }

    private static int getNumberOfSwimmingWorkoutsForPerson(Long personId, List<Workout> workoutList) {
        int count = 0;
        for (Workout workout : workoutList) {
            if (workout instanceof SwimmingWorkout && workout.getPersonId().equals(personId)) {
                count++;
            }
        }
        return count;
    }

    private static double getAverageDistance(List<? extends Workout> workouts) {
        double sum = 0.0;
        for (Workout workout : workouts) {
            sum += workout.getDistance();
        }
        return workouts.isEmpty() ? 0.0 : sum / workouts.size();
    }

    private static double getAverageDuration(List<? extends Workout> workouts) {
        double totalDuration = 0.0;
        int count = 0;
        for (Workout workout : workouts) {
            totalDuration += workout.getDuration();
            count++;
        }
        return count > 0 ? totalDuration / count : 0.0;
    }

}