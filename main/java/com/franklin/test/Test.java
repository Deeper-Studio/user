package com.franklin.test;

public class Test {
    public static void main (String[] args) {
        Object object = new Object();
        object = "admin,organization,staff";
        String[] perms = (String[]) object;
        for(String permStr : perms){
            System.out.println(permStr);
        }
    }
}
