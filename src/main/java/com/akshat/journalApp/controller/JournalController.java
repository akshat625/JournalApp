package com.akshat.journalApp.controller;

import com.akshat.journalApp.model.JournalEntry;
import com.akshat.journalApp.service.JournalService;
import com.akshat.journalApp.service.UserService;
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
    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String userName){
        return journalService.getAllJournalEntriesOfUser(userName);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry,
                                                    @PathVariable String userName){
        entry.setDate(LocalDateTime.now());
        return journalService.createJournalEntryOfUser(entry,userName);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry>  getJournalEntryById(@PathVariable ObjectId id){
        return journalService.getJournalEntryById(id);
    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id,
                                                               @PathVariable String userName,
                                                               @RequestBody JournalEntry entry){
        return journalService.updateJournalEntryById(id, userName, entry);
    }

    @DeleteMapping("id/{userName}/{id}")
    public ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable ObjectId id,
                                                               @PathVariable String userName){
        return journalService.deleteJournalEntryById(id, userName);
    }
}
