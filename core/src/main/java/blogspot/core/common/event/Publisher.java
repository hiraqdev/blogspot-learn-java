package blogspot.core.common.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Publisher {

    private final HashMap<String, List<Listener>> listeners = new HashMap<>();

    /**
     * This method will be used when there are some or more listeners that need to register
     * them self into as an observer from some specific events. A set of listeners from event X
     * will not be called if current operation raise an event Y
     *
     * @param listener Any object that implement event.Listener interface
     * @param eventName A main event that want to be observed
     */
    public void registerListener(Listener listener, String eventName) {
        List<Listener> listeners = this.listeners.get(eventName);
        if (listeners == null) {
            listeners = new ArrayList<>();
        }

        listeners.add(listener);
        this.listeners.put(eventName, listeners);
    }

    /**
     *
     * @param event A specific event raised by some aggregate
     */
    protected void raiseEvent(DomainEvent event) {
        List<Listener> listeners = this.listeners.get(event.name());

        if (listeners != null) {
            for (Listener listener : listeners) {
                listener.on(event);
            }
        }
    }
}
