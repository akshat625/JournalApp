package com.akshat.journalApp.service;

import com.akshat.journalApp.model.JournalEntry;
import com.akshat.journalApp.repo.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    @Autowired
    JournalRepo journalRepo;


    public ResponseEntity<List<JournalEntry>> getJournalEntries() {
        try {
            return new ResponseEntity<>(journalRepo.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<JournalEntry> createJournalEntry(JournalEntry entry) {
        try{
            return new ResponseEntity<>(journalRepo.save(entry), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<JournalEntry> getJournalEntryById(ObjectId id) {
        try {
            return new ResponseEntity<>(journalRepo.findById(id).orElse(null), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<JournalEntry> updateJournalEntryById(ObjectId id, JournalEntry newEntry) {

        try{
            JournalEntry oldEntry = journalRepo.findById(id).orElse(null);
            if(oldEntry == null) {
                return null;
            }
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            return new ResponseEntity<>(journalRepo.save(oldEntry), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<JournalEntry> deleteJournalEntryById(ObjectId id) {
        try {
            JournalEntry journalEntry = journalRepo.findById(id).orElse(null);
            if(journalEntry != null) {
                journalRepo.deleteById(id);
                return new ResponseEntity<>(journalEntry,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
