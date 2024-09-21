package com.example.links.repository;

import com.example.links.model.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinksRepository extends CrudRepository<Link, String> {
    public Optional<Link> findByUrl(String url);
}
