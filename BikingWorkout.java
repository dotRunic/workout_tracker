public class BikingWorkout extends Workout {

    private BikingWorkout bikingWorkout;

    public BikingWorkout(Long id, int duration, int distance, Long personId) {
        super(id, duration, distance, personId);
    }

    public BikingWorkout getBikingWorkout() {
        return this.bikingWorkout;
    }

    public void setBikingWorkout(BikingWorkout bikingWorkout) {
        this.bikingWorkout = bikingWorkout;
    }

}
