package org.ganapati.project.ecommerce.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T>   implements Serializable {
    T data;
    Result result;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(data, new Result(CommonConstants.SUCCESS_CODE, CommonConstants.SUCCESS));
    }
}
