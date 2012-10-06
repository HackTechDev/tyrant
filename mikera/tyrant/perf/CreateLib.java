package mikera.tyrant.perf;

import mikera.tyrant.Lib;
import mikera.tyrant.RPG;


public class CreateLib implements IWork {
    public void run() {
        Lib.instance();
    }

    public void setUp() {
        RPG.setRandSeed(0);
        Lib.clear();
    }

    public String getMessage() {
        return "";
    }
}