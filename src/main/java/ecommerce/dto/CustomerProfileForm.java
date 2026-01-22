package ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Author: Theingi Myint
public class CustomerProfileForm {

    @NotBlank(message = "Full name is required")
    @Size(max = 40, message = "Full name must be at most 40 characters")
    private String fullName;

    @NotBlank(message = "Address is required")
    private String address;

    @Pattern(regexp = "^[0-9]{8}$", message = "Phone number must be 8 digits")
    private String phoneNumber;

    public CustomerProfileForm() {
    }

    public CustomerProfileForm(String fullName, String address, String phoneNumber) {
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

