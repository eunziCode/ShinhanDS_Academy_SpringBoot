package com.shinhan.firstzone.multikey;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Serializable(직렬화) Network 전송시 바이너리로 변경하여 보냄

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
@Embeddable
public class MultiKeyB implements Serializable{

	Integer orderId; // 주문번호
	Integer productId; // 상품번호
}
