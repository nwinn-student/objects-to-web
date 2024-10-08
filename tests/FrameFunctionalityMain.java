/**
 * Write a description of class FunctionalityTester here.
 * https://docs.oracle.com/javase/6/docs/technotes/tools/windows/classpath.html
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FrameFunctionalityMain{
    public static void main(String[] args){
        
        Helper help = new Helper();
        help.displayOptions();
        FrameFunctionalityTester tester = new FrameFunctionalityTester();
        for(int i = 0; i < 1; i++){
            tester.canFrameShow();
            tester.tearDown();
            tester.canFrameIconify();
            tester.tearDown();
            tester.canFrameClose();
            tester.tearDown();
        }
        tester.exit();
    }
}
