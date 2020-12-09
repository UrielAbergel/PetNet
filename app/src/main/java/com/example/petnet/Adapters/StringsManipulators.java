package com.example.petnet.Adapters;

public class StringsManipulators {

    /**
     * @param string
     * @return same string with first latter in upper case
     */
    public static String SetFirstCharToUpperCase(String string){
        String result = "" + string.charAt(0);
        result = result.toUpperCase();
        result = result + string.substring(1);
        return result;
    }
}
