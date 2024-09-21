package com.example.links.repository;

import com.example.links.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinksRepository extends JpaRepository<Link, String> {
    public Optional<Link> findByUrl(String url);
}
