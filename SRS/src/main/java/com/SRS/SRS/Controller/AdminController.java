package com.SRS.SRS.Controller;
import com.SRS.SRS.DTO.StudentDto;
import com.SRS.SRS.ExceptionsHandulars.ResourceAlreadyExistsException;
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

    // Register the student
    @PostMapping("/register-student")
    public String registerStudent(@Valid @RequestBody StudentDto studentDto) throws ResourceAlreadyExistsException {
        // Get student's email from the request
        String studentEmail = studentDto.getEmail();

        // Check if the student email already exists
        boolean userEmailExists = adminServiceImplementation.checkStudentByEmail(studentEmail);

        if (userEmailExists) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        // If not exists, save student details
        adminServiceImplementation.registerStudent(studentDto);

        // Send confirmation email to student
        try {
            emailService.sendSimpleEmail(studentEmail,"Registration Successful",
                    "Hi " + studentDto.getName() + " " +
                            "You have been successfully registered");
        } catch (Exception e) {
            System.err.println("Email sending failed: " + e.getMessage());
            // Optional: log the error or alert admin
        }

        return "Student Registered";
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
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto studentDto) {
        try {
            return ResponseEntity.ok(adminServiceImplementation.updateStudent(id, studentDto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // Delete the student by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        adminServiceImplementation.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
