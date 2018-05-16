package Übungen;

import javax.swing.*;
import java.util.Scanner;

public class KI_Projekt {

    public static Fenetre F;
    public static int [][] map;

    public static void main(String [] args) {

        Scanner S = new Scanner(System.in);

        map  = new int[8][8];


        for ( int i = 0 ; i < map.length ; i++ ) {
            for ( int j = 0 ; j < map[i].length ; j++ ) {
                map[i][j]=0;
            }
        }

        map[3][2]=1;
        map[3][3]=1;
        map[3][4]=1;
        map[3][5]=1;
        map[3][6]=1;
        map[4][1]=1;
        map[5][2]=1;
        map[5][6]=1;


        for ( int i = 0 ; i < map.length ; i++ ) {
            for ( int j = 0 ; j < map[i].length ; j++ ) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }

//        final JOptionPane optionPane = new JOptionPane(
//                "The only way to close this dialog is by\n"
//                        + "pressing one of the following buttons.\n"
//                        + "Do you understand?",
//                JOptionPane.QUESTION_MESSAGE,
//                JOptionPane.YES_NO_OPTION);
//

        String test1= JOptionPane.showInputDialog("Rows");
        int rows = Integer.parseInt(test1);
        String test2= JOptionPane.showInputDialog("Columns");
        int columns = Integer.parseInt(test2);

//        if ( rows == 8 && columns == 8 )
//            F = new Fenetre(map);
//        else
            F = new Fenetre(rows,columns);



//        try {
//            F.dfs(7,0);
//        }catch(Exception e){
//            e.printStackTrace();
//        }

    }
}




