# Project Ruby

This project is short and sweet. It is a solver for the activity card minigame in New World: Aeternum. In this minigame, you complete activities which grants points to contribute towards your seasonal pass.

Conventionl wisdom is that you only really need to complete any six actvities to complete a card, which appears true if you place your wild stamps optimally. However, these optimal placements are not always obvious. This is where th is project comes in.

## Development

The project consists of two code bases. The front end is a simple React app responsible for input and output. The back end a Java Spring Boot app that attempts to determine an optimal sequence of placements using a rudiemntary algorithm.

The whole thing is combined together into a single docker container using a multi-stage build.
