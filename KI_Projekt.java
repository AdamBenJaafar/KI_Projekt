package Übungen;

import javax.swing.*;
import java.util.Scanner;

public class KI_Projekt {

    public static Fenetre F;

    public static void main(String [] args) {

        Scanner S = new Scanner(System.in);

        String test1= JOptionPane.showInputDialog("Rows 1 - 20");
        int rows = Integer.parseInt(test1);

        String test2= JOptionPane.showInputDialog("Columns 1 - 20");
        int columns = Integer.parseInt(test2);

        F = new Fenetre(rows,columns);

    }
}




