package com.soydasm.taskmanagement.enums;

public enum BugStatusEnum
{
    NEW("NEW", 0,"This indicates the new bug!"),
    VERIFIED("VERIFIED", 1,"This indicates the bug is in verification process!"),
    RESOLVED("COMPLETED", 2, "This indicates the bug has been resolved!");

    private final String name;
    private final int code;
    private final String description;


    private BugStatusEnum(String name, int code,String description)
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
