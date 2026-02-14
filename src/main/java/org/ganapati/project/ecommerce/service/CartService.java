package org.ganapati.project.ecommerce.service;

import org.ganapati.project.ecommerce.common.BaseResponse;
import org.ganapati.project.ecommerce.dto.CartRequest;
import org.ganapati.project.ecommerce.dto.CartResponse;

public interface CartService {
    CartResponse addToCart(CartRequest cartRequest);

    BaseResponse<CartResponse> getCartByCartId(Long cardId);

    BaseResponse<CartResponse> getCartsByUserEmail(int page, int size);

    void deleteCartById(Long cartId);

}
