public class SwimmingWorkout extends Workout {

    private SwimmingType swimmingType;

    public SwimmingWorkout(Long id, int duration, int distance, Long personId, SwimmingType swimmingType) {
        super(id, duration, distance, personId);
        this.swimmingType = swimmingType;
    }

    public SwimmingType getSwimmingType() {
        return this.swimmingType;
    }

    public void setSwimmingType(SwimmingType swimmingType) {
        this.swimmingType = swimmingType;
    }

}