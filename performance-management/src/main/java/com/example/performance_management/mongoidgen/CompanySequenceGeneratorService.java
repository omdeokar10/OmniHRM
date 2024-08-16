package com.example.performance_management.mongoidgen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class CompanySequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public long getCompanySequenceNumber(String collectionId, String collectionIdVal, String generatedIdValue) {

        Query query = new Query(Criteria.where(collectionId).is(collectionIdVal));
        //update the sequence no
        Update update = new Update().inc(generatedIdValue, 1);
        //modify in document

        CompanyIdSequence counter = mongoOperations
                .findAndModify(query,
                        update, options().returnNew(true).upsert(true),
                        CompanyIdSequence.class);

        return Objects.isNull(counter) ? 1 : counter.getCompanyId();
    }


}
