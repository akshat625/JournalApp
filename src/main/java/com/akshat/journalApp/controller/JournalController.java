package com.akshat.journalApp.controller;

import com.akshat.journalApp.model.JournalEntry;
import com.akshat.journalApp.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    JournalService journalService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalService.getJournalEntries();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry entry){
        return journalService.createJournalEntry(entry);
    }

    @GetMapping("id/{id}")
    public JournalEntry  getJournalEntryById(@PathVariable Long id){
        return journalService.getJournalEntryById(id);
    }

    @PutMapping("id/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable Long id, @RequestBody JournalEntry entry){
        return journalService.updateJournalEntryById(id, entry);
    }

    @DeleteMapping("id/{id}")
    public Optional<JournalEntry> deleteJournalEntryById(@PathVariable Long id){
        return journalService.deleteJournalEntryById(id);
    }
}
