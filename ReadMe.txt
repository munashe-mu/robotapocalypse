ROBOT apocalypse

The year is 2050 and the world as we know it has been taken over by robots. Created as once friendly robots, have now turned against humankind, especially software engineers like yourself. Their mission is to transform everyone into mindless zombies for their entertainment. You as a resistance member (and the last survivor who knows how to code), was designated to develop a system to meet the following requirements

You will develop a REST API (yes, we care about architecture design even in the midst of a robot apocalypse!) which will store information about the survivors, as well as the resources they own.

In order to accomplish this, the API must fulfil the following use cases:

Add survivors to the database
A survivor must have a name, age, gender, ID and last location (latitude, longitude).
A survivor also has an inventory of resources (which you need to declare upon the registration of the survivor). This can include Water, Food, Medication and Ammunition.

Update survivor location
A survivor must have the ability to update their last location, storing the new latitude/longitude pair in the base (no need to track locations, just replacing the previous one is enough).

Flag survivor as infected
In a chaotic situation like this, it's inevitable that a survivor may get transformed into a zombie. When this happens, we need to flag the survivor as infected.
A survivor is marked as infected when at least three other survivors report their contamination.

Connect to the Robot CPU system
Connect to the robot CPU system to get a list of all robots and their known locations. Robots have two categories (Flying robots and land robots). You need to sort this information in a meaningful and intuitive way for humans to understand and process. You can use this link to get the list of robots
https://robotstakeover20210903110417.azurewebsites.net/robotcpu

Reports
The API must also provide the following information:
• Percentage of infected survivors.
• Percentage of non-infected survivors.
• List of infected survivors
• List of non-infected survivors
• List of robots

Notes
• No authentication is needed.
• We still care about proper programming and architecture techniques; you must showcase that you're worthy of surviving the robot apocalypse through the sheer strength of your skills.
• Don't forget to make at least a minimal documentation of the API endpoints and how to use them.
• From the problem description above you can either do a very bare-bones solution or add optional features that are not described.
• Once done, please check this into a GitHub repo. Please ensure that you have a comprehensive readme that details how to run the application
