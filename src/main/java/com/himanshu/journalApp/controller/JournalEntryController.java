package com.himanshu.journalApp.controller;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.himanshu.journalApp.entity.JournalEntry;
import com.himanshu.journalApp.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public boolean addJournalEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(new Date());
        journalEntryService.saveEntry(journalEntry);
        return true;
    }

    // get journal by idString
    @GetMapping("/id/{idString}")
    public JournalEntry getJournalEntry(@PathVariable ObjectId idString) {
        return journalEntryService.getEntryById(idString);
    }

    // delete journal by idString
    @DeleteMapping("/id/{idString}")
    public boolean deleteJournalEntry(@PathVariable ObjectId idString) {
        journalEntryService.deleteEntryById(idString);
        return true;
    }

    // update journal by idString
    @PutMapping("/id/{idString}")
    public JournalEntry updateJournalEntry(@PathVariable ObjectId idString, @RequestBody JournalEntry journalEntry) {
        JournalEntry entry = journalEntryService.getEntryById(idString);
        if (entry == null) {
            return null;
        }
        entry.setTitle(journalEntry.getTitle() != null ? journalEntry.getTitle() : entry.getTitle());
        entry.setContent(journalEntry.getContent() != null ? journalEntry.getContent() : entry.getContent());
        journalEntryService.saveEntry(entry);

        return entry;
    }

}
