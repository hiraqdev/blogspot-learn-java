package blogspot.core.common.event;

public interface Listener {
    void on(DomainEvent event);
}
