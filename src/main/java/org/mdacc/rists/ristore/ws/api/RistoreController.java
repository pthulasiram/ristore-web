package org.mdacc.rists.ristore.ws.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.mdacc.rists.ristore.ws.model.vo.FmReportVO;
import org.mdacc.rists.ristore.ws.service.RistoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
			value = "/ristore/foundation/",
			method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<List<FmReportVO>> getAllFmReports() {
		List<FmReportVO> reports = ristoreService.findAllFmReports();
		if (reports == null) {
			return new ResponseEntity<List<FmReportVO>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<FmReportVO>>(reports, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/ristore/foundation/blockid/{blockid}",
			method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<List<FmReportVO>> getFmReportsByBlockId(@PathVariable String blockid) {
		List<FmReportVO> reports = ristoreService.findFmReportsByBlockId(blockid);
		if (reports == null) {
			return new ResponseEntity<List<FmReportVO>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<FmReportVO>>(reports, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/ristore/foundation/reportid/{reportid}",
			method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<List<FmReportVO>> getFmReportsByReportId(@PathVariable String reportid) {
		List<FmReportVO> reports = ristoreService.findFmReportsByReportId(reportid);
		if (reports == null) {
			return new ResponseEntity<List<FmReportVO>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<FmReportVO>>(reports, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/ristore/foundation/mrn/{mrn}",
			method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<List<FmReportVO>> getFmReportsByMrn(@PathVariable String mrn) {
		List<FmReportVO> fmReports = ristoreService.findFmReportsByMrn(mrn);
		if (fmReports == null) {
			return new ResponseEntity<List<FmReportVO>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<FmReportVO>>(fmReports, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/ristore/foundation/xml/{filename}",
			method = RequestMethod.GET,
			produces = "application/xml")
	public ResponseEntity<byte[]> downloadXMLFile(@PathVariable String filename) throws IOException {
/*		FileSystemResource xmlFile = new FileSystemResource("/rsrch1/rists/moonshot/data/prod/foundation/xml/" + filename + ".xml");
		byte [] content = new byte[(int)xmlFile.contentLength()];
        IOUtils.read(xmlFile.getInputStream(), content);

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .contentLength(xmlFile.contentLength())
            .body(content);*/
		FileInputStream fileStream;
        fileStream = new FileInputStream(new File("/rsrch1/rists/moonshot/data/prod/foundation/xml/" + filename + ".xml"));
        byte[] content = IOUtils.toByteArray(fileStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData(filename + ".xml", filename + ".xml");
        return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
	}

}
