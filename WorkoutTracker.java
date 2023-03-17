import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class WorkoutTracker {
    public static void main(String[] args) throws IOException {

        List<Person> people = FileUtils.readPersonsFromCsv();
        List<Workout> workouts = new ArrayList<>();
        people.forEach(p -> {
            long id = 0;
            workouts.add(new BikingWorkout(id, 5, 15, id++, BikingType.MOUNTAIN));
            workouts.add(new BikingWorkout(id, 5, 20, id++, BikingType.ROAD));
            workouts.add(new SwimmingWorkout(id, 10, 25, id++, SwimmingType.BACKSTROKE));
            workouts.add(new SwimmingWorkout(id, 15, 30, id++, SwimmingType.BUTTERFLY));
        });

        FileUtils.writeStatisticToFile(people, workouts);

    }
}