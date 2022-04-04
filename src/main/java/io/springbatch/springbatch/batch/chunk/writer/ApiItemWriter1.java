package io.springbatch.springbatch.batch.chunk.writer;

import io.springbatch.springbatch.batch.domain.ApiRequestVO;
import io.springbatch.springbatch.batch.domain.ApiResponseVO;
import io.springbatch.springbatch.service.AbstractApiService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class ApiItemWriter1 implements ItemWriter<ApiRequestVO> {

    private final AbstractApiService apiService;

    public ApiItemWriter1(AbstractApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void write(List<? extends ApiRequestVO> items) throws Exception {
        ApiResponseVO responseVo = apiService.service(items);
        System.out.println("responseVo = " + responseVo);
    }
}
