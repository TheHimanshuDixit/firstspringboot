package com.himanshu.journalApp.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.himanshu.journalApp.entity.JournalEntry;
import com.himanshu.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {
    
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry getEntryById(ObjectId idString) {
        return journalEntryRepository.findById(idString).orElse(null);
    }

    public void deleteEntryById(ObjectId idString) {
        journalEntryRepository.deleteById(idString);
    }
}
