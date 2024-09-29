package com.akshat.journalApp.service;

import com.akshat.journalApp.model.JournalEntry;
import com.akshat.journalApp.repo.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    @Autowired
    JournalRepo journalRepo;


    public List<JournalEntry> getJournalEntries() {
        return journalRepo.findAll();
    }

    public JournalEntry createJournalEntry(JournalEntry entry) {
        return journalRepo.save(entry);
    }

    public JournalEntry getJournalEntryById(ObjectId id) {
        return journalRepo.findById(id).orElse(null);
    }

    public JournalEntry updateJournalEntryById(ObjectId id, JournalEntry entry) {

        JournalEntry journalEntry = journalRepo.findById(id).orElse(null);
        if(journalEntry == null) {
            return null;
        }
        journalEntry.setTitle(entry.getTitle());
        journalEntry.setContent(entry.getContent());
        journalEntry.setDate(entry.getDate());
        return journalRepo.save(journalEntry);

    }

    public Optional<JournalEntry> deleteJournalEntryById(ObjectId id) {
        Optional<JournalEntry> entry = journalRepo.findById(id);
        journalRepo.deleteById(id);
        return entry;
    }
}
