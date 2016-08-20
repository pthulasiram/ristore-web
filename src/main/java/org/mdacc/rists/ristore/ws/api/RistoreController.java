package org.mdacc.rists.ristore.ws.api;

import java.util.ArrayList;
import java.util.List;

import org.mdacc.rists.bdi.models.FmReportTb;
import org.mdacc.rists.bdi.models.SpecimenTb;
import org.mdacc.rists.ristore.ws.model.Foundation;
import org.mdacc.rists.ristore.ws.model.vo.FmReportVO;
import org.mdacc.rists.ristore.ws.service.RistoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
			value = "/ristore/foundation/",
			method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<List<SpecimenTb>> getAll() {
		List<SpecimenTb> specimens = ristoreService.findAll();
		if (specimens == null) {
			return new ResponseEntity<List<SpecimenTb>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<SpecimenTb>>(specimens, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/ristore/foundation/blockid/{blockid}",
			method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<FmReportVO> getFmReportByBlockId(@PathVariable String blockid) {
		FmReportVO fmReportVO = ristoreService.findFmReportByBlockId(blockid);
		if (fmReportVO == null) {
			return new ResponseEntity<FmReportVO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<FmReportVO>(fmReportVO, HttpStatus.OK);
	}
//	@RequestMapping( 
//			value = "/ristore/foundation/{trf}", 
//			method = RequestMethod.GET, 
//			produces = "application/xml")
//	public ResponseEntity<InputStreamResource> downloadXMLFile(@PathVariable String trf) throws IOException {
//		InputStream xmlInputStream = ristoreService.findByTRF(trf);
//		HttpHeaders headers = new HttpHeaders();
//	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//	    headers.add("Pragma", "no-cache");
//	    headers.add("Expires", "0");
//		
//		return ResponseEntity
//	            .ok()
//	            .headers(headers)
//	            .contentType(MediaType.parseMediaType("application/octet-stream"))
//	            .body(new InputStreamResource(xmlInputStream));
//	}
}
