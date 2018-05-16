package Übungen;

import sun.text.resources.cldr.et.FormatData_et;

import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFrame;

public  class Fenetre extends JFrame implements Drawing_Grid, KeyListener {

    public static JButton[][] JBA;

    static int[][] map;

    public ArrayList<JButton> JBL;

    public Thread th = new Thread();
    public RunnableDemo rd;

        private ButtonGroup bgmenu;
        private ButtonGroup actionmenu;
        private ButtonGroup statemenu;

    private static int gx;
    private static int gy;

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.print("TYPED");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("pressed");

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("pressed");
        th.interrupt();
    }

    class RunnableDemo implements Runnable {
        private Thread t;
        private String threadName;
        private int x;
        private int y;

        RunnableDemo( String name, int x, int y) {
            threadName = name;
            this.x=x;
            this.y=y;
            System.out.println("Creating " +  threadName );
        }

        public void run() {
            System.out.println("Running " +  threadName );
            try {
                switch(this.threadName) {
                    case "BFS":
                        AlgorithmsUtil.bfs(this.x, this.y, gx,gy,map,JBA,Fenetre.this);
                        break;
                    case "DFS":
                        AlgorithmsUtil.dfs(this.x, this.y,gx,gy,map,JBA);
                        break;
                }

//                for(int i = 4; i > 0; i--) {
//                    System.out.println("Thread: " + threadName + ", " + i);
//                    // Let the thread sleep for a while.
//                    Thread.sleep(50);
//                }
            } catch (InterruptedException e) {
                System.out.println("Thread " +  threadName + " interrupted.");
            }
            System.out.println("Thread " +  threadName + " exiting.");
        }

        public void start () {
            System.out.println("Starting " +  threadName );
            if (t == null) {
                t = new Thread (this, threadName);
                t.start ();
            }
        }

        public void stop(){

        }


    }

    public JButton getGridButton(int row, int col) {
        int index = row * JBA.length + col;
        return JBL.get(index);
    }

    /// finale game to understand
    public JButton CreateGridButton(final int i, final int j) {
        JButton jb = new JButton();
        //jb.setBackground(map[i][j] == '0' ? Color.WHITE : map[i][j] == (char) 149 ? Color.BLACK : Color.YELLOW);
        //jb.setBackground(map[i][j] == '0' ? Color.WHITE : Color.LIGHT_GRAY);
        jb.setBackground(Color.LIGHT_GRAY);
        System.out.println("Creatign Jbutton with coord [" + i + "," + j + "]");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hello , I'am the button [" + i + "," + j + "]");
//                try {
//                    bfs(i, j);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }

                for (Enumeration<AbstractButton> buttons = Fenetre.this.actionmenu.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                        if ( button.getLabel().equals("End")) {
                            gx = i;
                            gy = j;
                            return;
                        } else if ( button.getLabel().equals("DRAW")) {

                            JBA[i][j].setBackground( map[i][j] == 0 ? Color.black : Color.white );
                            map[i][j] = ( map[i][j] == 1 ? 0 : 1 );
                            return;
                        }
                    }
                }

                for (Enumeration<AbstractButton> buttons = Fenetre.this.bgmenu.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {


                        RunnableDemo R1 = new RunnableDemo(button.getLabel(),i,j);
                        Thread thready = new Thread(R1);
                        th = thready;
                        thready.start();
                     //   R1.start();
                    }
                }



            }
        });
        JBA[i][j] = jb;
        return jb;
    }

    public Fenetre(int r, int c) {
        this.setTitle("Bouton");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        addKeyListener(this);
        setFocusable(true);

        map = new int [r][c];

        ButtonGroup group = new ButtonGroup();
        this.bgmenu = group;


        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Algorihtms");
        menu.setMnemonic(KeyEvent.VK_O);

        JRadioButtonMenuItem menuItem;

//        JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem("North");
//        group.add(menuItem);
//        menu.add(menuItem);

//        menuItem = new JRadioButtonMenuItem("East");
//        menuItem.setName("BFS");
//        group.add(menuItem);
//        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("BFS");
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("DFS");
        group.add(menuItem);
        menu.add(menuItem);

        menuItem = new JRadioButtonMenuItem("Center");
        group.add(menuItem);
        menu.add(menuItem);

        JMenu actions = new  JMenu("Button Action");
        JMenu state = new  JMenu("GRID ACTION");
        JMenuItem refresh = new JMenuItem("REFRESH MAP");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMap();
            }
        });
        state.add(refresh);

        JMenuItem draw = new JMenuItem("DRAW");
        draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                draw();
            }
        });
        state.add(draw);

        JMenuItem stop = new JMenuItem("STOP");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                th.interrupt();
                System.out.println("STOPPPING THREAD");
            }
        });
        state.add(stop);


        ButtonGroup stateAction = new ButtonGroup();
        this.statemenu = stateAction;

        ButtonGroup groupAction = new ButtonGroup();
        this.actionmenu = groupAction;

        menuItem = new JRadioButtonMenuItem("DRAW");
        groupAction.add(menuItem);
        actions.add(menuItem);

        menuItem = new JRadioButtonMenuItem("Run");
        groupAction.add(menuItem);
        actions.add(menuItem);

        menuItem = new JRadioButtonMenuItem("End");
        groupAction.add(menuItem);
        actions.add(menuItem);


        bar.add(actions);
        bar.add(state);
        bar.add(menu);

        this.setJMenuBar(bar);

        this.setLayout(new GridLayout(map.length, map[0].length));


//        JMenuBar menubar = new JMenuBar();
//        JMenu menu = new JMenu("size");
//        JMenuItem size = new JMenuItem("size");
//        menu.add(size);
//        menu.add(new AbstractAction("Exit"){
//
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                try {
//                    Fenetre.bfs(7,0);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        });
//        menubar.add(menu);
//        this.setJMenuBar(menubar);

        JBA = new JButton[map.length][map[0].length];

        JBL = new ArrayList<>();

        // thought about  extending the Jbutto n andcreating anewitem
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {

                this.getContentPane().add(CreateGridButton(i, j));
            }
        }

        this.setVisible(true);
    }





    public void clearMap(){
        for ( JButton []JB : JBA ){
            for( JButton KB : JB ) {
                KB.setBackground(Color.lightGray);
                KB.setText(null);
            }
        }
    }

    public static void draw() {
        System.out.println("loolololo");
        for ( int i = 0 ; i < JBA.length ; i++ ) {
            for ( int j =  0 ; j < JBA.length; j++ ) {
                JBA[i][j].setBackground(map[i][j] == 0 ? Color.WHITE : Color.black );
            }
        }
    }

}
