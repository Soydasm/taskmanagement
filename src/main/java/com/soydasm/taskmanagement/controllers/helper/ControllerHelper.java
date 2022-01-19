package com.soydasm.taskmanagement.controllers.helper;

import com.soydasm.taskmanagement.enums.OperationEnum;

import java.util.HashMap;

public class ControllerHelper
{
    public static HashMap<String, OperationEnum> getAuthMap(String endPoint, OperationEnum operationEnum)
    {
        HashMap<String, OperationEnum> authMap = new HashMap<>();
        authMap.put(endPoint, operationEnum);
        return authMap;
    }
}
