📐 Rectangular Tube Joint Visualizer (Java Desktop)

This project is a single-file Java Swing application designed to visualize and analyze miter joints between rectangular or square tubes.

It functions as an interactive desktop tool where users can define tube dimensions and a custom joint angle, with the application calculating the necessary geometric cuts and displaying a 2D cross-section view.

🚀 Getting Started

Prerequisites

To run this application, you must have the Java Development Kit (JDK 17+) installed on your system.

Source File

The complete, runnable source code is located in: src/desktop/TubeJointVisualizer.java.

🛠️ Build and Run Instructions

Follow these steps using your computer's Terminal or Command Prompt to compile and execute the application.

Step 1: Navigate to the Source Directory

cd src/desktop


Step 2: Compile the Code

Use the Java compiler (javac) to create the executable class file:

javac TubeJointVisualizer.java


Step 3: Run the Application

Execute the compiled application using the Java Runtime Environment (java):

java TubeJointVisualizer


A new desktop window will open with the control panel and visualization area.

🎮 Usage and Interaction

The application is structured into three main areas:

Control Panel (Left): Input fields for defining:

Tube Dimensions (W, H, T, L): Width, Height, Thickness, and Length.

Joint Angle (θ): Set the desired internal angle using the slider or preset buttons (e.g., 90°, 45°).

Visualization Panel (Center): Displays a real-time 2D cross-section of the tube profiles at the joint, illustrating the calculated miter cut angle.

Output Panel (Bottom): Provides technical details, including the calculated Miter Cut Angle ($\alpha$) and current dimensions.
