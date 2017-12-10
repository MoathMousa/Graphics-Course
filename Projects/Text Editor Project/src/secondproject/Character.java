package secondproject;

public class Character {

    int letter[][] = new int[16][16];// 16 bit character

    public Character(String x) {// take the dicimal for the charachter
        preperArr(x);
    }

    Character(int i, int i0, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void preperArr(String w) {// take the decimal and convert it to binarry and fill the array 
        String[] x = w.split(",");
        int[] z;
        for (int i = 0; i < x.length; i++) {
            z = BinConv(Integer.parseInt(x[i].trim()));
            for (int q = 0; q < z.length; q++) {
                letter[i][q] = z[q];
            }

        }
    }

    private int[] BinConv(int no) {// take decemal number and convert it to array of bin number

        int[] t = new int[16];
        for (int i = 0; no > 0; i++) {
            t[i] = no % 2;
            no /= 2;
        }
        return t;
    }

}
