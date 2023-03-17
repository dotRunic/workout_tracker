import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class WorkoutTracker {
    public static void main(String[] args) throws IOException {

        List<Person> people = FileUtils.readPersonsFromCsv();
        List<Workout> workouts = new ArrayList<>();
        people.forEach(p -> {
            long id = 0;
            workouts.add(new BikingWorkout(id++, 1, 15, p.getId(), BikingType.MOUNTAIN));
            workouts.add(new BikingWorkout(id++, 5, 20, p.getId(), BikingType.ROAD));
            workouts.add(new SwimmingWorkout(id++, 10, 25, p.getId(), SwimmingType.BACKSTROKE));
            workouts.add(new SwimmingWorkout(id++, 15, 30, p.getId(), SwimmingType.BUTTERFLY));
        });

        FileUtils.writeStatisticToFile(people, workouts);

    }
}