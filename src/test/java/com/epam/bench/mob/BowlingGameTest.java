 package com.epam.bench.mob;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.hamcrest.Matchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BowlingGameTest
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void gutterGame_shouldScoreZero() throws Exception
    {
        doReset();

        for( int i = 0; i < 20; ++i ) {
            roll(0);
        }

        assertScore(0);
    }

    @Test
    public void normalGame_shouldAddThePins() throws Exception
    {
        doReset();

        for( int i = 0; i < 20; ++i ) {
            roll(4);
        }

        assertScore(80);
    }

    @Test
    public void spare_shouldDoubleNextRoll() throws Exception
    {
        doReset();

        roll(4);
        roll(6);
        roll(1);

        assertScore(12);
    }

    @Test
    public void spare_shouldNotDoubleSubsequentRoll() throws Exception
    {
        doReset();

        roll(4);
        roll(6);
        roll(4);
        roll(5);
        roll(4);

        assertScore(27);
    }

    @Test
    public void strike_shouldDoubleNextTwoRolls() throws Exception
    {
        doReset();

        roll(10);
        roll(6);
        roll(1);

        assertScore(24);
    }

    @Test
    public void full_strike_shouldScore300() throws Exception
    {
        doReset();

        for (int i = 0; i < 12; i++) {
            roll(10);
        }

        assertScore(300);
    }

    @Test
    public void reset_shouldReturn204() throws Exception {
        mockMvc.perform(post("/reset"))
            .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    public void resetAndScore_shouldReturn0() throws Exception {
        doReset();
        assertScore(0);
    }

    

    @Test
    public void pinsShouldReturn204() throws Exception { 
        mockMvc.perform(post("/pins").content("{ \"pins\": 0 }")
            .contentType("application/json"))
            .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    public void nonZeroPinsShouldScoreTheSame() throws Exception {
        doReset();
        roll(10);
        assertScore(10);
    }


    private void doReset() throws Exception {
        mockMvc.perform(post("/reset"));
    }

    private void roll(Integer pins) throws Exception {
        mockMvc.perform(post("/pins").content("{ \"pins\":" + pins + "}")
            .contentType("application/json"))
            .andExpect(MockMvcResultMatchers.status().is(204));
    }

    private void assertScore(Integer score) throws Exception {
        mockMvc.perform(get("/score"))
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.score", Matchers.equalTo(score)));
    }


}

///Bowling service:

//One endpoint for resetting
//POST /reset
//request payload: null
//response: HTTP 204 NO CONTENT

//One endpoint for inputting how many pins were knocked down
//POST /pins
//request payload: { "pins": 10 }
//response: HTTP 204 NO CONTENT

//One endpoint for getting the results
//GET /score
//response: { "score": 87 }
//HTTP 200 OK
