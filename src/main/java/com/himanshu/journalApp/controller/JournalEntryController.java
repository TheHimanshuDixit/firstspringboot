package com.himanshu.journalApp.controller;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.himanshu.journalApp.entity.JournalEntry;
import com.himanshu.journalApp.entity.User;
import com.himanshu.journalApp.service.JournalEntryService;
import com.himanshu.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<List<JournalEntry>> getAllEntriesOfUser(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<JournalEntry> data = user.getJournalEntries();
            if (data.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> addJournalEntry(@RequestBody JournalEntry journalEntry,
            @PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            journalEntryService.saveEntry(journalEntry, user);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // get journal by idString
    @GetMapping("/id/{idString}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable ObjectId idString) {
        try {
            JournalEntry data = journalEntryService.getEntryById(idString);
            if (data == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete journal by idString
    @DeleteMapping("/id/{username}/{idString}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId idString, @PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            journalEntryService.deleteEntryById(idString, user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // update journal by idString
    @PutMapping("/id/{username}/{idString}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId idString,
            @PathVariable String username,
            @RequestBody JournalEntry journalEntry) {
        try {
            JournalEntry entry = journalEntryService.getEntryById(idString);
            if (entry == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            entry.setTitle(journalEntry.getTitle() != null ? journalEntry.getTitle() : entry.getTitle());
            entry.setContent(journalEntry.getContent() != null ? journalEntry.getContent() : entry.getContent());
            journalEntryService.saveEntry(entry);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
