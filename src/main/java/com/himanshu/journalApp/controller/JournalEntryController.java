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
import com.himanshu.journalApp.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {
        try {
            List<JournalEntry> data = journalEntryService.getAllEntries();
            if(data.isEmpty()) {
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
            journalEntry.setDate(new Date());
            journalEntryService.saveEntry(journalEntry);
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
    @DeleteMapping("/id/{idString}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId idString) {
        try {
            journalEntryService.deleteEntryById(idString);
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
