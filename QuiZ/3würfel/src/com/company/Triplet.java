package com.company;

public class Triplet<type> {

    type a;
    type b;
    type c;

    //void Triplet(){}

    Triplet<type> Triplet(type a, type b, type c){
        setA(a);
        setB(b);
        setC(c);
        return this;
    }

    Triplet<type> setTriplet(type a, type b, type c){
        setA(a);
        setB(b);
        setC(c);
        return this;
    }

    public type getA() {
        return a;
    }

    public void setA(type a) {
        this.a = a;
    }

    public type getB() {
        return b;
    }

    public void setB(type b) {
        this.b = b;
    }

    public type getC() {
        return c;
    }

    public void setC(type c) {
        this.c = c;
    }

    public void println(){
        System.out.println("("+getA()+","+getB()+","+getC()+")");
    }

    public void print(){
        System.out.print("("+getA()+","+getB()+","+getC()+")");
    }

    public int count(type i){
        int ret = 0;
        if(i == getA()) ret++;
        if(i == getB()) ret++;
        if(i == getC()) ret++;
        return ret;
    }

}
