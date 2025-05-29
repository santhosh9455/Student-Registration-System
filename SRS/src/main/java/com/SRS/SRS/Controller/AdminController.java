package com.SRS.SRS.Controller;
import com.SRS.SRS.DTO.AdminDto;
import com.SRS.SRS.DTO.StudentDto;
import com.SRS.SRS.DTO.StudentUpdateDto;
import com.SRS.SRS.ServiceImplementation.AdminServiceImplementation;
import com.SRS.SRS.ServiceImplementation.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServiceImplementation adminServiceImplementation;

    @Autowired
    private EmailService emailService;

    @PostMapping("/createAdmin")
    public AdminDto createAdmin(@RequestBody AdminDto adminDto){
        return adminServiceImplementation.CreateAdmin(adminDto);

    }

    // Register the student
    @PostMapping("/registerStudent")
    public ResponseEntity<StudentDto> registerStudent(@Valid @RequestBody StudentDto studentDto,StudentUpdateDto studentUpdateDto){
        // Get student's email from the request
        String studentEmail = studentDto.getEmail();
        String studentName = studentDto.getName();

//        // Check if the student email already exists
        boolean userEmailExists = adminServiceImplementation.checkStudentByEmail(studentEmail);

        // If not exists, save student details
        StudentDto saved= adminServiceImplementation.registerStudent(studentDto,studentUpdateDto);

        // Send confirmation email to student
        try {
            emailService.sendSimpleEmail(studentEmail,"Registration Successful",
                    "Hi " + studentName + " " +
                            "You have been successfully registered");
        } catch (Exception e) {
            System.err.println("Email sending failed: " + e.getMessage());
            // Optional: log the error or alert admin
        }

        return ResponseEntity.ok(saved);
    }


    // get the student details by id
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(adminServiceImplementation.getStudentById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // get the student details by email
    @GetMapping("/email/{email}")
    public ResponseEntity<StudentDto> getStudentByEmail(@PathVariable String email) {
        return ResponseEntity.ok(adminServiceImplementation.getStudentByEmail(email));
    }

    // get the student details by username
    @GetMapping("/username/{username}")
    public ResponseEntity<StudentDto> getStudentByUsername(@PathVariable String username) {
        try{
            return ResponseEntity.ok(adminServiceImplementation.getStudentByUsername(username));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // get all student details
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        try {
            return ResponseEntity.ok(adminServiceImplementation.getAllStudents());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //Searching by Username or Email
    @GetMapping("/search")
    public ResponseEntity<StudentDto> getByEmailOrUsername(@RequestParam String search) {
        StudentDto student;

        if (search.contains("@")) {  // Check is email or not
            student = adminServiceImplementation.getStudentByEmail(search);
        } else {
            student = adminServiceImplementation.getStudentByUsername(search);
        }

        if (student == null) {
            return ResponseEntity.notFound().build(); // return 404 if not found
        }

        return ResponseEntity.ok(student);
    }

    // Update the student by id
    @PatchMapping("/updateStudent/{id}")
    public String updateStudent(@PathVariable Long id, @RequestBody StudentUpdateDto studentUpdateDto) {
        adminServiceImplementation.updateStudentPartial(id, studentUpdateDto);
        return "Student updated";
    }


    // Delete the student by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        adminServiceImplementation.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
