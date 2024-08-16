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
public class FormSequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    /*
        collection
        ----------
         _id (collectionId): 'form_seq' (collectionIdVal)
         formId: 0 (generatedIdVal)
    */

    /**
     * @param collectionId id key of a collection for storing sequence
     * @param generatedIdValue value which will be used.
     * @param collectionIdVal the value of id collectionId - how we identify what to update.
     * @return
     */
    public long getSequenceNumber(String collectionId, String collectionIdVal, String generatedIdValue) {

        Query query = new Query(Criteria.where(collectionId).is(collectionIdVal));
        //update the sequence no
        Update update = new Update().inc(generatedIdValue, 1);
        //modify in document

        FormIdSequence counter = mongoOperations
                .findAndModify(query,
                        update, options().returnNew(true).upsert(true),
                        FormIdSequence.class);

        return Objects.isNull(counter) ? 1 : counter.getFormId();
    }

}
