📐 Rectangular Tube Joint Visualizer

This project is an interactive application designed to visualize and analyze miter joints between rectangular or square tubes at various custom angles.

It provides two implementations:


Desktop/2D Visualization (Java Swing): A single-file Java application demonstrating geometric calculations and a 2D cross-section view.

🚀 Getting Started

Prerequisites

Implementation

Requirements

Desktop (Java)

Java Development Kit (JDK 17+)


Usage

Navigate to the web source directory: cd src/web

Simply open the file in your browser:

open index.html  # macOS/Linux
# OR
start index.html # Windows


🖥️ Desktop Application (2D Visualization)

The desktop application is located in src/desktop/TubeJointVisualizer.java. It is a single Java Swing file.

Build and Run Instructions

Navigate to the desktop source directory: cd src/desktop

Compile the Java file:

javac TubeJointVisualizer.java


Run the application:

java TubeJointVisualizer


Interaction: Input the tube parameters and joint angle in the left panel. The center panel will display a 2D cross-section illustrating the calculated miter cut.

📝 Commit Message Guidelines

This project uses Conventional Commits. Please follow this format for clear version history:

Type

Description

Example

feat

A new feature or capability.

feat: add wireframe toggle to 3D view

fix

A bug fix.

fix: adjust calculation for 45 degree miter joint

refactor

Code changes that neither fix a bug nor add a feature (e.g., cleanup).

refactor: optimize tube geometry generation function

docs

Documentation only changes.

docs: update build instructions in README

style

Formatting, semi-colons, white-space, etc. (no change to code logic).

style: implement Tailwind classes for better contrast

⚙️ Development Environment

The project requires no external build tools Maven and relies solely on standard browser and JDK capabilities for simplicity.