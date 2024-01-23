package com.epam.bench.mob;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BowlingGameTest
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void gutterGame_shouldScoreZero() throws Exception
    {
//        for( int i = 0; i < 20; ++i ) {
            mockMvc.perform(post("/pins").content("{ \"pins\": 0 }"))
              .andExpect(MockMvcResultMatchers.status().is(204));
//        }
    }

    @Test
    public void pinsShouldReturn204() throws Exception { 
        mockMvc.perform(post("/pins").content("{ \"pins\": 0 }"))
            .andExpect(MockMvcResultMatchers.status().is(204));
    }


}

///Bowling service:

//One endpoint for inputting how many pins were knocked down
//POST /pins
//request payload: { "pins": 10 }
//response: HTTP 204 NO CONTENT

//One endpoint for getting the results
//GET /score
//response: { "score": 87 }
//HTTP 200 OK
