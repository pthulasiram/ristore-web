package org.mdacc.rists.ristore.ws.repository;

import org.mdacc.rists.bdi.models.FmReportTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FmReportRepository extends JpaRepository<FmReportTb, Long> {
	@Query("select f from FmReportTb f where f.frBlockId=?1")
	public FmReportTb findByBlockId(String blockId);
}
