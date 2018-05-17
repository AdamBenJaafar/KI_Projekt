package Übungen;

import sun.security.tools.PathList;

import javax.swing.*;
import java.awt.*;
import java.beans.Visibility;
import java.util.*;
import java.util.concurrent.TimeUnit;

public final class AlgorithmsUtil {

    public static void bfs(int a, int b,int gx,int gy, int [][] map, JButton[][] JBA, demonstrationGrid DG) throws InterruptedException {

        DG.clearMap();

        int[][] vmap = new int[map.length][map[0].length];
        int[][] losvmap = new int[map.length][map[0].length];

        int[] xv = {-1, 0, 0, 1};
        int[] yv = {0, -1, 1, 0};

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                vmap[i][j] = map[i][j] == 0 ? 0 : 1;
                losvmap[i][j] = map[i][j] == 0 ? 0 : 0;

            }
        }

        vmap[a][b] = 1;
        losvmap[a][b] = 1;
        Queue<Integer> Q = new LinkedList<>();
        Q.add(a);
        Q.add(b);
        Q.add(0);
        int cx = -2, cy = -2;
        int number=0;
        while (!Q.isEmpty()) {
            TimeUnit.MILLISECONDS.sleep(25);
            if (cx != -2 && cy != -2 ) JBA[cx][cy].setBackground(Color.green);
            cx = Q.remove();
            cy = Q.remove();
            number = Q.remove();
            JBA[cx][cy].setBackground(Color.BLUE);
            DG.updateLineOfSight(cx,cy,losvmap);
            //LOS(cx,cy,losvmap,vmap, JBA);
            for (int i = 0; i < 4; i++) {
                int nx = cx + xv[i];
                int ny = cy + yv[i];
                if (nx < 0 || nx >= map.length || ny < 0 || ny >= map[0].length || vmap[nx][ny] != 0) {
                    continue;
                } else {
                    Q.add(nx);
                    Q.add(ny);
                    Q.add(number+1);
                    vmap[nx][ny] = 2;
                    losvmap[nx][ny] = 2;
                    JBA[nx][ny].setText((number+1)+"");
                    JBA[nx][ny].setBackground(Color.ORANGE);
                    if ( nx == gx && ny == gy ) {
                        JBA[nx][ny].setBackground(Color.RED);
                        return;
                    }
                }
            }
        }
    }

    public static void dfs(int a, int b, int gx,int gy, int [][] map, JButton[][] JBA) throws InterruptedException {

        int [][] vmap = new int [map.length][map[0].length];

        int [] xv = { -1, 0, 0, 1};
        int [] yv = { 0, -1, 1, 0};

        for( int i = 0 ; i < map.length ; i++ ) {
            for ( int j = 0 ; j < map[i].length ; j++ ) {
                vmap[i][j] = map[i][j] == 0 ? 0 : 1 ;
            }
        }

        vmap[a][b] = 1;
        Stack<Integer> Q = new Stack<>();
        Q.add(b);
        Q.add(a);
        int cx=-2,cy=-2;
        int number =0;
        while(!Q.isEmpty()){
            System.out.println(Q);
            TimeUnit.MILLISECONDS.sleep(200);
            if((cx != -2 && cy != -2)) JBA[cx][cy].setBackground(Color.GREEN);
            cx=Q.pop();
            cy=Q.pop();
            //LOS(cx,cy, losvmap);
            if ((cx != gx && cy != gy  ) ) JBA[cx][cy].setBackground(Color.BLUE);
            for (int i = 0 ; i < 4 ; i++ ) {
                int nx = cx + xv[i];
                int ny = cy + yv[i];
                if ( nx < 0 || nx >= map.length || ny < 0 || ny >= map[0].length || vmap[nx][ny] == 1 ) {
                    continue;
                } else {
                    Q.add(ny);
                    Q.add(nx);
                    vmap[nx][ny]=1;
                    JBA[nx][ny].setBackground(Color.ORANGE);
                    if ( nx == gx && ny == gy ) {
                        JBA[nx][ny].setBackground(Color.RED);
                        return;
                    }
                    break;
                }
            }
//            JBA[cx][cy].setBackground(Color.YELLOW);
        }
    }

    public static void aStar(int a, int b, int gx, int gy, int[][] map, JButton [][] JBA, demonstrationGrid DG) throws  InterruptedException {
        // add mulitple aStar values
        System.out.println("Running aStar");
        ArrayList<ArrayList<Node>> path = generateHValue(map,a,b,gx,gy,map.length,10,10,false,1);
        DG.showMap();
        for ( int i = 0 ; i  < path.get(0).size() ; i++) {
            TimeUnit.MILLISECONDS.sleep(1);
            //Node node = path.get(path.size()-1-i);
            Node node = path.get(0).get(i);
            DG.color(node.x,node.y,Color.green);
        }
        for ( int i = 0 ; i  < path.get(1).size() ; i++) {
            TimeUnit.MILLISECONDS.sleep(5);
            //Node node = path.get(path.size()-1-i);
            Node node = path.get(1).get(i);
            DG.color(node.x,node.y,Color.blue);
        }
    }

    /**
     * @param matrix         The boolean matrix that the framework generates
     * @param Ai             Starting point's x value
     * @param Aj             Starting point's y value
     * @param Bi             Ending point's x value
     * @param Bj             Ending point's y value
     * @param n              Length of one side of the matrix
     * @param v              Cost between 2 cells located horizontally or vertically next to each other
     * @param d              Cost between 2 cells located Diagonally next to each other
     * @param additionalPath Boolean to decide whether to calculate the cost of through the diagonal path
     * @param h              int value which decides the correct method to choose to calculate the Heuristic value
     */
    public static ArrayList<ArrayList<Node>> generateHValue(int matrix[][], int Ai, int Aj, int Bi, int Bj, int n, int v, int d, boolean additionalPath, int h) {
        System.out.println("Running generateHValue");
        Node [][] cell = new Node[matrix.length][matrix.length];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix.length; x++) {
                //Creating a new Node object for each and every Cell of the Grid (Matrix)
                cell[y][x] = new Node(y, x);
                //Checks whether a cell is Blocked or Not by checking the boolean value
                if (matrix[y][x] == 0) {
                    if (h == 1) {
                        //Assigning the Chebyshev Heuristic value
                        if (Math.abs(y - Bi) > Math.abs(x - Bj)) {
                            cell[y][x].hValue = Math.abs(y - Bi) * 10;
                        } else {
                            cell[y][x].hValue = Math.abs(x - Bj) * 10;
                        }
                    } else if (h == 2) {
                        //Assigning the Euclidean Heuristic value
                        cell[y][x].hValue = Math.sqrt(Math.pow(y - Bi, 2) + Math.pow(x - Bj, 2)) * 10 ;
                    } else if (h == 3) {
                        //Assigning the Manhattan Heuristic value by calculating the absolute length (x+y) from the ending point to the starting point
                        cell[y][x].hValue = Math.abs(y - Bi) + Math.abs(x - Bj) * 10;
                    }
                } else {
                    //If the boolean value is false, then assigning -1 instead of the absolute length
                    cell[y][x].hValue = -1;
                }
            }
        }
        return generatePath(cell, Ai, Aj, Bi, Bj, n, v, d, additionalPath,cell);
    }

    /**
     * @param hValue         Node type 2D Array (Matrix)
     * @param Ai             Starting point's y value
     * @param Aj             Starting point's x value
     * @param Bi             Ending point's y value
     * @param Bj             Ending point's x value
     * @param n              Length of one side of the matrix
     * @param v              Cost between 2 cells located horizontally or vertically next to each other
     * @param d              Cost between 2 cells located Diagonally next to each other
     * @param additionalPath Boolean to decide whether to calculate the cost of through the diagonal path
     */
    public static ArrayList<ArrayList<Node>> generatePath(Node hValue[][], int Ai, int Aj, int Bi, int Bj, int n, int v, int d, boolean additionalPath, Node [][] cell) {

        System.out.println("Running generatePath");

        ArrayList<Node> pathList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();
        ArrayList<Node> visitedList = new ArrayList<>();


        //Creation of a PriorityQueue and the declaration of the Comparator
        PriorityQueue<Node> openList = new PriorityQueue<>(11, new Comparator() {
            @Override
            //Compares 2 Node objects stored in the PriorityQueue and Reorders the Queue according to the object which has the lowest fValue
            public int compare(Object cell1, Object cell2) {
                return ((Node) cell1).fValue < ((Node) cell2).fValue ? -1 :
                        ((Node) cell1).fValue > ((Node) cell2).fValue ? 1 : 0;
            }
        });

        //Adds the Starting cell inside the openList
        openList.add(cell[Ai][Aj]);
        //visitedList.add(cell[Ai][Aj]);

        //Executes the rest if there are objects left inside the PriorityQueue
        while (true) {

            //Gets and removes the objects that's stored on the top of the openList and saves it inside node
            Node node = openList.poll();

            //Checks if whether node is empty and f it is then breaks the while loop
            if (node == null) {
                break;
            }

            //Checks if whether the node returned is having the same node object values of the ending point
            //If it des then stores that inside the closedList and breaks the while loop
            if (node == cell[Bi][Bj]) {
                closedList.add(node);
                break;
            }

            closedList.add(node);
            visitedList.add(node);

            //Left Cell
            try {
                if (cell[node.x][node.y - 1].hValue != -1
                        && !openList.contains(cell[node.x][node.y - 1])
                        && !closedList.contains(cell[node.x][node.y - 1])) {
                    double tCost = node.fValue + v;
                    cell[node.x][node.y - 1].gValue = v;
                    double cost = cell[node.x][node.y - 1].hValue + tCost;
                    if (cell[node.x][node.y - 1].fValue > cost || !openList.contains(cell[node.x][node.y - 1]))
                        cell[node.x][node.y - 1].fValue = cost;

                    openList.add(cell[node.x][node.y - 1]);
                    //visitedList.add(cell[node.x][node.y - 1]);
                    cell[node.x][node.y - 1].parent = node;
                }
            } catch (IndexOutOfBoundsException e) {
            }

            //Right Cell
            try {
                if (cell[node.x][node.y + 1].hValue != -1
                        && !openList.contains(cell[node.x][node.y + 1])
                        && !closedList.contains(cell[node.x][node.y + 1])) {
                    double tCost = node.fValue + v;
                    cell[node.x][node.y + 1].gValue = v;
                    double cost = cell[node.x][node.y + 1].hValue + tCost;
                    if (cell[node.x][node.y + 1].fValue > cost || !openList.contains(cell[node.x][node.y + 1]))
                        cell[node.x][node.y + 1].fValue = cost;

                    openList.add(cell[node.x][node.y + 1]);
                    //visitedList.add(cell[node.x][node.y + 1]);
                    cell[node.x][node.y + 1].parent = node;
                }
            } catch (IndexOutOfBoundsException e) {
            }

            //Bottom Cell
            try {
                if (cell[node.x + 1][node.y].hValue != -1
                        && !openList.contains(cell[node.x + 1][node.y])
                        && !closedList.contains(cell[node.x + 1][node.y])) {
                    double tCost = node.fValue + v;
                    cell[node.x + 1][node.y].gValue = v;
                    double cost = cell[node.x + 1][node.y].hValue + tCost;
                    if (cell[node.x + 1][node.y].fValue > cost || !openList.contains(cell[node.x + 1][node.y]))
                        cell[node.x + 1][node.y].fValue = cost;

                    openList.add(cell[node.x + 1][node.y]);
                    //visitedList.add(cell[node.x + 1][node.y]);
                    cell[node.x + 1][node.y].parent = node;
                }
            } catch (IndexOutOfBoundsException e) {
            }

            //Top Cell
            try {
                if (cell[node.x - 1][node.y].hValue != -1
                        && !openList.contains(cell[node.x - 1][node.y])
                        && !closedList.contains(cell[node.x - 1][node.y])) {
                    double tCost = node.fValue + v;
                    cell[node.x - 1][node.y].gValue = v;
                    double cost = cell[node.x - 1][node.y].hValue + tCost;
                    if (cell[node.x - 1][node.y].fValue > cost || !openList.contains(cell[node.x - 1][node.y]))
                        cell[node.x - 1][node.y].fValue = cost;

                    openList.add(cell[node.x - 1][node.y]);
                        //visitedList.add(cell[node.x - 1][node.y]);
                    cell[node.x - 1][node.y].parent = node;
                }
            } catch (IndexOutOfBoundsException e) {
            }

            if (additionalPath) {

                //TopLeft Cell
                try {
                    if (cell[node.x - 1][node.y - 1].hValue != -1
                            && !openList.contains(cell[node.x - 1][node.y - 1])
                            && !closedList.contains(cell[node.x - 1][node.y - 1])) {
                        double tCost = node.fValue + d;
                        cell[node.x - 1][node.y - 1].gValue = d;
                        double cost = cell[node.x - 1][node.y - 1].hValue + tCost;
                        if (cell[node.x - 1][node.y - 1].fValue > cost || !openList.contains(cell[node.x - 1][node.y - 1]))
                            cell[node.x - 1][node.y - 1].fValue = cost;

                        openList.add(cell[node.x - 1][node.y - 1]);
                        //visitedList.add(cell[node.x - 1][node.y - 1]);
                        cell[node.x - 1][node.y - 1].parent = node;
                    }
                } catch (IndexOutOfBoundsException e) {
                }

                //TopRight Cell
                try {
                    if (cell[node.x - 1][node.y + 1].hValue != -1
                            && !openList.contains(cell[node.x - 1][node.y + 1])
                            && !closedList.contains(cell[node.x - 1][node.y + 1])) {
                        double tCost = node.fValue + d;
                        cell[node.x - 1][node.y + 1].gValue = d;
                        double cost = cell[node.x - 1][node.y + 1].hValue + tCost;
                        if (cell[node.x - 1][node.y + 1].fValue > cost || !openList.contains(cell[node.x - 1][node.y + 1]))
                            cell[node.x - 1][node.y + 1].fValue = cost;

                        openList.add(cell[node.x - 1][node.y + 1]);
                        //visitedList.add(cell[node.x - 1][node.y + 1]);
                        cell[node.x - 1][node.y + 1].parent = node;
                    }
                } catch (IndexOutOfBoundsException e) {
                }

                //BottomLeft Cell
                try {
                    if (cell[node.x + 1][node.y - 1].hValue != -1
                            && !openList.contains(cell[node.x + 1][node.y - 1])
                            && !closedList.contains(cell[node.x + 1][node.y - 1])) {
                        double tCost = node.fValue + d;
                        cell[node.x + 1][node.y - 1].gValue = d;
                        double cost = cell[node.x + 1][node.y - 1].hValue + tCost;
                        if (cell[node.x + 1][node.y - 1].fValue > cost || !openList.contains(cell[node.x + 1][node.y - 1]))
                            cell[node.x + 1][node.y - 1].fValue = cost;

                        openList.add(cell[node.x + 1][node.y - 1]);
                        //visitedList.add(cell[node.x + 1][node.y - 1]);
                        cell[node.x + 1][node.y - 1].parent = node;
                    }
                } catch (IndexOutOfBoundsException e) {
                }

                //BottomRight Cell
                try {
                    if (cell[node.x + 1][node.y + 1].hValue != -1
                            && !openList.contains(cell[node.x + 1][node.y + 1])
                            && !closedList.contains(cell[node.x + 1][node.y + 1])) {
                        double tCost = node.fValue + d;
                        cell[node.x + 1][node.y + 1].gValue = d;
                        double cost = cell[node.x + 1][node.y + 1].hValue + tCost;
                        if (cell[node.x + 1][node.y + 1].fValue > cost || !openList.contains(cell[node.x + 1][node.y + 1]))
                            cell[node.x + 1][node.y + 1].fValue = cost;

                        openList.add(cell[node.x + 1][node.y + 1]);
                        //visitedList.add(cell[node.x + 1][node.y + 1]);
                        cell[node.x + 1][node.y + 1].parent = node;
                    }
                } catch (IndexOutOfBoundsException e) {
                }
            }
        }

        /*for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(cell[i][j].fValue + "    ");
            }
            System.out.println();
        }*/

        //Assigns the last Object in the closedList to the endNode variable
        Node endNode = closedList.get(closedList.size() - 1);

        //Checks if whether the endNode variable currently has a parent Node. if it doesn't then stops moving forward.
        //Stores each parent Node to the PathList so it is easier to trace back the final path
        while (endNode.parent != null) {
            Node currentNode = endNode;
            pathList.add(currentNode);
            endNode = endNode.parent;
        }

        pathList.add(cell[Ai][Aj]);
        //Clears the openList
        openList.clear();




        System.out.println();

        ArrayList<ArrayList<Node>> aar = new ArrayList<>();

        aar.add(visitedList);
        aar.add(pathList);

        return aar;

    }

    public static void LOS(int x, int y, int [][] losvmap, int [][] vmap, JButton[][] JBA ) {
        for ( int i = x-3 ; i <= x+3 ; i++ ) {
            for ( int j = y-3 ; j <= y+3 ; j++ ) {
                if (!( i < 0 || i >= losvmap.length || j < 0 || j >= losvmap[0].length ) && losvmap[i][j] == 0) {
                    losvmap[i][j]=1;
                    JBA[i][j].setBackground(vmap[i][j] == 1 ? Color.black : Color.WHITE );
                }
            }
        }
    }

}
