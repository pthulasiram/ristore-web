package org.mdacc.rists.ristore.ws.service;

import java.util.List;

import org.mdacc.rists.ristore.ws.model.vo.FmReportVO;

public interface RistoreService {

	List<FmReportVO> findAllFmReports();
	FmReportVO findFmReportByBlockId(String blockId);
	FmReportVO findFmReportByReportId(String reportId);
	List<FmReportVO> findFmReportByMrn(String mrn);
}
