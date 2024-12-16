# **Sky Battle**

The link to the Github Repository goes as follows:  
[https://github.com/ImpactGLX323/CW2024](https://github.com/ImpactGLX323/CW2024)  

To access the repository, open any web browser and type the above link.

---

## **INTRODUCTION TO THE GAME**

The game **'Sky Battle'** is inspired by the space shooter games of the early 2000s. These games were popular for their simplicity and easy controls. Similarly, **'Sky Battle'** does not demand complex skills like modern games but instead relies on basic keyboard inputs for gameplay.  

### **Features:**
1. A **UserPlane** (Main Actor/Protagonist/User) shoots down **EnemyPlanes**.  
2. EnemyPlanes appear on each level, with difficulty increasing as levels progress.  
3. The user's lifetime depends on their health, which decreases as levels advance.  
4. Graphics transitions occur as users progress through levels.  
5. Basic keyboard controls are sufficient for gameplay, with options to "NextLevel" and "Restart."

---

## **COMPILATION INSTRUCTIONS**

1. The project requires **JDK version 19.0.2** and the corresponding **JavaFX SDK 19.0.2** version.  
   - Ensure compatibility between the JDK and JavaFX versions to use libraries like `JavaFX.graphics` and `JavaFX.media`, which are mandatory for the project.  

2. The source files are located at:
   - `CW2024/src/main/java/com/example/demo`  
   - **Controller.java** and **Main.java** are inside the `controller` folder under the `demo` directory.  

3. Steps to run the project:
   - Download the project from the GitHub repository.
   - Run the project on any IDE (such as IntelliJ, Eclipse, or VSCode) or via the command line, after ensuring that the JDK, JavaFX, and Maven dependencies are installed.
   - Execute the application. The game starts at **"Level One"** with the UserPlane battling EnemyPlanes.
   - Use the buttons **"TRY AGAIN"** and **"NextLevel"** to proceed through the gameplay until you win Level Two.

---

## **IMPLEMENTED FEATURES**

### **Working Features**
1. **All Classes Functional:**  
   - The UserPlane and EnemyPlane functionality is operational.  
   - The UserPlane can shoot projectiles to score points and lose health when collides with EnemyPlanes.  
   - If health becomes zero, the game is over but has an option to restart the game.  
   - Achieving the target number of kills allows the user to proceed to the next level via the "NextLevel" button.  

2. **Smooth Transitions:**  
   - Transitions between levels are functional, with scene backgrounds updating correctly.  

3. **Improved Codebase:**  
   - The code has been refactored for clarity and encapsulation and uses Object-Oriented Programming (OOP) principles.  
   - Deprecated features like `Observable` have been removed and existing code modified accordingly.

4. **Better Exception Handling:**  
   - Enhanced mechanisms provide detailed stack traces for debugging. Better exception handling and error handling mechanisms to support Stack Tracing in order to show the exact location of exceptions, making the exceptions for specific (NullPointerException, IOException) which is helpful for debugging complex and nested exceptions in the classes.These changes also enabled better error location and indetifying potential log issues in the code.
  

5. **New Methods and Functionalities:**  
   - Methods like `getStage()`, `getKillCount()`, `askForRestart()`, `restartGame()`, and `goToNextLevel()` have been added and enhanced to adapt to the changing requirements.  
   - Buttons for **RestartGame** and **NextLevel** have been introduced.  

6. **Flexible Screen Layout:**  
   - The game supports different layouts for improved usability.

---
## **Game in Action**

### **Level One**
![Level One Screenshot](Screenshots/L1.png)

### **Level Two**
![Level Two Screenshot](Screenshots/L2.png)

### **Level One Win**
![Level One Win Screenshot](Screenshots/L1%20Win.png)

### **IMPLEMENTED FEATURES BUT NOT FUNCTIONAL**

1. **Logical Errors in New Features:**  
   - The addition of different methods to classes like `LevelParent`, `LevelOne`, and `LevelTwo` led to presence of logical errors, preventing EnemyPlanes from throwing projectiles.  

2. **Non-Functional Features:**  
   - **RestartGame**: Logic errors prevent the game from restarting and reseting the game state.  
   - **BackgroundMusic**: Despite the presence of `Audio.java` and file paths, background music is not functional due to logic issues.  

---

## **FEATURES NOT IMPLEMENTED**

1. **Boss Levels:**  
   - The `Boss.java` class has been removed. Expansion to further levels with a "Boss" enemy is not supported.  

2. **Shield Mechanic:**  
   - Shielding functionality is not implemented.  

---

## **NEW CLASSES**

1. **Audio.java:**  
   - Added to handle background music and audio functionalities.  
   - The class modifies `LevelParent.java`, `Controller.java`, and `Main.java`.  
   - However, due to logical issues, audio is not yet functional.  

2. **No Splitting/Merging:**  
   - Existing classes were modified instead of splitting or merging them.  

---

## **MODIFIED CLASSES**

1. **LevelParent.java:**  
   - New methods added to improve functionality and incorporate new features.  
2. **LevelOne and LevelTwo:**  
   - Enhanced with additional methods inherited from their parent classes.  
3. **Main.java:**  
   - Updated to accommodate changing requirements.  
4. **Controller.java:**  
   - Modified to support new functionalities and upgrades.  
5. **Module-info.java:**  
   - Updated to import necessary modules and libraries.  

---

## **UNEXPECTED ERRORS**

1. **JavaFX and JDK Version Compatibility:**  
   - Ensure **JDK and JavaFX are version 19.0.2** to avoid issues with building and initializing scenes.  

2. **Interaction Requires Clicking the Screen:**  
   - The game requires the user to click on the screen to start interactions.

---

## **LINKS**
- **GitHub Repository:**  
  [https://github.com/ImpactGLX323/CW2024](https://github.com/ImpactGLX323/CW2024)
# Sky Battle

The link to the Github Repository goes as follows:
https://github.com/ImpactGLX323/CW2024

Go to any web browser and type the following to access the repository

You are welcome to change the introduction to suit your needs, adding information or changing the tone to match the aesthetic of your project.
## **Features**
- **Paddle controls**: Use the arrow keys or "UP", "DOWN" buttons to move the plan UP and DOWN precisely, causing mobility and increasing the precision of throwing projectiles.
- **Destroy planes**: Use the "SPACE" bar in the keyboard to throw projectile and if it hits the enemy, it will destroy the enemyPlane.
- **Next Level**: After succeeding the first level, tap the "NEXT LEVEL" button to go to the next level.
- **RESTART GAME**: Quit the game by stop running the game.You can start a new game by running the code again.

