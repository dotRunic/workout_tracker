import java.io.*;
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

    private double getAverageDistance(List<? extends Workout> workouts) {
        double totalDistance = 0;
        for (Workout workout : workouts) {
            totalDistance += workout.getDistance() / (double) workouts.size();
        }
        return totalDistance;
    }

    private double getAverageDuration(List<? extends Workout> workouts) {
        double totalDuration = 0;
        for (Workout workout : workouts) {
            totalDuration += workout.getDuration() / (double) workouts.size();
        }
        return totalDuration;
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

    private int getNumberOfBikingWorkoutsByPerson(Long personId, List<Workout> workoutList) {
        int count = 0;
        for (Workout workout : workoutList) {
            if (workout.getPersonId() == personId && workout instanceof BikingWorkout) {
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

    public List<Person> readPersonsFromCSV() throws IOException {
        List<Person> persons = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_PERSON_IMPORT))) {
            String line = bufferedReader.readLine();
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
            throw new IOException("Error reading file");
        }
        return persons;
    }

    public void writeStatisticsToFile(List<Person> persons, List<Workout> workouts) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH_TO_STATISTIC))) {
            List<Workout> allWorkouts = new ArrayList<Workout>();
            for (Person person : persons) {
                List<Workout> personWorkouts = new ArrayList<>();
                for (Workout workout : workouts) {
                    if (workout.getPersonId() == person.getId()) {
                        personWorkouts.add(workout);
                    }
                }
                allWorkouts.addAll(personWorkouts);
                bufferedWriter.write("- - - - - Person ( " + person.getId() + " ) - - - - -\n");
                bufferedWriter.write(
                        "Name: " + person.getFirstName() + " " + person.getLastName() + " (" + person.getAge() + ")\n");
                bufferedWriter.write("Number of Biking Workouts: "
                        + getNumberOfBikingWorkoutsByPerson(person.getId(), personWorkouts));
                bufferedWriter.newLine();
                bufferedWriter.write("Number of Swimming Workouts: "
                        + getNumberOfSwimmingWorkoutsByPerson(person.getId(), personWorkouts));
                bufferedWriter.newLine();
                double averageDuration = getAverageDurationOfWorkoutByPerson(person.getId(), personWorkouts,
                        Workout.class);
                String formattedDuration = String.format("%.0f", averageDuration);
                bufferedWriter.write("Average duration: " + formattedDuration + "min \n");
            }

            bufferedWriter.write("- - - - - Statistics - - - - - - \n");
            bufferedWriter.write("- - - - - Biking - - - - - - - - \n");

            double averageDurationBiking = getAverageDuration(
                    allWorkouts.stream().filter(w -> w instanceof BikingWorkout).collect(Collectors.toList()));
            String formattedDurationBiking = String.format("%.0f", averageDurationBiking);
            bufferedWriter.write("Average duration: " + formattedDurationBiking + "min");
            bufferedWriter.newLine();
            double averageDistanceBiking = getAverageDistance(
                    allWorkouts.stream().filter(w -> w instanceof BikingWorkout).collect(Collectors.toList()));
            String formattedDistanceBiking = String.format("%.0f", averageDistanceBiking);
            bufferedWriter.write("Average distance: " + formattedDistanceBiking + "m");
            bufferedWriter.newLine();
            bufferedWriter
                    .write("- MOUNTAIN: "
                            + getNumberOfBikingWorkoutsByType(
                                    allWorkouts.stream().filter(w -> w instanceof BikingWorkout)
                                            .map(BikingWorkout.class::cast).collect(Collectors.toList()),
                                    BikingType.MOUNTAIN));
            bufferedWriter.newLine();
            bufferedWriter
                    .write("- ROAD: "
                            + getNumberOfBikingWorkoutsByType(
                                    allWorkouts.stream().filter(w -> w instanceof BikingWorkout)
                                            .map(BikingWorkout.class::cast).collect(Collectors.toList()),
                                    BikingType.ROAD));
            bufferedWriter.newLine();
            
            bufferedWriter.write("- - - - - Swimming - - - - - - \n");
            double averageDurationSwimming = getAverageDuration(
                    allWorkouts.stream().filter(w -> w instanceof SwimmingWorkout).collect(Collectors.toList()));
            String formattedDurationSwimming = String.format("%.0f", averageDurationSwimming);
            bufferedWriter.write("Average duration: " + formattedDurationSwimming + "min");
            bufferedWriter.newLine();
            double averageDistanceSwimming = getAverageDistance(
                    allWorkouts.stream().filter(w -> w instanceof SwimmingWorkout).collect(Collectors.toList()));
            String formattedDistanceSwimming = String.format("%.0f", averageDistanceSwimming);
            bufferedWriter.write("Average distance: " + formattedDistanceSwimming + "m");
            bufferedWriter.newLine();
            bufferedWriter
                    .write("- BACKSTROKE: " + getNumberOfSwimmingWorkoutsByType(
                            allWorkouts.stream().filter(w -> w instanceof SwimmingWorkout)
                                    .map(SwimmingWorkout.class::cast).collect(Collectors.toList()),
                            SwimmingType.BACKSTROKE));
            bufferedWriter.newLine();
            bufferedWriter
                    .write("- BUTTERFLY: " + getNumberOfSwimmingWorkoutsByType(
                            allWorkouts.stream().filter(w -> w instanceof SwimmingWorkout)
                                    .map(SwimmingWorkout.class::cast).collect(Collectors.toList()),
                            SwimmingType.BUTTERFLY));
            bufferedWriter.newLine();
        }
    }
}
