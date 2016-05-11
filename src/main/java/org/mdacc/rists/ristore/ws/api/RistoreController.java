package org.mdacc.rists.ristore.ws.api;

import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mdacc.rists.ristore.ws.model.Foundation;
import org.mdacc.rists.ristore.ws.service.RistoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RistoreController {

	private static List<Foundation> fmList = new ArrayList<Foundation>();
	@Autowired
	private RistoreService ristoreService;
	@RequestMapping("/")
    public String index() {
        return "Welcome to the home page!";
    }
	
	static {
		for (int i = 1; i < 10; i++) {
			String reportID = "TRF000" + Integer.toString(i);
			String sample = reportID + ".1";
			String mrn = Integer.toString(i) + "0000";
			String diag = "Lung";
			Foundation fm = new Foundation(reportID, sample, mrn, diag);
			fmList.add(fm);
		}
	}
	
	@RequestMapping(
			value = "/ristore/foundation/",
			method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<List<Foundation>> getAllFoundation() {
		return new ResponseEntity<List<Foundation>>(fmList, HttpStatus.OK);
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
