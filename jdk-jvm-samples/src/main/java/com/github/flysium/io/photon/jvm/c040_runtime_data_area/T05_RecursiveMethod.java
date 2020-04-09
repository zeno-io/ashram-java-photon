package com.github.flysium.io.photon.jvm.c040_runtime_data_area;

public class T05_RecursiveMethod {
    public static void main(String[] args) {
        T05_RecursiveMethod h = new T05_RecursiveMethod();
        int i = h.m(3);
    }

    public int m(int n) {
        if(n == 1) return 1;
        return n * m(n-1);
    }
}
