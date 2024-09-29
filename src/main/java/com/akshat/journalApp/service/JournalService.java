package com.akshat.journalApp.service;

import com.akshat.journalApp.model.JournalEntry;
import com.akshat.journalApp.repo.JournalRepo;
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

    public JournalEntry getJournalEntryById(Long id) {
        return journalRepo.findById(id).orElse(null);
    }

    public JournalEntry updateJournalEntryById(Long id, JournalEntry entry) {
        return journalRepo.save(entry);

    }

    public Optional<JournalEntry> deleteJournalEntryById(Long id) {
        Optional<JournalEntry> entry = journalRepo.findById(id);
        journalRepo.deleteById(id);
        return entry;
    }
}
