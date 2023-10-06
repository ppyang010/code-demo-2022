package com.code;


public class Main {
    record Point(int x, int y) {
    }

    record Rect(int x, int y, int w, int h) {
    }

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        var p = new Point(1, 2);
        var r = new Rect(1, 2, 3, 4);
        var s = fmt(p);
        println(s);
        s = fmt(r);
        println(s);
        s = fmt(new Object());
        println(s);
    }

    public static String fmt(Object o) {
        return switch (o) {
            case Point(var x, var y) -> STR. "Point:x=\{ x },y=\{ y }" ;
            case Rect(var x, var y, var w, var h) -> STR. "Rect:x=\{ x },y=\{ y },w=\{ w },h=\{ h }" ;
            default -> STR. "object:\{ o }" ;
        };
    }

    public static void println(Object o) {
        System.out.println(o);
    }


}