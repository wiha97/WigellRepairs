package org.example.wigellrepairs.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.wigellrepairs.dtos.services.CreateServiceTypeDTO;
import org.example.wigellrepairs.dtos.technicians.MinimalTechnicianDTO;
import org.example.wigellrepairs.entities.ServiceType;
import org.example.wigellrepairs.enums.Expertise;
import org.example.wigellrepairs.repositories.ServiceTypeRepo;
import org.example.wigellrepairs.services.CurrencyService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ServicesControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceTypeRepo typeRepo;

    @MockitoBean
    private CurrencyService currencyMock;

    private ObjectMapper mapper;
    private CreateServiceTypeDTO createService;
    List<MinimalTechnicianDTO> techs;

    @BeforeEach
    void setUp(){
        mapper = new ObjectMapper();
        createService = new CreateServiceTypeDTO();
        techs = new ArrayList<>();
    }

    @AfterEach
    void tearDown(){
        mapper = new ObjectMapper();
        createService = new CreateServiceTypeDTO();
        techs = new ArrayList<>();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addService_AddWithAllFieldsShouldReturnOk() throws Exception {
        createService.setName("New service");
        createService.setSek(899.99);
        createService.setCategory(Expertise.ELECTRONICS);
        MinimalTechnicianDTO moss = new MinimalTechnicianDTO();
        moss.setId(2l);
        techs.add(moss);
        createService.setTechnician(techs);
        when(currencyMock.getConversion(anyDouble(), anyString(), anyString())).thenReturn(85.68);

        mockMvc.perform(post("/wigellrepairs/addservice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createService)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("New service"))
            .andExpect(jsonPath("$.sek").value(899.99))
            .andExpect(jsonPath("$.eur").value(85.68))
            .andExpect(jsonPath("$.category").value(Expertise.ELECTRONICS.name()))
            .andExpect(jsonPath("$.technicians").isNotEmpty())
        ;
        ServiceType service = typeRepo.findByNameIgnoreCase("new service");
        assertNotNull(service);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addService_AddServiceWithExistingNameShouldReturn409AndNotAddNew() throws Exception {
        Long serviceCount = typeRepo.count();
        createService.setName("New service");
        createService.setSek(899.99);
        createService.setCategory(Expertise.ELECTRONICS);
        MinimalTechnicianDTO moss = new MinimalTechnicianDTO();
        moss.setId(2l);
        techs.add(moss);
        createService.setTechnician(techs);
        when(currencyMock.getConversion(anyDouble(), anyString(), anyString())).thenReturn(85.68);

        mockMvc.perform(post("/wigellrepairs/addservice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createService)))
            .andExpect(status().isConflict())
        ;

        assertEquals(serviceCount, typeRepo.count());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addService_TooLongNameShouldReturnOutOfRangeException() throws Exception {
        Long serviceCount = typeRepo.count();
        createService.setName("New service but with a really really really long name that exceeds 35 characters");
        createService.setSek(899.99);
        createService.setCategory(Expertise.ELECTRONICS);
        MinimalTechnicianDTO moss = new MinimalTechnicianDTO();
        moss.setId(2l);
        techs.add(moss);
        createService.setTechnician(techs);
        when(currencyMock.getConversion(anyDouble(), anyString(), anyString())).thenReturn(85.68);

        mockMvc.perform(post("/wigellrepairs/addservice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createService)))
            .andExpect(status().isRequestedRangeNotSatisfiable())
        ;

        assertEquals(serviceCount, typeRepo.count());
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addService_EmptyTechniciansListShouldReturnOk() throws Exception {
        createService.setName("New service 2");
        createService.setSek(899.99);
        createService.setCategory(Expertise.ELECTRONICS);
        createService.setTechnician(techs);
        when(currencyMock.getConversion(anyDouble(), anyString(), anyString())).thenReturn(85.68);

        mockMvc.perform(post("/wigellrepairs/addservice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createService)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("New service 2"))
            .andExpect(jsonPath("$.sek").value(899.99))
            .andExpect(jsonPath("$.eur").value(85.68))
            .andExpect(jsonPath("$.category").value(Expertise.ELECTRONICS.name()))
            .andExpect(jsonPath("$.technicians").isEmpty())
        ;
        verify(currencyMock, times(1)).getConversion(anyDouble(), anyString(), anyString());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addService_MissingFieldShouldReturnUnprocessable() throws Exception {
        // Ingen tekniker lista
        Long serviceCount = typeRepo.count();
        createService.setName("New service 3");
        createService.setSek(899.99);
        createService.setCategory(Expertise.ELECTRONICS);
        when(currencyMock.getConversion(anyDouble(), anyString(), anyString())).thenReturn(85.68);

        mockMvc.perform(post("/wigellrepairs/addservice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createService)))
            .andExpect(status().isUnprocessableEntity())
        ;
        assertEquals(serviceCount, typeRepo.count());
    }

    @Test
    @WithMockUser(username = "John", roles = {"USER"})
    void addService_AddAsUserShouldReturnForbidden() throws Exception {
        Long serviceCount = typeRepo.count();
        createService.setName("New service 3");
        createService.setSek(899.99);
        createService.setCategory(Expertise.ELECTRONICS);
        MinimalTechnicianDTO moss = new MinimalTechnicianDTO();
        moss.setId(2l);
        techs.add(moss);
        createService.setTechnician(techs);

        when(currencyMock.getConversion(anyDouble(), anyString(), anyString())).thenReturn(85.68);

        mockMvc.perform(post("/wigellrepairs/addservice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createService)))
            .andExpect(status().isForbidden());

        assertEquals(serviceCount, typeRepo.count());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addService_MisSpelledExpertiseCategoryShouldReturn() throws Exception {
        Long serviceCount = typeRepo.count();

        String json = "{\"name\":\"New service 3\"," +
                              "\"sek\":899.99," +
                              "\"category\":\"ELEKTRONICS\"" +
                              "}";
        when(currencyMock.getConversion(anyDouble(), anyString(), anyString())).thenReturn(85.68);

        mockMvc.perform(post("/wigellrepairs/addservice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
            .andExpect(status().isNotFound());

        assertEquals(serviceCount, typeRepo.count());
    }
}
