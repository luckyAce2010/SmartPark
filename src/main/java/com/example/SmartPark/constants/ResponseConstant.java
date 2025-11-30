package com.example.SmartPark.constants;

public class ResponseConstant {

    //Codes
    public static final int BAD_REQUEST_CODE = 400;
    public static final int SUCCESS_CODE = 200;

    //Messages
    public static final String INVALID_INPUT = "Invalid Input";
    public static final String TYPE_MISMATCH = "Malformed JSON or type mismatch";
    public static String TYPE_MISMATCH(String field, String type){
        return "Field '" + field + "' should be of type " + type;
    }


}
