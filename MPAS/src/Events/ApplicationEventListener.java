package Events;

public interface ApplicationEventListener {

    /**
     * Handles the given application event.
     *
     * @param event The event to handle.
     */
    void handle(ApplicationEvent event);

}
