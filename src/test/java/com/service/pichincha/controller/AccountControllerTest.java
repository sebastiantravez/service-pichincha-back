package com.service.pichincha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.pichincha.dto.AccountDTO;
import com.service.pichincha.service.AccountService;
import com.service.pichincha.util.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;
    @Mock
    private AccountService accountService;

    private MockMvc mockMvc;

    private TestData testData;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        testData = new TestData();
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldSavePerson() throws Exception {
        mockMvc.perform(post("/saveAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testData.account())))
                .andExpect(status().isOk());
        doNothing().when(accountService).saveAccount(any());
        accountController.saveAccount(testData.accountDTO(testData.account()));
    }

    @Test
    public void shouldUpdateAccount() throws Exception {
        mockMvc.perform(put("/updateAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testData.account())))
                .andExpect(status().isOk());
        when(accountService.updateAccount(any())).thenReturn(new AccountDTO());
        accountController.updateAccount(testData.accountDTO(testData.account()));
    }

    @Test
    public void shouldGetAllAccounts() throws Exception {
        mockMvc.perform(get("/getAllAccounts"))
                .andExpect(status().isOk())
                .andReturn();
        when(accountService.getAllAccounts()).thenReturn(List.of(testData.accountDTO(testData.account())));
        List<AccountDTO> accountPresenters = accountController.getAllAccounts();
        Assertions.assertThat(accountPresenters).isNotEmpty();
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        mockMvc.perform(delete("/deleteAccount" + "?accountId=" + 1))
                .andExpect(status().isOk());
        doNothing().when(accountService).deleteAccount(any());
        accountController.deleteAccount(1000L);
    }


}