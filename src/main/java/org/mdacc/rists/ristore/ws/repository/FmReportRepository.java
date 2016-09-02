package org.mdacc.rists.ristore.ws.repository;

import java.util.List;

import org.mdacc.rists.bdi.models.FmReportTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FmReportRepository extends JpaRepository<FmReportTb, Long> {
	@Query("select f from FmReportTb f where f.deleteTs is not null")
	public List<FmReportTb> findAllFmReports();
	
	@Query("select f from FmReportTb f where f.frBlockId=?1 and f.deleteTs is not null")
	public FmReportTb findByBlockId(String blockId);
	
	@Query("select f from FmReportTb f where f.frReportId=?1 and f.deleteTs is not null")
	public FmReportTb findByReportId(String reportId);
	
}
