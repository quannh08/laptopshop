package com.laptopshop.laptopshop.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ReviewPageResponse extends PageResponseAbstract implements Serializable {
    List<ReviewResponse> reviewResponses;
}
