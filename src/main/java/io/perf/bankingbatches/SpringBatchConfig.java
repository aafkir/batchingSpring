package io.perf.bankingbatches;

import io.perf.bankingbatches.dao.BankTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig{
@Autowired private JobBuilderFactory jobBuilderFactory;
@Autowired private StepBuilderFactory stepBuilderFactory;
@Autowired private ItemReader<BankTransaction> bankTransactionItemReader;
@Autowired private ItemWriter<BankTransaction> bankTransactionItemWriter;
@Autowired private ItemProcessor<BankTransaction, BankTransaction> bankTransactionBankTransactionItemProcessor;

    public Job bankJob(){
        Step step1 = stepBuilderFactory.get("step-load-data")
                .<BankTransaction, BankTransaction> chunk(100)
                .reader(bankTransactionItemReader)
                .processor(bankTransactionBankTransactionItemProcessor)
                .writer(bankTransactionItemWriter)
                .build();
        return jobBuilderFactory.get("bank-data-loader-job")
                .start(step1).build();
    }

    public FlatFileItemReader<BankTransaction> flatFileItemReader (@Value("${inputFile}") Resource inputFile){
            FlatFileItemReader<BankTransaction> fileItemReader = new FlatFileItemReader<>();

            fileItemReader.setName("FFIR1");
           // fileItemReader.
    }
}
