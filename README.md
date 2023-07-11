**   Furnace Control System ** 

This project is a Furnace Control System Simulation designed to regulate the temperature of a room. The system continuously monitors room temperature, door status, and furnace status, and makes decisions based on these signals. It turns on the furnace when the room temperature drops below a specified threshold and turns off the furnace when the room temperature exceeds the desired temperature.

:star2: Features
1. Monitor Room Temperature: The system checks the room temperature signal continuously and makes decisions based on the desired temperature setpoint.

2. Monitor Door Status: The system also monitors the door status. If the door is open, the system will turn off the furnace to prevent waste of energy.

3. Furnace Error Handling: The system is capable of detecting furnace errors. In the case of an error, the furnace is reset. If the reset action is performed three times within a certain period, an alarm signal is sent.

4. Threaded Control: The system uses separate threads for each signal and control action, providing a responsive and reliable control system.

5. Graceful Shutdown: The system can be shut down safely without abrupt termination, ensuring that all resources are cleaned up properly.

6. File I/O: The system can read signals and write the furnace status to/from files.

:arrow_forward: Usage
To run the system, simply execute the main method in the Main class. The system will start monitoring the room temperature and make decisions accordingly.

:arrow_up: Future Improvements
**Future improvements could include:
**

1. Adding a user interface for setting the desired temperature.
2. Improving error handling and recovery.
3. Expanding the system to control more devices or functions.

:wrench: Dependencies
This project requires the Java Development Kit (JDK) version 8 or higher.
