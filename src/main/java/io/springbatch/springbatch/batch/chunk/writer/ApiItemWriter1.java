package io.springbatch.springbatch.batch.chunk.writer;

import io.springbatch.springbatch.batch.domain.ApiRequestVO;
import io.springbatch.springbatch.batch.domain.ApiResponseVO;
import io.springbatch.springbatch.service.AbstractApiService;
import java.util.List;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

public class ApiItemWriter1 extends FlatFileItemWriter<ApiRequestVO> {

    private final AbstractApiService apiService;

    public ApiItemWriter1(AbstractApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void write(List<? extends ApiRequestVO> items) throws Exception {
        ApiResponseVO responseVo = apiService.service(items);
        System.out.println("responseVo = " + responseVo);

        items.forEach(item -> item.setApiResponseVO(responseVo));
        super.setResource(new FileSystemResource("/Users/a1101717/Desktop/inflearn/스프링 배치 - Sping Boot 기반으로 개발하는 Spring Batch/spring-batch/src/main/resources/product1.txt"));
        super.open(new ExecutionContext());
        super.setLineAggregator(new DelimitedLineAggregator<>());
        super.setAppendAllowed(true);
        super.write(items);
    }

}
