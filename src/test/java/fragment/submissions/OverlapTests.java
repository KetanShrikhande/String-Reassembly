package fragment.submissions;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OverlapTests {
    
    private String string1;
    private String string2;
    
    // Setup
    @Before
    public void setup(){
        this.string1 = "ABCDEF";
    }
    
    // Tear down
    @After
    public void teardown(){

    }

    //- ABCDEF" and "DEFG" overlap with overlap length 3
    @Test
    public void basicOverlapDEFGPass(){
        this.string2 = "DEFG";
        String overlap = StefanCross.overlap(string1, string2);
        Assert.assertEquals(3, overlap.length());
        Assert.assertEquals("DEF", overlap);
    }

    // - "ABCDEF" and "XYZABC" overlap with overlap length 3
    @Test
    public void basicOverlapXYZABCPass(){
        this.string2 = "XYZABC";
        String overlap = StefanCross.overlap(string1, string2);
        Assert.assertEquals(3, overlap.length());
        Assert.assertEquals("ABC", overlap);
    }

    //  - "ABCDEF" and "BCDE" overlap with overlap length 4
    @Test
    public void basicOverlapBCDEPass(){
        this.string2 = "BCDE";
        String overlap = StefanCross.overlap(string1, string2);
        Assert.assertEquals(4, overlap.length());
        Assert.assertEquals("BCDE", overlap);
    }

    // - "ABCDEF" and "XCDEZ" do *not* overlap (they have matching characters in the middle, 
    //   but the overlap does not extend to the end of either string).
    @Test
    public void basicOverlapXCDEZFail(){
        this.string2 = "XCDEZ";
        String overlap = StefanCross.overlap(string1, string2);
        Assert.assertEquals(0, overlap.length());
        Assert.assertEquals("", overlap);
    }
    
}

