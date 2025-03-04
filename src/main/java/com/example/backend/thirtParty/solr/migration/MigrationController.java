package com.example.backend.thirtParty.solr.migration;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/solr/migrate")
public class MigrationController {
    private final BookMigrationService migrationService;

    public MigrationController(BookMigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @PostMapping
    public String migrateBooks() {
        try {
            migrationService.migrateBooksToSolr();
            return "Migration successful!";
        } catch (Exception e) {
            System.out.println(e);
            return "Migration failed: " + e.getMessage();
        }
    }
}
