package com.example.trello_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//Test connection
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import com.example.trello_clone.repository.BoardRepository;
@SpringBootApplication
@EnableTransactionManagement
// public class TrelloApplication implements CommandLineRunner {
public class TrelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrelloApplication.class, args);
	}
    
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> builder.indentOutput(true).failOnEmptyBeans(false);
    }

    //test connection
    //  @Autowired
    // private BoardRepository boardRepository;

    // @Override
    // public void run(String... args) throws Exception {
    //     // Test creating a board
    //     System.out.println("Testing database connection...");

    //     // Save a new board
    //     var board = new Board();
    //     board.setName("Test Board");
    //     boardRepository.save(board);

    //     // Fetch boards from the database
    //     boardRepository.findAll().forEach(System.out::println);
    // }
}

