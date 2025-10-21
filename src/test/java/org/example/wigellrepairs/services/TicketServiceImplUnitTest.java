package org.example.wigellrepairs.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import org.example.wigellrepairs.dtos.services.MinimalServiceDTO;
import org.example.wigellrepairs.dtos.services.ShowServiceTypeDTO;
import org.example.wigellrepairs.dtos.tickets.BookTicketDTO;
import org.example.wigellrepairs.dtos.tickets.ShowTicketDTO;
import org.example.wigellrepairs.entities.RepairTicket;
import org.example.wigellrepairs.entities.ServiceType;
import org.example.wigellrepairs.enums.Expertise;
import org.example.wigellrepairs.exceptions.InPastException;
import org.example.wigellrepairs.exceptions.ServiceTimeUnavailableException;
import org.example.wigellrepairs.repositories.TicketRepo;
import org.example.wigellrepairs.utils.Checker;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplUnitTest {

    private TicketServiceImpl ticketService;

    @Mock
    private TicketRepo ticketRepo;
    @Mock
    private AuthService authService;
    @Mock
    private TimeService timeService;
    @Mock
    private LoggerService loggerService;
    @Mock
    private DTOMapperService mapperService;
    @Mock
    private ServiceTypeService typeService;

    private BookTicketDTO bookTicketDTO;
    private ShowTicketDTO showTicketDTO;
    private MinimalServiceDTO miniServiceDTO;
    private ServiceType service;
    private ShowServiceTypeDTO showServiceDTO;
    private List<RepairTicket> tickets;

    @BeforeEach
    void setUp() {
        ticketService = new TicketServiceImpl(ticketRepo, authService, loggerService, mapperService, typeService, timeService);

        bookTicketDTO = new BookTicketDTO();
        miniServiceDTO = new MinimalServiceDTO();
        service = new ServiceType();
        tickets = new ArrayList<>();

        miniServiceDTO.setId(4l);
        service.setId(4l);
        service.setName("Screen replacement");
        service.setSek(1249.99);
        service.setCategory(Expertise.ELECTRONICS);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithMockUser(username = "user.usersson@mail.se", roles = {"USER"})
    void createTicket_AllValidFieldsShouldReturnShowTicketDTO() {
        bookTicketDTO.setDate(1760690487L);
        bookTicketDTO.setService(miniServiceDTO);

        showTicketDTO = new ShowTicketDTO(
            1l,
            "user.usersson@mail.se",
            1760690387L,
            false,
            1249.99,
            120.00,
            showServiceDTO
        );

        try(MockedStatic<Checker> mockedChecker = mockStatic(Checker.class)){

            mockedChecker.when(() -> Checker.valueCheck(anyLong(), anyString())).thenAnswer(a -> null);
            mockedChecker.when(() -> Checker.valueCheck(any(MinimalServiceDTO.class), anyString())).thenAnswer(a -> null);

            when(typeService.findExisting(anyLong())).thenReturn(service);
            when(timeService.formatted(anyLong(), anyString())).thenReturn("1970-01-01 00:00");
            when(timeService.getCurrentUTC()).thenReturn(1760690387L);
            when(ticketRepo.findByServiceCategory(any(Expertise.class))).thenReturn(tickets);
            when(authService.getUserName()).thenReturn("user.usersson@mail.se");
            when(mapperService.toTicketDTO(any(RepairTicket.class))).thenReturn(showTicketDTO);

            ShowTicketDTO result = ticketService.createTicket(bookTicketDTO);

            assertNotNull(result);
            assertEquals(showTicketDTO.getTotalEur(), result.getTotalEur());

        }
    }

    @Test
    @WithMockUser(username = "user.usersson@mail.se", roles = {"USER"})
    void createTicket_BookingWithin45MinutesShouldReturnConflict() {
        bookTicketDTO.setDate(1760892387L); // +2000 - ~33 min efter existerande 1760890387
        bookTicketDTO.setService(miniServiceDTO);

        ServiceType screenReplacement = new ServiceType(
            "Screen replacement",
            1249.99,
            Expertise.ELECTRONICS,
            new ArrayList<>()
        );
        screenReplacement.setId(2L);
        RepairTicket screenRepTicket = new RepairTicket(
            "user.usersson@mail.se",
            1760890387L,
            1249.99,
            screenReplacement
        );
        screenRepTicket.setId(1L);
        tickets.clear();
        tickets.add(screenRepTicket);

        try(MockedStatic<Checker> mockedChecker = mockStatic(Checker.class)){

            mockedChecker.when(() -> Checker.valueCheck(anyLong(), anyString())).thenAnswer(a -> null);
            mockedChecker.when(() -> Checker.valueCheck(any(MinimalServiceDTO.class), anyString())).thenAnswer(a -> null);

            when(typeService.findExisting(anyLong())).thenReturn(service);
            when(timeService.formatted(anyLong(), anyString())).thenReturn("1970-01-01 00:00");
            when(timeService.getCurrentUTC()).thenReturn(1760690387L);
            when(ticketRepo.findByServiceCategory(any(Expertise.class))).thenReturn(tickets);

            assertThrows(ServiceTimeUnavailableException.class, () -> ticketService.createTicket(bookTicketDTO));
        }
    }

    @Test
    @WithMockUser(username = "user.usersson@mail.se", roles = {"USER"})
    void createTicket_BookingWithin45MinutesBeforeExistingShouldReturnConflict() {
        bookTicketDTO.setDate(1760888387L); // -2000 - ~33 min före existerande 1760890387
        bookTicketDTO.setService(miniServiceDTO);

        ServiceType screenReplacement = new ServiceType(
            "Screen replacement",
            1249.99,
            Expertise.ELECTRONICS,
            new ArrayList<>()
        );
        screenReplacement.setId(2L);
        RepairTicket screenRepTicket = new RepairTicket(
            "user.usersson@mail.se",
            1760890387L,
            1249.99,
            screenReplacement
        );
        screenRepTicket.setId(1L);
        tickets.clear();
        tickets.add(screenRepTicket);

        try(MockedStatic<Checker> mockedChecker = mockStatic(Checker.class)){

            mockedChecker.when(() -> Checker.valueCheck(anyLong(), anyString())).thenAnswer(a -> null);
            mockedChecker.when(() -> Checker.valueCheck(any(MinimalServiceDTO.class), anyString())).thenAnswer(a -> null);

            when(typeService.findExisting(anyLong())).thenReturn(service);
            when(timeService.formatted(anyLong(), anyString())).thenReturn("1970-01-01 00:00");
            when(timeService.getCurrentUTC()).thenReturn(1760690387L);
            when(ticketRepo.findByServiceCategory(any(Expertise.class))).thenReturn(tickets);

            assertThrows(ServiceTimeUnavailableException.class, () -> ticketService.createTicket(bookTicketDTO));
        }
    }

    @Test
    @WithMockUser(username = "user.usersson@mail.se", roles = {"USER"})
    void createTicket_BookingInPastShouldReturnBadRequest() {
        bookTicketDTO.setDate(858045600L); // 1997-03-11 02:00
        bookTicketDTO.setService(miniServiceDTO);

        try(MockedStatic<Checker> mockedChecker = mockStatic(Checker.class)){

            mockedChecker.when(() -> Checker.valueCheck(anyLong(), anyString())).thenAnswer(a -> null);
            mockedChecker.when(() -> Checker.valueCheck(any(MinimalServiceDTO.class), anyString())).thenAnswer(a -> null);

            when(timeService.formatted(anyLong(), anyString())).thenReturn("1970-01-01 00:00");
            when(timeService.getCurrentUTC()).thenReturn(1760690387L);

            assertThrows(InPastException.class, () -> ticketService.createTicket(bookTicketDTO));
        }
    }
}
