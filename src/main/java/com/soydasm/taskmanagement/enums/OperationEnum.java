package com.soydasm.taskmanagement.enums;

public enum OperationEnum
{
    GET("GET", 0,"This gives you read grant!"),
    POST("POST", 1, "This gives you insert grant!"),
    PUT("PUT", 2, "This gives you update grant!"),
    DELETE("DELETE", 3,"This gives you delete grant!");

    private final String name;
    private final int code;
    private final String description;


    private OperationEnum(String name, int code, String description)
    {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
