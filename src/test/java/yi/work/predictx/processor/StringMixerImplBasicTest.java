package yi.work.predictx.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by hy10121012 on 2018/3/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
        ({"classpath:spring-context.xml"})
public class StringMixerImplBasicTest {

    @Autowired
    @Qualifier(value = "basicMixer")
    StringMixer stringMixer;


    @Test
    public void ExampleCase() throws Exception {
        String[] input = {"A aaaa bb c",
                "& aaa bbb c d"};

        String returnStr = stringMixer.min(input);
        assertEquals("1:aaaa/2:bbb",returnStr);

    }


    @Test
    public void ExampleCase2() throws Exception {
        String[] input = {"my&friend&Paul has heavy hats! &",
                          "my friend John has many many friends &"};

        String returnStr = stringMixer.min(input);
        assertEquals("2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss",returnStr);
    }

    @Test
    public void ExampleCase3() throws Exception {
        String[] input = {"mmmmm m nnnnn y&friend&Paul has heavy hats! &",
                "my frie n d Joh n has ma n y ma n y frie n ds n&"};

        String returnStr = stringMixer.min(input);
        assertEquals("1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss",returnStr);
    }



    @Test
    public void ExampleCase4() throws Exception {
        String[] input = {"Are the kids at home? aaaaa fffff",
                "Yes they are here! aaaaa fffff"};

        String returnStr = stringMixer.min(input);
        assertEquals("=:aaaaaa/2:eeeee/=:fffff/1:tt/2:rr/=:hh",returnStr);
    }


    @Test
    public void NullCaseTest() {
        String returnStr = stringMixer.min(null);
        assertEquals("", returnStr);
    }

    @Test
    public void NullElementCaseTest() {
        String[] input = {null,
                null, null, null};

        String returnStr = stringMixer.min(input);
        assertEquals("", returnStr);
    }


    @Test
    public void NullHlafElementCaseTest() {
        String[] input = {null,
                null, "aaaaaaaa", null};

        String returnStr = stringMixer.min(input);
        assertEquals("3:aaaaaaaa", returnStr);
    }


    @Test
    public void NonLowerCaseChartCaseTest() {

        String[] input = {"21342341234",
                "AAAAAAABBBBBFFFFF", "@#$!@#%@#$%@#$%", "   PLP'L:````\"M?M"};
        String returnStr = stringMixer.min(input);
        assertEquals("", returnStr);
    }

    @Test
    public void massiveDuplicateTest() {
        String[] input = {"AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll",
                "AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll",
                "AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll",
                "AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll",
                "AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll",
                "AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll",
                "AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll",
                "AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll",
                "AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll",
                "AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll",
                "AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll"


        };

        String returnStr = stringMixer.min(input);
        assertEquals("=:lllllllllllll/=:ttttttttt/=:aaaaaa/=:kkkkkk", returnStr);
    }

    @Test
    public void volumeTest(){
        List<String> a = new ArrayList<>(500);

        for(int i=0; i<500;i++){
            a.add("AAAA KKKKK aaaaaa kkkkkk tttt ttttt llllll lllllll");
        }
        a.toArray(new String[a.size()]);
        long start = System.currentTimeMillis();
        String returnStr = stringMixer.min(a.toArray(new String[a.size()]));
        long end = System.currentTimeMillis();


        // when handle 500 string it should take less than 350ms
        assertTrue((end-start)<350);


    }

}