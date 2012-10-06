package mikera.tyrant;


public interface IThingFilter {
    boolean accept(Thing thing, String query);
}
