package com.soydasm.taskmanagement.enums;

public enum PriorityEnum
{
    CRITICAL("CRITICAL", 0, "The field caught fire, it should be solved as soon as possible! :)"),
    MAJOR("MAJOR", 1,"This is going to be trouble, solve it!"),
    MINOR("MINOR", 2, "It doesn't hinder things too much, you have time to solve it.");

    private final String name;
    private final int code;
    private final String description;


    private PriorityEnum(String name, int code, String description)
    {
        this.name = name;
        this.code = code;
        this.description = description;
    }
}
