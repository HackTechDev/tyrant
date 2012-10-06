/*
 * Created on 27-Dec-2004
 *
 * By Mike Anderson
 */
package mikera.tyrant.test;

import mikera.tyrant.Lib;
import mikera.tyrant.Potion;
import mikera.tyrant.Thing;

/**
 * @author Mike
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Potion_TC extends TyrantTestCase {
    public void testHolyWater() {
        Thing rock = Lib.create("rock");
        assertTrue(!rock.getFlag("IsBlessed"));
        assertTrue(!rock.getFlag("IsCursed"));
        
        Thing hw=Lib.create("potion of holy water");
        Potion.dip(null,rock,hw);
        
        assertTrue(rock.getFlag("IsBlessed"));
    }
    
    public void testUnholyWater() {
        Thing rock = Lib.create("rock");
        assertTrue(!rock.getFlag("IsBlessed"));
        assertTrue(!rock.getFlag("IsCursed"));
        
        Thing hw=Lib.create("potion of unholy water");
        Potion.dip(null,rock,hw);
        
        assertTrue(rock.getFlag("IsCursed"));
    }
    
    public void testPotionOfRepair() {
        Thing rock = Lib.create("rock");
        rock.set("HPS",1);
        
        Thing pot=Lib.create("potion of perfect repair");
        Potion.dip(null,rock,pot);
        
        assertEquals(rock.getStat("HPSMAX"), rock.getStat("HPS"));
    }
}
