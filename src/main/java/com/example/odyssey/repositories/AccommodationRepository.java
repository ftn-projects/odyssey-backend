package com.example.odyssey.repositories;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @NonNull
    public Page<Accommodation> findAll(Pageable page);

    public Accommodation findOneById(Long id);

    public List<Accommodation> findAllByHost(Host host);

}
