package com.darkkeeper.themaze.Methods;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by andreipiatosin on 6/4/15.
 */
public class RecursiveBacktracking {
    private int[][] maze;
    private final int x;
    private final int y;

    public RecursiveBacktracking (  int cx, int cy ){
        this.x = cx;
        this.y = cy;
        maze = new int[cx][cy];
        calculate( 0, 0 );
    }

    public void calculate ( int cx, int cy ){
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                calculate(nx, ny);
            }
        }
    }

    public int[][] getMaze (){
        return maze;
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
}
