package com.springbatch.wd.batch;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            List<Autobot> results = this.jdbcTemplate.query("SELECT id, variavel1, variavel2, variavel3, totalVendedor FROM autobot",
                    (rs, row) -> new Autobot(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getFloat(5)  ));

            for (Autobot autobot : results) {
                if(autobot.getId().equals("003")) {
	               	 String replace = autobot.getVariavel2().replace("[","").replace("]","");
	               	 List<String> myList = new ArrayList<String>(Arrays.asList(replace.split(",")));
	               	 float totalVendedor = 0;
	               	 for(String tempString:myList) {
		               	 List<String>  mySubList = Arrays.asList(tempString.split("-"));
		               	 float valorTotal = Float.parseFloat(mySubList.get(1)) * Float.parseFloat(mySubList.get(2));
		               	 totalVendedor += valorTotal;
		               	 this.jdbcTemplate.execute("INSERT INTO item (id, quantidade, preco, valorTotal, idVenda) "
		               				+ "VALUES ("+mySubList.get(0)+", "+mySubList.get(1)+", "+mySubList.get(2)+", "+valorTotal+", "+autobot.getVariavel1()+")");
		             }
	               	Float totalve = this.jdbcTemplate.query("SELECT totalVendedor FROM autobot where variavel2 like '"+autobot.getVariavel3()+"'",
	                        (rs, row) -> new Float(rs.getFloat(1) )).get(0);
	               	totalVendedor += totalve;
	               	this.jdbcTemplate.execute("update autobot set totalVendedor = "+totalVendedor+" where variavel2 like '"+autobot.getVariavel3()+"'");
                }
            }
           
            Integer countClientes = this.jdbcTemplate.query("SELECT count(*) FROM autobot where id like '001'",
                    (rs, row) -> new Integer(rs.getString(1) )).get(0);
            
            Integer countVendas = this.jdbcTemplate.query("SELECT count(*) FROM autobot where id like '002'",
                    (rs, row) -> new Integer(rs.getString(1) )).get(0);
            
            Integer maiorVenda = this.jdbcTemplate.query("SELECT idVenda, sum(valorTotal) as somaTotal FROM item group by idVenda order by somaTotal desc",
                    (rs, row) -> new Integer(rs.getString(1) )).get(0);
            
            String melhorVendedor = this.jdbcTemplate.query("select variavel2 FROM autobot where id like '001' order by totalVendedor asc",
            		(rs, row) -> new String(rs.getString(1) )).get(0);
    	    List<String[]> linhas = new ArrayList<>();
    	    linhas.add(new String[]{countClientes.toString(),countVendas.toString(),maiorVenda.toString(),melhorVendedor});
            try {
				gerarArquivo(linhas);
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            this.jdbcTemplate.execute("drop table autobot");
            this.jdbcTemplate.execute("drop table item");

        }
        
    }
    
    
    private void gerarArquivo(List<String[]> linhas) throws IOException{	
        Path pathAbsolutee = Paths.get("src/main/resources/data/out/saida.csv");
        Writer writer = Files.newBufferedWriter(pathAbsolutee);
	    CSVWriter csvWriter = new CSVWriter(writer);

        csvWriter.writeAll(linhas);
	
	    csvWriter.flush();
	    writer.close();
    }
}
