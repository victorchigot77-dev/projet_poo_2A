package SwarmSim;

import java.util.PriorityQueue;

/**
 * Discrete-event manager: advances current date and executes due events.
 */
class EventManager {
    private long currentDate = 0;
    private final PriorityQueue<Event> queue = new PriorityQueue<>();

    public long getCurrentDate() {
        return currentDate;
    }

    public void addEvent(Event e) {
        if (e != null) queue.add(e);
    }

    public void next() {
        currentDate++;
        while (!queue.isEmpty() && queue.peek().getDate() <= currentDate) {
            queue.poll().execute();
        }
    }

    public boolean isFinished() {
        return queue.isEmpty();
    }

    public void restart() {
        currentDate = 0;
        queue.clear();
    }
}

