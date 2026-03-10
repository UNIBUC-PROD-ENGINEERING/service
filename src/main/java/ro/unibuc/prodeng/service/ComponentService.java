package ro.unibuc.prodeng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import ro.unibuc.prodeng.model.ComponentEntity;
import ro.unibuc.prodeng.repository.ComponentRepository;
import ro.unibuc.prodeng.response.ComponentResponse;

import java.util.List;

@Service
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private MongoTemplate mongoTemplate; 

    public Page<ComponentResponse> getComponents(
            String category, 
            Boolean isConsumable, 
            Boolean available, 
            String search, 
            int page, 
            int size
    ) {
        // 1. Setăm paginarea
        Pageable pageable = PageRequest.of(page, size);
        Query query = new Query().with(pageable);

        if (category != null && !category.isBlank()) {
            query.addCriteria(Criteria.where("category").is(category));
        }
        
        if (isConsumable != null) {
            query.addCriteria(Criteria.where("isConsumable").is(isConsumable));
        }
        
        if (Boolean.TRUE.equals(available)) {
            query.addCriteria(Criteria.where("availableQuantity").gt(0));
        }
  
        if (search != null && !search.isBlank()) {
            Criteria searchCriteria = new Criteria().orOperator(
                    Criteria.where("name").regex(search, "i"),
                    Criteria.where("description").regex(search, "i")
            );
            query.addCriteria(searchCriteria);
        }

        List<ComponentEntity> entities = mongoTemplate.find(query, ComponentEntity.class);
        
        long total = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), ComponentEntity.class);

        List<ComponentResponse> responses = entities.stream()
                .map(this::toResponse)
                .toList();

        return new PageImpl<>(responses, pageable, total);
    }

 
    private ComponentResponse toResponse(ComponentEntity entity) {
        return new ComponentResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getPhotoUrls(), 
                entity.getQuantity(),
                entity.getAvailableQuantity(),
                entity.getIsConsumable(), 
                entity.getTags(),
                entity.getInfoMarkdown(),
                entity.getCreatedAt()
        );
    }
}