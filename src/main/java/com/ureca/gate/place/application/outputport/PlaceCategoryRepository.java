package com.ureca.gate.place.application.outputport;

import com.ureca.gate.place.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PlaceCategoryRepository {

    public List<Category> findByAll();


}
