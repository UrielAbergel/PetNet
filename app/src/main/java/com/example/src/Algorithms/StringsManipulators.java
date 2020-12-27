package com.example.src.Algorithms;

public class StringsManipulators {


    /**
     * @param string
     * @return same string with first latter in upper case
     */
    public static String SetFirstCharToUpperCase(String string){
        StringBuilder builder = new StringBuilder();
        String c = "" + string.charAt(0);
        builder.append(c.toUpperCase());
        builder.append(string.substring(1));
        System.out.println(builder.toString());
        return builder.toString();
    }
}
