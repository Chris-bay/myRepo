package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Triplet<Integer>> triplets = new ArrayList<Triplet<Integer>>();

        int all = 216;
        int count = 0;

        for(Integer x = 1; x<7; x++){
            for(Integer y = 1; y<7; y++){
                for(Integer z = 1; z<7; z++){
                    Triplet<Integer> tmp = new Triplet<Integer>();
                    tmp.setTriplet(x,y,z);
                    triplets.add(tmp);
                    //tmp.println();
                }
            }
        }

        for (Triplet<Integer> t : triplets) {
            t.print();
            System.out.println(" :" + getAmountOf(t,triplets));
        }

	// write your code here
    }

    public static int getAmountOf(Triplet<Integer> t, ArrayList<Triplet<Integer>> list){
        int a = t.getA();
        int b = t.getB();
        int c = t.getC();

        int count = 0;

        for(Triplet<Integer> tr: list){
            /*
            tr.print();
            System.out.print(" vs ");
            t.println();
            System.out.println(tr.count(a) + " vs " + t.count(a));
            System.out.println(tr.count(b) + " vs " + t.count(b));
            System.out.println(tr.count(c) + " vs " + t.count(c));*/
            if (       /*((tr.getA() == a) && (tr.getB() == b) && (tr.getC() == c))
                    || ((tr.getA() == a) && (tr.getB() == c) && (tr.getC() == b))
                    || ((tr.getA() == b) && (tr.getB() == a) && (tr.getC() == c))
                    || ((tr.getA() == b) && (tr.getB() == c) && (tr.getC() == a))
                    || ((tr.getA() == c) && (tr.getB() == b) && (tr.getC() == a))
                    || ((tr.getA() == c) && (tr.getB() == a) && (tr.getC() == b))*/

                       tr.count(a) == t.count(a)
                    && tr.count(b) == t.count(b)
                    && tr.count(c) == t.count(c)){
                //System.out.println("treffer");
                count++;
            }
        }
        return count;
    }
}
