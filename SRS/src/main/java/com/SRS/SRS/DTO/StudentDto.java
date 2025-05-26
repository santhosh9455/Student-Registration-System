package com.SRS.SRS.DTO;
import jakarta.validation.constraints.*;
import lombok.Data;

//@Data // for geters and seters
public class StudentDto {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    public String getUsername() {
		return username;
	}

	public StudentDto() {
		super();
	}

	public StudentDto(@NotBlank(message = "Username is required") String username,
			@NotBlank(message = "Name is required") String name,
			@NotNull(message = "Age is required") @Min(value = 18, message = "Age must be at least 18") Integer age,
			@NotBlank(message = "Gender is required") String gender,
			@Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email,
			@NotBlank(message = "Phone number is required") @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number") String phoneNumber,
			@NotBlank(message = "Department is required") String department) {
		super();
		this.username = username;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.department = department;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@NotBlank(message = "Gender is required")
    private String gender;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Department is required")
    private String department;

    
}
