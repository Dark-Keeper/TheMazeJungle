package com.darkkeeper.themaze.Basics;

import com.darkkeeper.themaze.Screens.GameScreen;
import com.darkkeeper.themaze.TheMaze;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by andreipiatosin on 5/20/15.
 */
public class MazeGenerator {
    private final int x;
    private final int y;
    private final int[][] maze;

    public MazeGenerator (int x, int y, int method) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];
        generateMaze(0, 0, method);
    }

    private void generateMaze(int cx, int cy, int method) {
        switch (method){
            case 0:
                recursiveBacktracking(cx, cy);
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        private DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    };

    public void display() {
        for (int i = 0; i < y; i++ ){
            for (int j = 0; j < x; j++ ){
                System.out.print(maze[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------------------------");
        for (int i = 0; i < y; i++) {
            // draw the north edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the west edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < x; j++) {
            System.out.print("+---");
        }
        System.out.println("+");

        TheMaze.game.setScreen( new GameScreen(maze) );
    }

    private void recursiveBacktracking ( int cx, int cy ){
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                recursiveBacktracking(nx, ny);
            }
        }
    }

    public int [][] getMaze () {
        return maze;
    }


}