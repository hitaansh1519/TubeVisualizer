📐 Rectangular Tube Joint Visualizer

This project is an interactive application designed to visualize and analyze miter joints between rectangular or square tubes at various custom angles.

It provides two distinct implementations:

Web/3D Visualization (Three.js): A single-file web application for interactive 3D inspection, bundled via Electron for desktop use.

Desktop/2D Visualization (Java Swing): A single-file Java application demonstrating geometric calculations and a 2D cross-section view.

🚀 Getting Started

Prerequisites

Implementation

Requirements

Web/Electron (3D)

Node.js (for building the executable) and a Modern Web Browser (for development/testing).

Desktop (Java 2D)

Java Development Kit (JDK 17+) to compile and run the source file.

Source Files

Implementation

Path

Description

Java Desktop (2D)

src/desktop/TubeJointVisualizer.java

Requires JDK to compile and run.

Web Visualization (3D)

src/web/index.html

Packaged using Electron for standalone desktop use.

🌐 3D Web Visualization (Development/Testing)

The core 3D visualization is a single-file HTML application.

Usage

Navigate to the web source directory: cd src/web

Simply open the file in your browser:

open index.html # macOS/Linux
# OR
start index.html # Windows


🖥️ Java Desktop Application: Build and Run Instructions

This application demonstrates the geometric calculation using a 2D cross-section view.

Build and Run Instructions

Navigate to the desktop source directory: cd src/desktop

Compile the Java file:

javac TubeJointVisualizer.java


Run the application:

java TubeJointVisualizer


📦 Electron Packaging Steps (Final Submission)

This process bundles the 3D Web Visualization (src/web/index.html) into a single, standalone executable for deployment.

1. Install Dependencies

From the root directory of the project (/tube-joint-visualizer), install the required development tools (electron and electron-builder) as defined in package.json:

npm install


2. Build the Application

Use the custom script defined in package.json to start the build process. This will create platform-specific executables (Windows, macOS, Linux).

npm run build:electron


3. Locate the Executable File

The electron-builder tool places all output files into the newly created dist directory at the project root.

Operating System

Output File Location Example

Windows

dist/Tube Joint Visualizer Setup 1.0.0.exe

macOS

dist/Tube Joint Visualizer-1.0.0.dmg

Linux

dist/Tube Joint Visualizer-1.0.0.AppImage

📝 Commit Message Guidelines

This project uses Conventional Commits. Please follow this format for clear version history:

Type

Description

Example

feat

A new feature or capability.

feat(electron): add mac build target

fix

A bug fix.

fix(java): correct validation for wall thickness

refactor

Code changes that neither fix a bug nor add a feature (e.g., cleanup).

refactor(web): improve updateJoint calculation logic

docs

Documentation only changes.

docs: finalize README with packaging instructions