package com.billmanagemtsystem.util;

public enum Message
{

    RESPONSE_STATUS_OK("success"),

    RESPONSE_STATUS_ERROR("failed"),

    USER_ALREADY_CREATED("User Already Created"),

    INVALID_CHOICE("Invalid Choice!"),

    ALREADY_PAID("Already Paid"),

    SERVER_UNREACHABLE("Server id Unreachable!"),

    INVALID_METER_ID("Meter ID Not Found!");

    private final Object constant;


    Message(Object constant)
    {

        this.constant = constant;
    }

    public Object getConstant()
    {

        return constant;
    }


}
