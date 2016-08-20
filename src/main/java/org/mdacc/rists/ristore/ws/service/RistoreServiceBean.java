package org.mdacc.rists.ristore.ws.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.mdacc.rists.bdi.models.FmReportTb;
import org.mdacc.rists.bdi.models.SpecimenTb;
import org.mdacc.rists.ristore.ws.model.vo.FmReportVO;
import org.mdacc.rists.ristore.ws.repository.FmReportRepository;
import org.mdacc.rists.ristore.ws.repository.SpecimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RistoreServiceBean implements RistoreService {

	@Autowired
	private SpecimenRepository specimenRepository;
	
	@Autowired
	private FmReportRepository fmReportRepository;
	
	@Override
	public List<SpecimenTb> findAll() {
		List<SpecimenTb> specimens = specimenRepository.findAll();
		return specimens;
	}
	
	@Override
	public FmReportVO findFmReportByBlockId(String blockId) {
		FmReportTb fmReport = fmReportRepository.findByBlockId(blockId);
		if (fmReport == null) {
			return null;
		}
		FmReportVO fmReportVO = new FmReportVO(fmReport);
		return fmReportVO;
	}
	
	public InputStream findByTRF(String trf) throws IOException {
		String filePath = "/Users/djiao/Box Sync/Work/Projects/RIStore/foundation/foundation_new/" + trf + ".xml";
		File file = new File(filePath);
		if (!file.exists()) {
			System.err.println("File " + filePath + " does not exist.");
			System.exit(1);
		}
		return new FileInputStream(file);
	}

}
