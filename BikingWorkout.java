public class BikingWorkout extends Workout {

    private BikingType bikingType;

    public BikingWorkout(Long id, int duration, int distance, Long personId, BikingType bikingType) {
        super(id, duration, distance, personId);
        this.bikingType = bikingType;
    }

    public BikingType getBikingType() {
        return this.bikingType;
    }

    public void setBikingType(BikingType bikingType) {
        this.bikingType = bikingType;
    }

}
