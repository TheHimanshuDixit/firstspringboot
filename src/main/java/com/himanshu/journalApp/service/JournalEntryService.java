package com.himanshu.journalApp.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.himanshu.journalApp.entity.JournalEntry;
import com.himanshu.journalApp.entity.User;
import com.himanshu.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, User user) {
        try {
            journalEntry.setDate(new Date());
            JournalEntry data = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(data);
            userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        try {
            journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<JournalEntry> getAllEntries() {
        try {
            return journalEntryRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // return an empty list in case of an exception
        }
    }

    public JournalEntry getEntryById(ObjectId idString) {
        try {
            return journalEntryRepository.findById(idString).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // return null in case of an exception
        }
    }

    @Transactional
    public boolean deleteEntryById(ObjectId idString, User user) {
        try {
            boolean removed = user.getJournalEntries().removeIf(entry -> entry.getIdString().equals(idString));
            if (!removed) {
                return false;
            }
            userService.updateUser(user);
            journalEntryRepository.deleteById(idString);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
