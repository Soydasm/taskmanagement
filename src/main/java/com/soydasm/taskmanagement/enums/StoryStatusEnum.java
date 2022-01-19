package com.soydasm.taskmanagement.enums;

public enum StoryStatusEnum
{
    NEW("NEW", 0,"This indicates the new story!"),
    ESTIMATED("ESTIMATED", 1, "This indicates the story is in estimation process!"),
    COMPLETED("COMPLETED", 2, "This indicates the story has been completed!");

    private final String name;
    private final int code;
    private final String description;


    private StoryStatusEnum(String name, int code, String description)
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
