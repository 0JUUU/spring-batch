package io.springbatch.springbatch.batch.partition;

import io.springbatch.springbatch.batch.domain.ProductVO;
import io.springbatch.springbatch.batch.job.api.QueryGenerator;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class ProductPartitioner implements Partitioner {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        ProductVO[] productList = QueryGenerator.getProductList(dataSource);
        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 0;

        for (int i = 0; i < productList.length; i++) {
            ExecutionContext value = new ExecutionContext();
            value.put("product", productList[i]);
            result.put("partition" + number, value);
            number++;
        }
        return result;
    }
}
