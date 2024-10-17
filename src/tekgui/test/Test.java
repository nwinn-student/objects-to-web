package tekgui.test;
import tekgui.test.window.*;
import tekgui.window.TEKFrame;
import tekgui.helper.Helper;

import java.util.Arrays;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Write a description of class Test here.
 * https://www.geeksforgeeks.org/class-getmethods-method-in-java-with-examples/
 * https://www.geeksforgeeks.org/java-lang-class-class-java-set-1/
 * https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html
 * https://stackoverflow.com/questions/28400408/what-is-the-new-way-of-getting-all-methods-of-a-class-including-inherited-defau
 * https://stackoverflow.com/questions/8524011/java-reflection-how-can-i-get-the-all-getter-methods-of-a-java-class-and-invoke
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Test{
    // wee
    protected Internal frame;
    private List<Method> testMethods;
    private List<Method> currentMethods;
    private boolean mustPause = false;
    private void computeMethods(){
        if(testMethods == null){
            testMethods = Arrays.asList(Test.class.getMethods());
        }
        if(currentMethods == null){
            currentMethods = Arrays.stream(getClass().getMethods())
                .filter(x->!testMethods.contains(x))
                .collect(Collectors.toList());
        }
    }
    private void invokeMethods(){
        if(currentMethods == null){
            computeMethods();
        }
        initFrame();
        for(Method m : currentMethods){
            if(mustPause){
                mustPause = !mustPause;
                break;}
            try{
                m.invoke(this);
            } catch (Exception e){
                e.printStackTrace();
            }
            tearDown();
        }
    }
    public List<Method> getMethods(){
        if(currentMethods == null){
            computeMethods();
        }
        return currentMethods;
    }
    public void haltTest(){
        // should just halt the tests
        mustPause = true;
    }
    public void test(Method m,  Object... args){
        initFrame();
        try{
            m.invoke(this, args);
        } catch (Exception e){
            e.printStackTrace();
        }
        tearDown();
    }
    public void testAll(){
        // need to filter out private, protected, and Object and this methods
        invokeMethods();
        //System.out.println(getMethods());
        
        //System.out.println(Arrays.toString(getClass().getMethods()));
        //System.out.println(getClass());
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    public void tearDown(){
        Helper.wait(6*Helper.SPEED_MS);
        frame.dispose();
        frame = null;
        frame = TestFrame.respawnInternal(new TEKFrame(), true);
    }
    private void initFrame(){
        if(frame == null){
            frame = TestFrame.respawnInternal(new TEKFrame(), true);
        }
    }
    @Override
    public String toString(){
        return ""+getClass().getName();
    }
}
