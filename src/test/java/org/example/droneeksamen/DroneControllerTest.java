package org.example.droneeksamen;

import org.example.droneeksamen.controller.DroneController;

import org.example.droneeksamen.service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DroneControllerTest {

    @Mock
    private DroneService droneService;

    @InjectMocks
    private DroneController droneController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(droneController).build();
    }

    // Test to create a drone
    @Test
    void testAddDrone() throws Exception {
        // Mocking the service method for adding a drone
        doNothing().when(droneService).registerNewDrone();

        // Perform a POST request to /drones/add
        mockMvc.perform(post("/drones/add"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    // Ensure the response body contains the success message
                    String responseContent = result.getResponse().getContentAsString();
                    assert responseContent.contains("Drone successfully added.");
                });

        // Verify that the service method was called
        verify(droneService, times(1)).registerNewDrone();
    }
}