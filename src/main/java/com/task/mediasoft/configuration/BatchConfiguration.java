package com.task.mediasoft.configuration;

import com.task.mediasoft.product.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
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

/**
 * Конфигурационный класс BatchConfiguration предоставляет настройки для выполнения
 * пакетных операций в приложении. Он определяет компоненты Spring Batch,
 * такие как чтение данных, обработка данных и запись данных.
 * Этот класс активируется только в профиле "prod" и при условии,
 * что параметры app.scheduling.enabled, app.scheduling.optimization установлены в "true",
 * а app.scheduling.preparedStatement установлены в "false".
 */
@Configuration
@ConditionalOnExpression("'${app.scheduling.enabled}'.equals('true') and '${app.scheduling.optimization}'.equals('true') and '${app.scheduling.preparedStatement}'.equals('false')")
@Profile("prod")
public class BatchConfiguration {

    /**
     * Создает JdbcPagingItemReader для чтения данных о продуктах из источника данных.
     *
     * @param dataSource Источник данных, из которого будут читаться продукты.
     * @return JdbcPagingItemReader для чтения данных о продуктах.
     * @throws Exception если возникают проблемы с созданием объекта JdbcPagingItemReader.
     */
    @Bean
    public JdbcPagingItemReader<Product> reader(DataSource dataSource) throws Exception {
        JdbcPagingItemReader<Product> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(10000);
        reader.setRowMapper(productRowMapper());
        reader.setQueryProvider(queryProvider());
        reader.setPageSize(1000);
        return reader;
    }

    /**
     * Создает RowMapper для сопоставления строк базы данных с объектами Product.
     *
     * @return RowMapper для сопоставления строк базы данных с объектами Product.
     */
    @Bean
    public RowMapper<Product> productRowMapper() {
        return new BeanPropertyRowMapper<>(Product.class);
    }

    /**
     * Создает PostgresPagingQueryProvider для построения SQL-запросов на основе пагинации.
     *
     * @return PostgresPagingQueryProvider для построения SQL-запросов.
     * @throws Exception если возникают проблемы с созданием объекта PostgresPagingQueryProvider.
     */
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

    /**
     * Создает ItemProcessor для обработки продуктов.
     *
     * @return ItemProcessor для обработки продуктов.
     */
    @Bean
    public ItemProcessor<Product, Product> processor() {
        return new ProductItemProcessor();
    }

    /**
     * Создает JdbcBatchItemWriter для записи обновлений цены продуктов в базу данных.
     *
     * @param dataSource Источник данных для записи.
     * @return JdbcBatchItemWriter для записи обновлений цены продуктов.
     */
    @Bean
    public JdbcBatchItemWriter<Product> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>()
                .sql("UPDATE product SET price = :price WHERE id = :id")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    /**
     * Создает FlatFileItemWriter для записи продуктов в файл.
     *
     * @return FlatFileItemWriter для записи продуктов в файл.
     */
    @Bean
    public FlatFileItemWriter<Product> fileWriter() {
        return new FlatFileItemWriterBuilder<Product>()
                .name("productItemWriter")
                .shouldDeleteIfExists(true)
                .resource(new FileSystemResource("products.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }

    /**
     * Создает CompositeItemWriter для объединения JdbcBatchItemWriter и FlatFileItemWriter.
     *
     * @param dataSource Источник данных для записи в базу данных.
     * @return CompositeItemWriter для записи данных в различные источники.
     */
    @Bean
    public CompositeItemWriter<Product> compositeItemWriter(DataSource dataSource) {
        CompositeItemWriter<Product> compositeWriter = new CompositeItemWriter<>();
        compositeWriter.setDelegates(Arrays.asList(writer(dataSource), fileWriter()));
        return compositeWriter;
    }

    /**
     * Создает Job для импорта продуктов.
     *
     * @param jobRepository Репозиторий Job для создания заданий.
     * @param step1         Шаг для выполнения задания импорта продуктов.
     * @return Job для импорта продуктов.
     */
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(step1)
                .build();
    }

    /**
     * Создает Step для обработки импорта продуктов.
     *
     * @param jobRepository       Репозиторий Job для создания заданий.
     * @param transactionManager  Менеджер транзакций для управления транзакциями шага.
     * @param reader              Читатель данных о продуктах.
     * @param processor           Процессор для обработки данных о продуктах.
     * @param compositeItemWriter Объединенный писатель для записи данных о продуктах.
     * @return Step для обработки импорта продуктов.
     */
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      JdbcPagingItemReader<Product> reader, ItemProcessor<Product, Product> processor,
                      CompositeItemWriter<Product> compositeItemWriter) {
        return new StepBuilder("step1", jobRepository)
                .<Product, Product>chunk(100000, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(compositeItemWriter)
                .build();
    }
}