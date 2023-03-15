abstract class Workout {
    private Long id;
    private int duration;
    private int distance;
    private Long personId;

    public Workout(Long id, int duration, int distance, Long personId) {
        this.id = id;
        this.duration = duration;
        this.distance = distance;
        this.personId = personId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Long getPersonId() {
        return this.personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
