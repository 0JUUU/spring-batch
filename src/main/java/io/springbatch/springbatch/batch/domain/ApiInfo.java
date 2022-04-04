package io.springbatch.springbatch.batch.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiInfo {

    private String url;
    private List<? extends ApiRequestVO> apiRequestList;
}
