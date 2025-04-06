### Environment

- Java version: 17+
- Maven version: 3+

## How project is structured

- init: responsible for initializing the cinema for booking
- cinema: house classes related to cinema, e.g. Seat and relevant utility classes
- booking: responsible for handling booking requests

### Core design idea

1. The main point of engagement with the application starts from the BookingSession class (defines when the program
   exits).
2. The various options for a menu is passed in as a parameter allow greater flexibility to manipulate options that are
   available to user.
2. For each option, a command is associated with it. The command class defines the behaviour and user interaction of an
   option while the option class provides the message user sees and the associated behaviour.
3. Dependency injection is used generously to decouple behaviours as much as possible.

### Assumptions

- When specifying a position for booking, if front rows (seats that are nearer to the screen) are filled, it will fill
  the seat towards the back (seats away from the screen) starting from the specified position. That's assuming users are
  inclined to seat a close to each other as possible.

### How to build

- From the project root folder execute `mvn clean package`

### How to run

- To run the application execute: `java -jar target/cinema-1.0.jar`
