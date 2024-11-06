package qlpk.test;

import qlpk.entity.Doctor;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {

    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        doctor = new Doctor("1", "John Doe", 50, "Cardiology");  // Thay đổi theo các giá trị phù hợp
    }


    @Test
    public void testDoctorName() {
        assertEquals("John Doe", doctor.getName());
    }

    @Test
    public void testDoctorSpecialty() {
        assertEquals("Cardiology", doctor.getSpecialty());
    }

    // Add more test methods as needed
}

