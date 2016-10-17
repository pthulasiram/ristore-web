package org.mdacc.rists.ristore.ws.service;

import java.util.List;

import org.mdacc.rists.ristore.ws.model.vo.FmReportVO;
import org.mdacc.rists.ristore.ws.models.FoundationTRF;

public interface RistoreService {

	List<FmReportVO> findAllFmReports();
	List<FmReportVO> findFmReportsByBlockId(String blockId);
	List<FmReportVO> findFmReportsByReportId(String reportId);
	List<FmReportVO> findFmReportsByMrn(String mrn);
	void updateTRFList(List<FoundationTRF> trfs);
}
