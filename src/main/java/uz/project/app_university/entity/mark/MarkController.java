package uz.project.app_university.entity.mark;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("${app.domain}/mark")
public class MarkController {
    private final MarkService markService;

    @PostMapping("/student")
    public ResponseEntity<?> evaluateTheStudent(@RequestBody MarkDto markDto) {
        return markService.evaluateTheStudent(markDto);
    }

}
