package tekgui.test.window;

import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.BorderLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.JComponent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.Arrays;
import java.awt.Insets;

/**
 * Write a description of class Internal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Internal extends JInternalFrame{
    
    private Border border;
    private JComponent northPane;
    private JComponent southPane;
    private JComponent westPane;
    private JComponent eastPane;
    public Internal(){super();}
    public Internal(JFrame frame){
        super(frame.getTitle(), true, true, true, true);
        
        border = getBorder();
        try{
            northPane = ((BasicInternalFrameUI) getUI()).getNorthPane();
            southPane = ((BasicInternalFrameUI) getUI()).getSouthPane();
            westPane = ((BasicInternalFrameUI) getUI()).getWestPane();
            eastPane = ((BasicInternalFrameUI) getUI()).getEastPane();
        } catch(Exception e){/* Class cast failed */}
        
        setJMenuBar(frame.getJMenuBar());
        setSize(frame.getSize());
        setLocation(frame.getLocation());
        setFrameIcon(new ImageIcon(getClass().getResource("/res/iconGrade.png")));
        while(frame.getContentPane().getComponents().length > 0){
            add(frame.getContentPane().getComponents()[0], 
                ((BorderLayout)frame.getContentPane().getLayout()).getConstraints(frame.getContentPane().getComponents()[0]));
        }
        setUndecorated(true); // just removes border, because we are using DeskTopPane, it'll override whatever we do, kinda
        List<WindowListener> windowListeners = Arrays.asList(frame.getWindowListeners());
        for(WindowListener w : windowListeners){
            addInternalFrameListener(new InternalFrameListener(){
                private WindowEvent convertWindow(InternalFrameEvent e){
                    int id = 0;
                    switch(e.getID()){
                        case InternalFrameEvent.INTERNAL_FRAME_ACTIVATED:
                            id = WindowEvent.WINDOW_ACTIVATED;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_CLOSED:
                            id = WindowEvent.WINDOW_CLOSED;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_CLOSING:
                            id = WindowEvent.WINDOW_CLOSING;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_DEACTIVATED:
                            id = WindowEvent.WINDOW_DEACTIVATED;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_DEICONIFIED:
                            id = WindowEvent.WINDOW_DEICONIFIED;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_ICONIFIED:
                            id = WindowEvent.WINDOW_ICONIFIED;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_OPENED:
                            id = WindowEvent.WINDOW_OPENED;
                            break;
                    }
                    return new WindowEvent(frame, id);
                }
                @Override
                public void internalFrameOpened(InternalFrameEvent e){
                    frame.getWindowListeners()[0].windowOpened(convertWindow(e));
                }
                @Override
                public void internalFrameClosed(InternalFrameEvent e){
                    w.windowClosed(convertWindow(e));
                }
                @Override
                public void internalFrameClosing(InternalFrameEvent e){
                    w.windowClosing(convertWindow(e));
                }
                @Override
                public void internalFrameDeiconified(InternalFrameEvent e){
                    w.windowDeiconified(convertWindow(e));
                }
                @Override
                public void internalFrameIconified(InternalFrameEvent e){
                    w.windowIconified(convertWindow(e));
                }
                @Override
                public void internalFrameDeactivated(InternalFrameEvent e){
                    w.windowDeactivated(convertWindow(e));
                }
                @Override
                public void internalFrameActivated(InternalFrameEvent e){
                    w.windowActivated(convertWindow(e));
                }
            });
        }
        setVisible(true);
    }
    public Insets getTrueInsets(){
        BasicInternalFrameUI ui = (BasicInternalFrameUI) getUI();
        Insets north = ui.getNorthPane().getInsets();
        return new Insets(north.top+ui.getNorthPane().getHeight(), 
                          north.left,
                          north.bottom,
                          north.right);
    }
    public void setUndecorated(boolean val){
        setBorder(val ? null : border);
        //((BasicInternalFrameUI) getUI()).setNorthPane(val ? null : northPane);
        //((BasicInternalFrameUI) getUI()).setSouthPane(val ? null : southPane);
        //((BasicInternalFrameUI) getUI()).setWestPane(val ? null : westPane);
        //((BasicInternalFrameUI) getUI()).setEastPane(val ? null : eastPane);
    }
    
    public static JInternalFrame convertFrame(JFrame frame){
        JInternalFrame intern = new JInternalFrame(frame.getTitle(), true, true, true, true);
        intern.setJMenuBar(frame.getJMenuBar());
        intern.setSize(frame.getSize());
        intern.setLocation(frame.getLocation());
        while(frame.getContentPane().getComponents().length > 0){
            intern.add(frame.getContentPane().getComponents()[0], 
                ((BorderLayout)frame.getContentPane().getLayout()).getConstraints(frame.getContentPane().getComponents()[0]));
        }
        List<WindowListener> windowListeners = Arrays.asList(frame.getWindowListeners());
        for(WindowListener w : windowListeners){
            intern.addInternalFrameListener(new InternalFrameListener(){
                private WindowEvent convertWindow(InternalFrameEvent e){
                    int id = 0;
                    switch(e.getID()){
                        case InternalFrameEvent.INTERNAL_FRAME_ACTIVATED:
                            id = WindowEvent.WINDOW_ACTIVATED;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_CLOSED:
                            id = WindowEvent.WINDOW_CLOSED;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_CLOSING:
                            id = WindowEvent.WINDOW_CLOSING;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_DEACTIVATED:
                            id = WindowEvent.WINDOW_DEACTIVATED;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_DEICONIFIED:
                            id = WindowEvent.WINDOW_DEICONIFIED;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_ICONIFIED:
                            id = WindowEvent.WINDOW_ICONIFIED;
                            break;
                        case InternalFrameEvent.INTERNAL_FRAME_OPENED:
                            id = WindowEvent.WINDOW_OPENED;
                            break;
                    }
                    return new WindowEvent(frame, id);
                }
                @Override
                public void internalFrameOpened(InternalFrameEvent e){
                    frame.getWindowListeners()[0].windowOpened(convertWindow(e));
                }
                @Override
                public void internalFrameClosed(InternalFrameEvent e){
                    w.windowClosed(convertWindow(e));
                }
                @Override
                public void internalFrameClosing(InternalFrameEvent e){
                    w.windowClosing(convertWindow(e));
                }
                @Override
                public void internalFrameDeiconified(InternalFrameEvent e){
                    w.windowDeiconified(convertWindow(e));
                }
                @Override
                public void internalFrameIconified(InternalFrameEvent e){
                    w.windowIconified(convertWindow(e));
                }
                @Override
                public void internalFrameDeactivated(InternalFrameEvent e){
                    w.windowDeactivated(convertWindow(e));
                }
                @Override
                public void internalFrameActivated(InternalFrameEvent e){
                    w.windowActivated(convertWindow(e));
                }
            });
        }
        intern.setVisible(true);
        return intern;
    }
    
}
