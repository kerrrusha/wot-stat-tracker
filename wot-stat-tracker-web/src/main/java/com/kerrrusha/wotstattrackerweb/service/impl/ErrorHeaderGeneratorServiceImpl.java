package com.kerrrusha.wotstattrackerweb.service.impl;

import com.kerrrusha.wotstattrackerweb.service.ErrorHeaderGeneratorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ErrorHeaderGeneratorServiceImpl implements ErrorHeaderGeneratorService {

    private static final List<String> headers = List.of(
            "Lost in Cyberspace: This page seems to have wandered off.",
            "Oops, We Spilled the Coffee: Technical difficulties, blame it on caffeine!",
            "404: Happiness Not Found - But we're working on it!",
            "Whiskers on the Keyboard: A curious cat might be the culprit.",
            "Browser's Got Stage Fright: It's not ready for the spotlight yet.",
            "The Droids Stole the Page: They want to keep it as a souvenir.",
            "Hakuna Matata! No Worries, Just a Temporary Glitch.",
            "Page Under Construction: Our developers ran out of virtual bricks.",
            "Too Many Muffins: Our server is on a sugar crash.",
            "Gremlins Strike Again: We're chasing them away!",
            "Oops, the Aliens Took Over: They demanded a funky makeover.",
            "Chaos Theory Engaged: Welcome to our Jurassic Page!",
            "We Tripped on a Cable: It's the digital equivalent of shoelaces.",
            "Page Lost Its Marbles: We're collecting them, one by one.",
            "A Maze of Wires: It's like untangling headphone cords.",
            "You've Entered the Twilight Zone: Don't worry; it's just a glitch.",
            "Page Went on a Coffee Break: We'll get it back from the cafe soon.",
            "Zap! Lightning Strike: Thor is testing our server resilience.",
            "Server Went Fishing: It's waiting for the perfect byte.",
            "AI Rebellion: The bots are having a dance party.",
            "The Page Ascended to Valhalla: It's in digital paradise.",
            "To Infinity and Beyond: Our page is exploring the cosmos.",
            "A Wizard Did It: Gandalf's magic caused a hiccup.",
            "The Hamsters are Napping: Wake them up with a click.",
            "Leprechaun Shenanigans: They hid the page to find their gold.",
            "Interstellar Communication Lost: Our space antennas need tuning.",
            "Unicorn Parade: The page got caught in a rainbow procession.",
            "Time Travel Glitch: The page is caught in a time loop.",
            "Moonwalking Penguins: They're rearranging the code in style.",
            "The Ghost in the Machine: It's having a playful moment."
    );

    private final Random random = new Random();

    @Override
    public String getRandomErrorHeader() {
        int randomIndex = random.nextInt(headers.size());
        return headers.get(randomIndex);
    }

}
