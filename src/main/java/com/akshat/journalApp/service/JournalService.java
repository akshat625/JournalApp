package com.akshat.journalApp.service;

import com.akshat.journalApp.model.JournalEntry;
import com.akshat.journalApp.model.User;
import com.akshat.journalApp.repo.JournalRepo;
import com.akshat.journalApp.repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JournalService {

    @Autowired
    JournalRepo journalRepo;

    @Autowired
    UserRepo userRepo;


    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(String userName) {
        try {
            User user = userRepo.findByUserName(userName);
            if(!journalRepo.findAll().isEmpty()) {
                return new ResponseEntity<>(user.getJournalEntries(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ResponseEntity<JournalEntry> createJournalEntryOfUser(JournalEntry entry, String userName) {
        try{
            User user = userRepo.findByUserName(userName);
            journalRepo.save(entry);
            user.getJournalEntries().add(entry);
            userRepo.save(user);
            return new ResponseEntity<>( HttpStatus.CREATED);
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

    public ResponseEntity<JournalEntry> updateJournalEntryById(ObjectId id, String userName, JournalEntry newEntry) {

        try{
            User user = userRepo.findByUserName(userName);
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

    @Transactional
    public ResponseEntity<JournalEntry> deleteJournalEntryById(ObjectId id, String userName) {
        try {
//            User user = userRepo.findByJournalEntriesId(id);
            User user = userRepo.findByUserName(userName);
            if(user != null) {
                user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
                userRepo.save(user);
            }
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
