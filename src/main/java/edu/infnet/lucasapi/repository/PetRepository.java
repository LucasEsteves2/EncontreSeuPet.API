package edu.infnet.lucasapi.repository;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {
}
