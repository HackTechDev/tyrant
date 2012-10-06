package mikera.tyrant.perf;

import mikera.tyrant.Thing;


public interface IThingsInspector {
    void inspect(Thing thing);
    void printResults();
    void setup(String[] args);
}
