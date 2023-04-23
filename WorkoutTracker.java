import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WorkoutTracker {
    public static void main(String[] args) throws IOException {

        FileUtils fileUtils = new FileUtils();
        List<Person> people = fileUtils.readPersonsFromCSV();
        List<Workout> workouts = new ArrayList<>();

        // Person 1
        workouts.add(new BikingWorkout(0L, 8, 15, 1L, BikingType.MOUNTAIN));
        workouts.add(new BikingWorkout(1L, 10, 35, 1L, BikingType.ROAD));
        workouts.add(new SwimmingWorkout(2L, 55, 55, 1L, SwimmingType.BACKSTROKE));
        workouts.add(new SwimmingWorkout(3L, 20, 75, 1L, SwimmingType.BUTTERFLY));
        workouts.add(new SwimmingWorkout(4L, 250, 95, 1L, SwimmingType.BUTTERFLY));

        // Person 2
        workouts.add(new BikingWorkout(0L, 5, 5, 2L, BikingType.MOUNTAIN));
        workouts.add(new BikingWorkout(1L, 120, 25, 2L, BikingType.ROAD));
        workouts.add(new SwimmingWorkout(2L, 75, 45, 2L, SwimmingType.BACKSTROKE));
        workouts.add(new SwimmingWorkout(3L, 20, 65, 2L, SwimmingType.BUTTERFLY));
        workouts.add(new SwimmingWorkout(4L, 85, 85, 2L, SwimmingType.BUTTERFLY));

        // Person 3
        workouts.add(new BikingWorkout(0L, 5, 175, 3L, BikingType.MOUNTAIN));
        workouts.add(new BikingWorkout(1L, 1, 205, 3L, BikingType.ROAD));
        workouts.add(new SwimmingWorkout(2L, 15, 335, 3L, SwimmingType.BACKSTROKE));
        workouts.add(new SwimmingWorkout(3L, 20, 45, 3L, SwimmingType.BUTTERFLY));
        workouts.add(new SwimmingWorkout(4L, 12, 55, 4L, SwimmingType.BUTTERFLY));

        // Person 4
        workouts.add(new BikingWorkout(0L, 7, 15, 4L, BikingType.MOUNTAIN));
        workouts.add(new BikingWorkout(1L, 77, 50, 4L, BikingType.ROAD));
        workouts.add(new SwimmingWorkout(2L, 21, 35, 4L, SwimmingType.BACKSTROKE));
        workouts.add(new SwimmingWorkout(3L, 49, 45, 4L, SwimmingType.BUTTERFLY));
        workouts.add(new SwimmingWorkout(4L, 25, 5, 4L, SwimmingType.BUTTERFLY));

        // Person 5
        workouts.add(new BikingWorkout(0L, 5, 15, 5L, BikingType.MOUNTAIN));
        workouts.add(new BikingWorkout(1L, 10, 25, 5L, BikingType.ROAD));
        workouts.add(new SwimmingWorkout(2L, 15, 35, 5L, SwimmingType.BACKSTROKE));
        workouts.add(new SwimmingWorkout(3L, 20, 45, 5L, SwimmingType.BUTTERFLY));
        workouts.add(new SwimmingWorkout(4L, 25, 55, 5L, SwimmingType.BUTTERFLY));

        fileUtils.writeStatisticsToFile(people, workouts);

    }
}