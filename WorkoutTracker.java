import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class WorkoutTracker {
    public static void main(String[] args) throws IOException {

        List<Person> people = FileUtils.readPersonsFromCsv();
        List<Workout> workouts = new ArrayList<>();
        people.forEach(p -> {
            long id = 0;
            workouts.add(new BikingWorkout(id++, 10, 100, p.getId(), BikingType.MOUNTAIN));
            workouts.add(new BikingWorkout(id++, 20, 100, p.getId(), BikingType.ROAD));
            workouts.add(new SwimmingWorkout(id++, 30, 100, p.getId(), SwimmingType.BACKSTROKE));
            workouts.add(new SwimmingWorkout(id++, 40, 100, p.getId(), SwimmingType.BUTTERFLY));
        });

        FileUtils.writeStatisticToFile(people, workouts);

    }
}