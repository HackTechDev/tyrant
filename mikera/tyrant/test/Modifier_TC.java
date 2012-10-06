package mikera.tyrant.test;

import mikera.tyrant.Lib;
import mikera.tyrant.Modifier;
import mikera.tyrant.RPG;
import mikera.tyrant.Thing;

/**
 * @author Chris Grindstaff chris@gstaff.org
 */
public class Modifier_TC extends TyrantTestCase {
    private Thing rabbit;
    private Thing berry;
    private Thing berry2;

    public void setUp() throws Exception {
        super.setUp();
        rabbit = Lib.create("rabbit");
        berry = Lib.extend("berry", "base item");
        berry2 = Lib.extend("berry2", "base item");
    }
   
    public void testLinear_carried() {
       	int baseSK = rabbit.getStat("SK");
    	
        berry.add("CarriedModifiers", Modifier.linear("SK", 200, 9));
        rabbit.addThing(berry);
        assertEquals(2*baseSK+9 , rabbit.getStat("SK"));
    }
    
    public void testLinear_dropped() {
        int baseSK = rabbit.getStat("SK");
        
        berry.add("CarriedModifiers", Modifier.linear("SK", 200, 9));
        rabbit.addThing(berry);
        assertEquals(2*baseSK+9 , rabbit.getStat("SK"));
        rabbit.dropThing(berry);
        assertEquals(baseSK,rabbit.getStat("SK"));
    }

    public void testLinear_carriedSeveral() {
       	int baseSK = rabbit.getStat("SK");
    	
    	berry.add("CarriedModifiers", Modifier.linear("SK", 100, 9));    // 3 + 9  = 12
        berry2.add("CarriedModifiers", Modifier.linear("SK", 100, 9));   // 12 + 9 = 21
        
        rabbit.addThing(berry);
        rabbit.addThing(berry2);
        assertEquals(baseSK+9+9, rabbit.getStat("SK"));
    }

    public void testBonus() {
       	int baseSK = rabbit.getStat("SK");
    	
        berry.add("CarriedModifiers", Modifier.bonus("SK", 17));
        
        rabbit.addThing(berry);
        assertEquals(baseSK+17, rabbit.getStat("SK"));
        assertEquals(baseSK, rabbit.getBaseStat("SK"));
    }
    
    public void testConstant() {
        berry.add("CarriedModifiers", Modifier.constant("SK", 1000));
        
        rabbit.addThing(berry);
        assertEquals(1000, rabbit.getStat("SK"));
    }
    
    public void testConstant_several() {
        // Is this working as designed? The first constant shortcircuits the other ones
    	// mikera - yes, this is as designed
        berry.add("CarriedModifiers", Modifier.constant("SK", 9));
        berry.add("CarriedModifiers", Modifier.constant("SK", 1));
        
        rabbit.addThing(berry);
        assertEquals(new Integer(9), rabbit.get("SK"));
    }
    
    public void testConstant_several_flipped() {
        // Is this working as designed? The first constant shortcircuits the other ones
        berry.add("CarriedModifiers", Modifier.constant("SK", 1));
        berry.add("CarriedModifiers", Modifier.constant("SK", 9));
        
        rabbit.addThing(berry);
        assertEquals(new Integer(1), rabbit.get("SK"));
    }
    
    public void testMultipleModifiers() {
    	int baseSK = rabbit.getStat("SK");

    	berry.add("CarriedModifiers", Modifier.bonus("SK", 9));
        berry.add("CarriedModifiers", Modifier.bonus("SK", 4));
        
        rabbit.addThing(berry);
        assertEquals(baseSK+13, rabbit.getStat("SK"));
    }
    
    public void testAddModifier() {
    	int baseST = rabbit.getStat("ST");
    	
        assertEquals(baseST, rabbit.getStat("ST"));
        berry.addThing(Lib.create("strength rune"));
        rabbit.addThing(berry);
        assertEquals(baseST, rabbit.getStat("ST"));
        assertTrue(rabbit.wield(berry, RPG.WT_MAINHAND));
        assertTrue(baseST< rabbit.getStat("ST"));
    }
    
    public void testDynamicModifiers() {
    	int baseSK = rabbit.getStat("SK");
   
        berry.add("CarriedModifiers", Modifier.bonus("SK", 1));
        rabbit.addThing(berry);
        assertEquals(baseSK+1, rabbit.getStat("SK"));
        
        berry.add("CarriedModifiers", Modifier.bonus("SK", 2));
        assertEquals(baseSK+3, rabbit.getStat("SK"));
    }
    
    public void testMultilevel() {
    	int baseSK = rabbit.getStat("SK");
    	   
        berry2.add("CarriedModifiers", Modifier.bonus("SK", 10));
        berry.add("CarriedModifiers", Modifier.bonus("SK", 1));
        berry.addThing(berry2);
        rabbit.addThing(berry);
        
        assertEquals(baseSK+1, rabbit.getStat("SK"));
    }
    
    public void testPriority() {
        Thing r1=Lib.create("ring of blindness");
        Thing r2=Lib.create("ring of prevent blindness");
        
        rabbit.addThing(r1);
        rabbit.addThing(r2);
        
        // ring of prevent blindness has higher priority
        // so should cancal effect of other ring
        assertTrue(rabbit.wield(r1,RPG.WT_LEFTRING));
        assertTrue(rabbit.getFlag("IsBlind"));
        assertTrue(rabbit.wield(r2,RPG.WT_RIGHTRING));
        assertFalse(rabbit.getFlag("IsBlind"));
    }
    
}
