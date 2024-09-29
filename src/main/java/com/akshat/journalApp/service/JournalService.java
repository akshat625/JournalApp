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

    public JournalEntry updateJournalEntryById(ObjectId id, JournalEntry newEntry) {

        JournalEntry oldEntry = journalRepo.findById(id).orElse(null);
        if(oldEntry == null) {
            return null;
        }
        oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
        oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
        return journalRepo.save(oldEntry);

    }

    public Optional<JournalEntry> deleteJournalEntryById(ObjectId id) {
        Optional<JournalEntry> journalEntry = journalRepo.findById(id);
        if(journalEntry.isPresent()) {
            journalRepo.deleteById(id);
        }
        return journalEntry;
    }
}
