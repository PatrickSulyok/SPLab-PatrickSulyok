
package ro.uvt.sp.lab1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uvt.sp.lab1.services.AsyncCommandService;

import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final AsyncCommandService asyncCommandService;

    public TasksController(AsyncCommandService asyncCommandService) {
        this.asyncCommandService = asyncCommandService;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Map<String, Object>> getTaskStatus(@PathVariable String taskId) {
        Map<String, Object> status = asyncCommandService.getStatus(taskId);

        if ("NOT_FOUND".equals(status.get("status"))) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(status);
    }
}

