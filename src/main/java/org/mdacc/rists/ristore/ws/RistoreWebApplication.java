package org.mdacc.rists.ristore.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

/**
 * Spring Boot main Application
 *
 */

@SpringBootApplication
public class RistoreWebApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(RistoreWebApplication.class, args);
    }
    
    // following method only needed if deployed as war
// 	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//         return application.sources(RistoreWebApplication.class);
//     }
}
