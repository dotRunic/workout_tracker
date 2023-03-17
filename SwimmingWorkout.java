public class SwimmingWorkout extends Workout {

    private SwimmingType swimmingType;

    public SwimmingWorkout(Long id, int duration, int distance, Long personId, SwimmingType backstroke) {
        super(id, duration, distance, personId);
    }

    public SwimmingType getSwimmingType() {
        return this.swimmingType;
    }

    public void setSwimmingType(SwimmingType swimmingType) {
        this.swimmingType = swimmingType;
    }

    public SwimmingType getType() {
        return null;
    }

}