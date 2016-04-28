package org.mdacc.rists.ristore.ws.api;

import java.io.IOException;
import java.io.InputStream;

import org.mdacc.rists.ristore.ws.service.RistoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RistoreController {

	@Autowired
	private RistoreService ristoreService;
	
	@RequestMapping("/")
    public String index() {
        return "Welcome to the home page!";
    }
	
	@RequestMapping( 
			value = "/ristore/foundation/{trf}", 
			method = RequestMethod.GET, 
			produces = "application/xml")
	public ResponseEntity<InputStreamResource> downloadXMLFile(@PathVariable String trf) throws IOException {
		InputStream xmlInputStream = ristoreService.findByTRF(trf);
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    headers.add("Pragma", "no-cache");
	    headers.add("Expires", "0");
		
		return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(new InputStreamResource(xmlInputStream));
	}
}
