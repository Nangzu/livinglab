package com.example.livinglab.Entity;

import lombok.*;

@Getter
@Setter
public class OrderHistoryId implements java.io.Serializable {
    private Long history_num;
    private Long order;
}