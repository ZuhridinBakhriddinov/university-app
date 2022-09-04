package uz.project.app_university.entity.group;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static uz.project.app_university.common.Constants.DEFAULT_PAGE_SIZE;

@Controller
@RequestMapping("${app.domain}/group")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/add")
    public ResponseEntity<?> addNewGroup(@Valid @RequestBody GroupDto groupDto) {
        return groupService.addNewGroup(groupDto);
    }

    @GetMapping("/get/{facultyId}")
    public ResponseEntity<?> getAllGroupsByFacultyId(
            @PathVariable Integer facultyId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "direction", defaultValue = "true") Boolean direction
    ) {
        return groupService.getAllGroupsByFacultyId(facultyId, page, size, sort, direction);
    }

    @PutMapping("/edit/{groupId}")
    public ResponseEntity<?> editGroup(
            @PathVariable Integer groupId,
            @Valid @RequestBody GroupDto groupDto
    ){
        return groupService.editGroupByGroupId(groupId,groupDto);
    }

    @GetMapping("/grade/table/{groupId}")
    public ResponseEntity<?> getGradesTable(@PathVariable Integer groupId){
        return groupService.getGradesTable(groupId);
    }

    @DeleteMapping("/delete/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Integer groupId){
        return groupService.deleteGroup(groupId);
    }

    @GetMapping("/add/student")
    public ResponseEntity<?> addStudentToTheGroup(
            @RequestParam(name = "groupId",defaultValue = "0") Integer groupId,
            @RequestParam(name = "studentId",defaultValue = "0") Integer studentId
    ){
        return groupService.addStudentToTheGroup(groupId,studentId);
    }

}
