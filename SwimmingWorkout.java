public class SwimmingWorkout extends Workout {

    private SwimmingType swimmingType;

    public SwimmingWorkout(Long id, int duration, int distance, Long personId) {
        super(id, duration, distance, personId);
        // TODO Auto-generated constructor stub
    }

    public SwimmingType getSwimmingType() {
        return this.swimmingType;
    }

    public void setSwimmingType(SwimmingType swimmingType) {
        this.swimmingType = swimmingType;
    }

}