package com.himanshu.journalApp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal Entry", description = "Journal Entry API")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntriesOfUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
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

    @PostMapping
    public ResponseEntity<JournalEntry> addJournalEntry(@RequestBody JournalEntry journalEntry) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
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
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<JournalEntry> entries = user.getJournalEntries().stream()
                    .filter(entry -> entry.getIdString().equals(idString)).collect(Collectors.toList());
            if (entries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            JournalEntry data = journalEntryService.getEntryById(idString);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete journal by idString
    @DeleteMapping("/id/{idString}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId idString) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            boolean data = journalEntryService.deleteEntryById(idString, user);
            if (!data) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // update journal by idString
    @PutMapping("/id/{idString}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId idString,
            @RequestBody JournalEntry journalEntry) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<JournalEntry> entries = user.getJournalEntries().stream()
                    .filter(entry -> entry.getIdString().equals(idString)).collect(Collectors.toList());
            if (entries.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
