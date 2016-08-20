package org.mdacc.rists.ristore.ws.repository;

import java.util.List;

import javax.persistence.NamedQuery;

import org.mdacc.rists.bdi.models.SpecimenTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecimenRepository extends JpaRepository<SpecimenTb, Long> {
	
	@Query("select s from SpecimenTb s")
	public List<SpecimenTb> findAll();
	
	@Query("select s from SpecimenTb s where s.specimenNo=?1")
	public SpecimenTb findBySpecimenNo(String specimenNo);
	
	@Query("select s from SpecimenTb s where s.mrn=?1")
	public List<SpecimenTb> findByMrn(String mrn);

}
