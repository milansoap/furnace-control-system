Furnace Control System
This project is a furnace control system simulation designed to regulate the temperature of a room. The system continuously monitors room temperature, door status, and furnace status, and makes decisions based on these signals. It turns on the furnace when the room temperature drops below a specified threshold and turns off the furnace when the room temperature exceeds the desired temperature.

Features
Monitor Room Temperature: The system checks the room temperature signal continuously and makes decisions based on the desired temperature setpoint.

Monitor Door Status: The system also monitors the door status. If the door is open, the system will turn off the furnace to prevent waste of energy.

Furnace Error Handling: The system is capable of detecting furnace errors. In the case of an error, the furnace is reset. If the reset action is performed three times within a certain period, an alarm signal is sent.

Threaded Control: The system uses separate threads for each signal and control action, providing a responsive and reliable control system.

Graceful Shutdown: The system can be shut down safely without abrupt termination, ensuring that all resources are cleaned up properly.

File I/O: The system can read signals and write the furnace status to/from files.

Usage
To run the system, simply execute the main method in the ControlSystem class. The system will start monitoring the room temperature and make decisions accordingly.

Future Improvements
Future improvements could include:

Adding a user interface for setting the desired temperature.
Improving error handling and recovery.
Expanding the system to control more devices or functions.
Dependencies
This project requires the Java Development Kit (JDK) version 8 or higher.
