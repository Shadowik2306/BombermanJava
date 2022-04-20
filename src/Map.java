import java.awt.*;
import java.util.Arrays;

public class Map {
    GameObject[][] matrix;
    int[][] coordsOfbombs = new int[5][2];
    int countOfBombs = 0;
    int count_of_enemy = 0;
    Graphics canvas;

    public void getGraphics(Graphics g) {
        canvas = g;
    }

    Map(int[][] matrix) {
        this.matrix = new GameObject[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                switch (matrix[i][j]) {
                    case 0:
                        this.matrix[i][j] = new GameObject();
                        break;
                    case 1:
                        this.matrix[i][j] = new Hero();
                        break;
                    case 2:
                        this.matrix[i][j] = new Enemy();
                        break;
                    case 3:
                        this.matrix[i][j] = new BreakableWall();
                        break;
                    case 4:
                        this.matrix[i][j] = new UnbreakableWall();
                        break;
                    default:
                        this.matrix[i][j] = new GameObject();
                        break;
                }
            }
        }
    }

    public void drawMap() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matrix[i][j].getGraphics(canvas);
                matrix[i][j].drawObj(i * 100, j * 100);
            }
        }
    }

    int[][] path = null;
    public void timeLeft() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                switch (matrix[i][j].whatIs()) {
                    case B:
                        if (!((Bombs) matrix[i][j]).isExisting()) {
                            countOfBombs--;
                            for (int k = -1; k <= 1; k++) {
                                for (int l = -1; l <= 1; l++) {
                                    matrix[i][j] = new Explosion();
                                    if (Math.abs(k) != Math.abs(l) && isOnMatrix(i + k) && isOnMatrix(j + l)) {
                                        switch (matrix[i + k][j + l].whatIs()) {
                                            case Hero:
                                                Hero.died();
                                                break;
                                            case Enemy:
                                                Enemy.die();
                                                break;
                                            default:
                                                break;
                                        }
                                        if (matrix[i + k][j + l].whatIs() != Object.UW) {
                                            matrix[i + k][j + l] = new Explosion();
                                            if (k > 0 || l > 0) {
                                                ((Explosion)matrix[i + k][j + l]).itWasLower();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case EX:
                        if (!((Explosion) matrix[i][j]).isExisting()) {
                            matrix[i][j] = new GameObject();
                        }
                        break;
                    case Enemy:
/*                        if (((Enemy) matrix[i][j]).isActive()) {
                            int k = 20, l = 20;
                            for (int m = 0; m < 3; m++) {
                                switch ((int) (Math.random() * 4)) {
                                    case 0:
                                        k = 1;
                                        l = 0;
                                        break;
                                    case 1:
                                        k = -1;
                                        l = 0;
                                        break;
                                    case 2:
                                        k = 0;
                                        l = 1;
                                        break;
                                    case 3:
                                        k = 0;
                                        l = -1;
                                        break;
                                    default:
                                        break;
                                }
                                if (Math.abs(k) != Math.abs(l) && isOnMatrix(i + k) && isOnMatrix(j + l)) {
                                    if (matrix[i + k][j + l].whatIs() == Object.Nothing) {
                                        GameObject obj = matrix[i + k][j + l];
                                        if (((Enemy) matrix[i][j]).isUpToBomb()) {
                                            obj = new Bombs();
                                            ((Enemy) matrix[i][j]).setUpToBomb(false);
                                        }
                                        matrix[i + k][j + l] = matrix[i][j];
                                        matrix[i][j] = obj;
                                        ((Enemy) matrix[i + k][j + l]).setActive(false);
                                        if (countOfBombs < 5) {
                                            if (((Enemy) matrix[i + k][j + l]).isExisting()) {
                                                coordsOfbombs[countOfBombs++] = new int[]{i + k, j + l};
                                                ((Enemy) matrix[i + k][j + l]).setUpToBomb(true);
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }*/
                        path = ((Enemy) matrix[i][j]).getPath();
                        if (((Enemy) matrix[i][j]).isActive()) {
                            if (((Enemy) matrix[i][j]).isExisting()){
                                ((Enemy) matrix[i][j]).setUpToBomb(true);
                                ((Enemy) matrix[i][j]).setBomb(i, j);
                                ((Enemy) matrix[i][j]).setPath(WaveAlgolBombs(new int[]{i, j}, new int[]{i, j}, null));
                            }
                            else if (((Enemy) matrix[i][j]).isPlaceBomb()) {
                                int[] pos = ((Enemy) matrix[i][j]).getPosB();
                                int x = pos[0], y = pos[1];

                                for (int[] item : path
                                ) {
                                    System.out.println(Arrays.toString(item));
                                }
                                int mi = -1;
                                int save_i = 0, save_j = 0;
                                for (int k = -1; k <= 1; k++) {
                                    for (int l = -1; l <= 1; l++) {
                                        if ((k == 0 || l == 0) && isOnMatrix(i + k) && isOnMatrix(j + l)) {
                                            if (path[i + k][j + l] > mi && path[i + k][j + l] != 99999) {
                                                save_i = k + i;
                                                save_j = l + j;
                                                mi = path[i + k][j + l];
                                            }
                                        }
                                    }
                                }
                                if (matrix[save_i][save_j].whatIs() == Object.Nothing) {
                                    GameObject sv;
                                    if (((Enemy)matrix[i][j]).isUpToBomb()) {
                                        sv = new Bombs();
                                        ((Enemy)matrix[i][j]).setUpToBomb(false);
                                    }
                                    else {
                                        sv = matrix[save_i][save_j];
                                    }
                                    matrix[save_i][save_j] = matrix[i][j];
                                    matrix[i][j] = sv;
                                    ((Enemy) matrix[save_i][save_j]).setActive(false);
                                }
                            }
                            else {
                                int x = -1, y = -1;
                                for (int k = 0; k < 10; k++) {
                                    if (x != -1) break;
                                    for (int l = 0; l < 10; l++) {
                                        if (matrix[k][l].whatIs() == Object.Hero) {
                                            x = k;
                                            y = l;
                                            break;
                                        }
                                    }
                                }
                                if (x == -1) break;

                                ((Enemy) matrix[i][j]).setPath(WaveAlgolForHero(new int[]{i, j}, new int[]{x, y}, null));
                                path = ((Enemy) matrix[i][j]).getPath();
                                for (int[] item : path
                                ) {
                                    System.out.println(Arrays.toString(item));
                                }
                                System.out.println();
                                int mi = 999;
                                int save_i = 0, save_j = 0;
                                for (int k = -1; k <= 1; k++) {
                                    for (int l = -1; l <= 1; l++) {
                                        if ((k == 0 || l == 0) && isOnMatrix(i + k) && isOnMatrix(j + l)) {
                                            if (path[i + k][j + l] < mi) {
                                                save_i = k + i;
                                                save_j = l + j;
                                                mi = path[i + k][j + l];
                                            }
                                        }
                                    }
                                }
                                if (matrix[save_i][save_j].whatIs() == Object.Nothing) {
                                    GameObject sv;
                                    if (((Enemy)matrix[i][j]).isUpToBomb()) {
                                        sv = new Bombs();
                                        ((Enemy)matrix[i][j]).setUpToBomb(false);
                                    }
                                    else {
                                        sv = matrix[save_i][save_j];
                                    }
                                    matrix[save_i][save_j] = matrix[i][j];
                                    matrix[i][j] = sv;
                                    ((Enemy) matrix[save_i][save_j]).setActive(false);
                                }
                            }
                        }
                        break;
                    case Nothing:
                        break;
                    case Hero:
                        ((Hero) matrix[i][j]).isExisting();
                    case UW:
                        break;
                    case BW:
                        break;
                    default:
                        break;
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (matrix[i][j].whatIs() == Object.Enemy) {
                    ((Enemy)matrix[i][j]).setActive(true);
                }
            }
        }
    }

    private boolean isOnMatrix(int n) {
        return (n >= 0) && (n <= 9);
    }


    public void moveHero(Directions direction) {
        int pos_x = 20, pos_y = 20;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (matrix[i][j].whatIs() == Object.Hero) {
                    pos_x = i;
                    pos_y = j;
                }
            }
        }

        int baseX = pos_x, baseY = pos_y;

        switch (direction) {
            case Up:
                pos_y--;
                break;
            case Down:
                pos_y++;
                break;
            case Left:
                pos_x--;
                break;
            case Right:
                pos_x++;
                break;
        }

        if (isOnMatrix(pos_x) && isOnMatrix(pos_y)) {
            if (matrix[pos_x][pos_y].whatIs() == Object.Nothing) {
                GameObject object = matrix[pos_x][pos_y];
                matrix[pos_x][pos_y] = matrix[baseX][baseY];

                matrix[baseX][baseY] = object;
                if (((Hero) matrix[pos_x][pos_y]).isUpToBomb()) {
                    matrix[baseX][baseY] = new Bombs();
                    ((Hero) matrix[pos_x][pos_y]).setUpToBomb(false);
                }
            }

        }
    }

    public void heroPlaceBomb() {
        int pos_x = 20, pos_y = 20;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (matrix[i][j].whatIs() == Object.Hero) {
                    pos_x = i;
                    pos_y = j;
                }
            }
        }

        if (isOnMatrix(pos_x) && isOnMatrix(pos_y)) {
            if (((Hero) matrix[pos_x][pos_y]).isTimeToPlaceBomb()) {
                    ((Hero) matrix[pos_x][pos_y]).setUpToBomb(true);
            }
        }
    }

    private int[][] WaveAlgolForHero(int[] place_st, int[] place_end, int[][] map) {
        if (map == null) {
            map = new int[10][10];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    map[i][j] = 99999;
                }
            }
            map[place_end[0]][place_end[1]] = 0;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isOnMatrix(place_end[0] + i) && isOnMatrix(place_end[1] + j) && (i == 0 || j == 0)) {
                    if (matrix[place_end[0] + i][place_end[1] + j].whatIs() != Object.UW) {
                        if (map[place_end[0] + i][place_end[1] + j] > map[place_end[0]][place_end[1]] + 1) {
                            map[place_end[0] + i][place_end[1] + j] = map[place_end[0]][place_end[1]] + 1;
                            WaveAlgolForHero(place_st, new int[]{place_end[0] + i, place_end[1] + j}, map);
                        }
                    }
                }
            }
        }
        return map;
    }


    private int[][] WaveAlgolBombs(int[] place_st, int[] place_end, int[][] map) {
        if (map == null) {
            map = new int[10][10];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    map[i][j] = 99999;
                }
            }
            map[place_end[0]][place_end[1]] = 0;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isOnMatrix(place_end[0] + i) && isOnMatrix(place_end[1] + j) && (i == 0 || j == 0)) {
                    if (matrix[place_end[0] + i][place_end[1] + j].whatIs() == Object.Nothing) {
                        if (map[place_end[0] + i][place_end[1] + j] > map[place_end[0]][place_end[1]] + 1) {
                            map[place_end[0] + i][place_end[1] + j] = map[place_end[0]][place_end[1]] + 1;
                            WaveAlgolBombs(place_st, new int[]{place_end[0] + i, place_end[1] + j}, map);
                        }
                    }
                }
            }
        }
        return map;
    }
}
