package com.task.mediasoft.configuration;

import com.task.mediasoft.product.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnExpression("'${app.scheduling.enabled}'.equals('true') and '${app.scheduling.optimization}'.equals('true')")
@Profile("prod")
public class BatchConfiguration {
    @Bean
    public JdbcPagingItemReader<Product> reader(DataSource dataSource) throws Exception {
        JdbcPagingItemReader<Product> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        // вытянули из бд за запрос
        reader.setFetchSize(10000);
        reader.setRowMapper(productRowMapper());
        reader.setQueryProvider(queryProvider());
        // размер порции данных, которая будет загружена и обработана за один раз в рамках пейджинга данных
        reader.setPageSize(1000);
        return reader;
    }

    @Bean
    public RowMapper<Product> productRowMapper() {
        return new BeanPropertyRowMapper<>(Product.class);
    }

    @Bean
    public PostgresPagingQueryProvider queryProvider() throws Exception {
        PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
        queryProvider.setSelectClause("SELECT *");
        queryProvider.setFromClause("FROM product");
        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        return queryProvider;
    }

    @Bean
    public ItemProcessor<Product, Product> processor() {
        return new ProductItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Product> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>()
                .sql("UPDATE product SET price = :price WHERE id = :id")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Product> itemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Product>()
                .dataSource(dataSource)
                .name("creditReader")
//                .sql("SELECT * FROM product ORDER BY id ASC LIMIT 1000 FOR UPDATE")
                .sql("SELECT * FROM product FOR UPDATE")
//                .sql("SELECT * FROM product")
                .rowMapper(productRowMapper())
                .build();
    }


    @Bean
    public FlatFileItemWriter<Product> fileWriter() {
        return new FlatFileItemWriterBuilder<Product>()
                .name("productItemWriter")
                .shouldDeleteIfExists(true)
                .resource(new FileSystemResource("products.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    @Bean
    public CompositeItemWriter<Product> compositeItemWriter(DataSource dataSource) {
        CompositeItemWriter<Product> compositeWriter = new CompositeItemWriter<>();
        compositeWriter.setDelegates(Arrays.asList(writer(dataSource), fileWriter()));
        return compositeWriter;
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      JdbcCursorItemReader<Product> reader, ItemProcessor<Product, Product> processor,
                      CompositeItemWriter<Product> compositeItemWriter) {
        return new StepBuilder("step1", jobRepository)
                .<Product, Product>chunk(100000, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(compositeItemWriter)
                .build();
    }
}