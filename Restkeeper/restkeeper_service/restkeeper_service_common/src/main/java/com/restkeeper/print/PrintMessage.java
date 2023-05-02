package com.restkeeper.print;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrintMessage implements Serializable {

    private static final long serialVersionUID = 1;

    private String orderId;

    private int printType;

    private boolean isFront;

    private String dishId;

    private int dishNumber;

    private String storeId;

    private String shopId;

}
