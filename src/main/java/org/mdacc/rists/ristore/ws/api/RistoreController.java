package org.mdacc.rists.ristore.ws.api;

import java.util.List;

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
	public ResponseEntity<FmReportVO> getFmReportByBlockId(@PathVariable String blockid) {
		FmReportVO fmReportVO = ristoreService.findFmReportByBlockId(blockid);
		if (fmReportVO == null) {
			return new ResponseEntity<FmReportVO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<FmReportVO>(fmReportVO, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/ristore/foundation/reportid/{reportid}",
			method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<FmReportVO> getFmReportByReportId(@PathVariable String reportid) {
		FmReportVO fmReportVO = ristoreService.findFmReportByReportId(reportid);
		if (fmReportVO == null) {
			return new ResponseEntity<FmReportVO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<FmReportVO>(fmReportVO, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/ristore/foundation/mrn/{mrn}",
			method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<List<FmReportVO>> getFmReportByMrn(@PathVariable String mrn) {
		List<FmReportVO> fmReports = ristoreService.findFmReportByMrn(mrn);
		if (fmReports == null) {
			return new ResponseEntity<List<FmReportVO>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<FmReportVO>>(fmReports, HttpStatus.OK);
	}

}
