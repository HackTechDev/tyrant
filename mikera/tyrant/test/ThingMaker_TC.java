package mikera.tyrant.test;

import junit.framework.TestCase;
import mikera.tyrant.Lib;
import mikera.tyrant.Map;
import mikera.tyrant.Thing;
import mikera.tyrant.author.Designer;
import mikera.tyrant.author.ThingMaker;

public class ThingMaker_TC extends TestCase {
    private ThingMaker thingMaker;
    private StringBuffer actual;

    protected void setUp() throws Exception {
        thingMaker = new ThingMaker();
        actual = new StringBuffer();
    }
    
    public void testStoring() throws Exception {
        Map map = new Map(3, 3);
        map.addThing("carrot", 0, 0);
        map.addThing("parsnip", 1, 0);
        map.addThing("beefcake", 1, 0);
        String expected = 
            "\r\n---Things---\r\n" + 
            "0x0 carrot\r\n" + 
            "1x0 beefcake\r\n" +
            "1x0 parsnip\r\n" + 
            "---Things---\r\n" + 
            "";
        thingMaker.storeThings(map, actual);
        assertEquals(expected, actual.toString());
    }
    
    public void testStoringIs() throws Exception {
        Map map = new Map(3, 3);
        map.addThing(Designer.getFlagged("IsFood"), 0, 0);
        String expected = 
            "\r\n---Things---\r\n" + 
            "0x0 [IsFood]\r\n" + 
            "---Things---\r\n" + 
            "";
        thingMaker.storeThings(map, actual);
        assertEquals(expected, actual.toString());
    }
    
    public void testThings_modififed() throws Exception {
        Map map = new Map(3, 3);
        Thing carrot = Lib.create("carrot");
        carrot.set("Number", "6");
        carrot.set("Level", "16");
        map.addThing(carrot, 0, 0);
        map.addThing("parsnip", 1, 0);
        map.addThing("beefcake", 1, 0);
        String expected = 
            "\r\n---Things---\r\n" + 
            "0x0 carrot\r\n" + 
            ThingMaker.SPACES_3 + "Level = 16\r\n" + 
            ThingMaker.SPACES_3 + "Number = 6\r\n" + 
            "1x0 beefcake\r\n" + 
            "1x0 parsnip\r\n" +
            "---Things---\r\n"; 
        thingMaker.storeThings(map, actual);
        assertEquals(expected, actual.toString());
    }


    public void testThings_create() throws Exception {
        String mapText = "" +
                "---Legend---\r\n" + 
                "Width = 3 \r\n" + 
                "Height = 3\r\n" + 
                "EntranceX = 3 \r\n" + 
                "EntranceY = 3\r\n" + 
                "---Legend---\r\n" + 
                "---Things---\r\n" + 
                "0x0 carrot\r\n" + 
                "2x2 carrot\r\n" +
                "2x2 parsnip\r\n" +
                "---Things---";
        Map map = new Map(3, 3);
        thingMaker.addThingsToMap(map, mapText);
        assertEquals("carrot", map.getThings(0, 0)[0].name());
        assertEquals("parsnip", map.getThings(2, 2)[0].name());
        assertEquals("carrot", map.getThings(2, 2)[1].name());
    }
    
    public void testThings_createWithAttributes() throws Exception {
        String mapText = "" +
                "---Things---\r\n" + 
                "0x0 carrot\r\n" +
                ThingMaker.SPACES_3 + "Level = 16\r\n" + 
                ThingMaker.SPACES_3 + "Number = 6\r\n" + 
                "2x2 carrot\r\n" +
                "2x2 parsnip\r\n" +
                "---Things---";
        Map map = new Map(3, 3);
        thingMaker.addThingsToMap(map, mapText);
        Thing carrot = map.getThings(0, 0)[0];
        assertEquals("carrot", carrot.name());
        assertEquals(16, carrot.getStat("Level"));
        assertEquals(6, carrot.getStat("Number"));
        assertEquals("parsnip", map.getThings(2, 2)[0].name());
        assertEquals("carrot", map.getThings(2, 2)[1].name());
    }
}
