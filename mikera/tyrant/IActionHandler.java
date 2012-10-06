package mikera.tyrant;


public interface IActionHandler {
    /**
     * Answer true if event propagation should stop, false if it should continue.
     */
    boolean handleAction(Thing actor, Action action, boolean isShiftDown);
}
