package cellularSim;

/**
 * Abstract event scheduled at a specific simulation date.
 */
abstract class Event implements Comparable<Event> {
    private final long date;

    public Event(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public abstract void execute();

    @Override
    public int compareTo(Event other) {
        return Long.compare(this.date, other.date);
    }
}

