package com.himanshu.journalApp.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.himanshu.journalApp.entity.JournalEntry;
import com.himanshu.journalApp.entity.User;
import com.himanshu.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {
    
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, User user) {
        journalEntry.setDate(new Date());
        JournalEntry data = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(data);
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry getEntryById(ObjectId idString) {
        return journalEntryRepository.findById(idString).orElse(null);
    }

    public void deleteEntryById(ObjectId idString, User user) {
        journalEntryRepository.deleteById(idString);
        user.getJournalEntries().removeIf(entry -> entry.getIdString().equals(idString));
        userService.saveUser(user);
    }
}
