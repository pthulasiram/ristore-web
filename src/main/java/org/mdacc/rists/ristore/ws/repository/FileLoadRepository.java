package org.mdacc.rists.ristore.ws.repository;

import org.mdacc.rists.bdi.models.FileLoadTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileLoadRepository extends JpaRepository<FileLoadTb, Long> {
	@Query("select f.uri from FileLoadTb f where f.rowId=?1")
	public String findUriByFileLoadId(long id);

}
