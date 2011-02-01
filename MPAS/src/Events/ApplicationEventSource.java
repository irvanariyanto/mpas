package Events;


public interface ApplicationEventSource {


    void addListener(ApplicationEventListener listener);

    /**
     * Removes the given application event listener from this source.
     *
     * @param listener The listener to be removed (if the given listener is not registered with this source, this method
     *        wiill do nothing and exit quietly).
     */
    void removeListener(ApplicationEventListener listener);

    /**
     * Removes all application event listeners registered with this source.
     */
    void clearListeners();

}

