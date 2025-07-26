package abdumalik.prodev.searchservice.controller;

import abdumalik.prodev.searchservice.service.SearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "API for global search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> search(@RequestParam String q) {
        return ResponseEntity.ok(searchService.searchAll(q));
    }

}