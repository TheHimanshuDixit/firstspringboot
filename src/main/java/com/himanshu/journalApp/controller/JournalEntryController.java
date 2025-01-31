package com.himanshu.journalApp.controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.himanshu.journalApp.entity.JournalEntry;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<String, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean addJournalEntry(@RequestBody JournalEntry journalEntry) {
        journalEntries.put(journalEntry.getIdString(), journalEntry);
        return true;
    }

    // get journal by idString
    @GetMapping("/id/{idString}")
    public JournalEntry getJournalEntry(@PathVariable String idString) {
        return journalEntries.get(idString);
    }

    // delete journal by idString
    @DeleteMapping("/id/{idString}")
    public boolean deleteJournalEntry(@PathVariable String idString) {
        journalEntries.remove(idString);
        return true;
    }

    // update journal by idString
    @PutMapping("/id/{idString}")
    public boolean updateJournalEntry(@PathVariable String idString, @RequestBody JournalEntry journalEntry) {
        journalEntries.put(idString, journalEntry);
        return true;
    }

}
