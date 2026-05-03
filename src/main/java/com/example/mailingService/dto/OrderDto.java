package com.example.mailingService.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long code;
    private String interval;
    private Item item;
    private String email;
    private String reference;
    private String amount;
    private boolean payment;

    public Integer getAmount() {
        int count = 0;
        for(Item.Unit unit: item.getItems()){
            count += (unit.getPrice()) * Integer.parseInt(unit.getQuantity());
        }
        return count;
    }
    @Data
    public static class Item implements Serializable {

        private List<Unit> items;

        @Data
        public static class Unit implements Serializable {
            private String itemName;
            private Integer price;
            private String quantity;
            private String discount;
        }
    }
}
