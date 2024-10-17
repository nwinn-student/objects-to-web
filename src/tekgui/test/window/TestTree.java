package tekgui.test.window;
import tekgui.test.Test;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.lang.reflect.Method;
/**
 * To be later used.
 * https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TreeDemoProject/src/components/TreeDemo.java
 * https://stackoverflow.com/questions/7928839/adding-and-removing-nodes-from-a-jtree
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestTree extends JTree implements TreeSelectionListener{
    private DefaultTreeModel model = null;
    private DefaultMutableTreeNode root = null;
    private Handler currentHandle = null;
    public TestTree(){
        
        super(new DefaultMutableTreeNode("Tests"));
        getSelectionModel().setSelectionMode
             (TreeSelectionModel.SINGLE_TREE_SELECTION);
        addTreeSelectionListener(this);
        //setEditable(true);
        model = (DefaultTreeModel) this.getModel();
        root = (DefaultMutableTreeNode) model.getRoot();
    }
    private class TestMethod{
        private String methodName;
        private Test t;
        private Method m;
        TestMethod(Test t, Method m){
            methodName = m.getName();
            this.t = t;
            this.m = m;
        }
        public void fire(){
            try{
                t.test(m); // Please don't have inputs
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public String toString(){return methodName;}
    }
    private class Handler extends Thread{
        private Test t;
        private TestMethod m;
        Handler(Test t){this.t = t;}
        Handler(TestMethod m){this.m = m;}
        Handler(Object o){
            if(o instanceof Test){
                this.t = (Test)o;
            } else if (o instanceof TestMethod){
                this.m = (TestMethod)o;
            }
        }
        @Override
        public void run(){
            if(t != null){
                t.testAll();
            }
            if(m != null){
                m.fire();
            }
        }
    }
    public void add(Test o){
        DefaultMutableTreeNode branch = new DefaultMutableTreeNode(o);
        for(Method i : o.getMethods()){
            branch.add(new DefaultMutableTreeNode(new TestMethod(o, i)));
        }
        root.add(branch);
        model.reload(root);
    }
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
            this.getLastSelectedPathComponent();
        if(node == null){
            return;
        }
        currentHandle = new Handler(node.getUserObject());
        //h.start();
        System.out.println(e);
    }
    public void fire(){
        if(currentHandle == null){
            System.out.println("No test mechanism is currently selected.");
            return;}
        currentHandle.start();
    }
}
