package com.akshat.journalApp.repo;

import com.akshat.journalApp.model.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepo extends MongoRepository<JournalEntry, Long> {
}
