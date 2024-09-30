package com.akshat.journalApp.controller;

import com.akshat.journalApp.model.JournalEntry;
import com.akshat.journalApp.service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    JournalService journalService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        return journalService.getJournalEntries();
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry){
        entry.setDate(LocalDateTime.now());
        return journalService.createJournalEntry(entry);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry>  getJournalEntryById(@PathVariable ObjectId id){
        return journalService.getJournalEntryById(id);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry entry){
        return journalService.updateJournalEntryById(id, entry);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable ObjectId id){
        return journalService.deleteJournalEntryById(id);
    }
}
