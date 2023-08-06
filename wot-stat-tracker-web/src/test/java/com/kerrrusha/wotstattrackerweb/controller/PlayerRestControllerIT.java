package com.kerrrusha.wotstattrackerweb.controller;

import com.kerrrusha.wotstattrackerweb.controller.rest.PlayerRestController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
class PlayerRestControllerIT {

    @Autowired
    PlayerRestController playerRestController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(playerRestController).build();
    }

    @Test
    void getNicknameIsValid_invalid() throws Exception {
        String invalidNickname = "::";
        MvcResult result = mockMvc.perform(get("/player/" + invalidNickname + "/is-valid"))
                .andExpect(status().is4xxClientError())
                .andReturn();
        log.info("Response: {}", result.getResponse().getContentAsString());
        log.info("Status: {}", result.getResponse().getStatus());
    }

    @Test
    void getNicknameIsValid_valid() throws Exception {
        String invalidNickname = "Jove";
        MvcResult result = mockMvc.perform(get("/player/" + invalidNickname + "/is-valid"))
                .andExpect(status().isOk())
                .andReturn();
        log.info("Response: {}", result.getResponse().getContentAsString());
        log.info("Status: {}", result.getResponse().getStatus());
    }

}
